package com.dlyd.application.gbm.service.cs;


import com.dlyd.application.gbm.service.KtkjInfo;
import com.dlyd.application.gbm.service.TransactionReportingService;
import com.dlyd.application.gbm.service.common.AppConst;
import com.dlyd.application.gbm.service.common.PathConfig;
import com.dlyd.application.gbm.service.dao.ReportingBatch;
import com.dlyd.application.gbm.service.dao.SysXtcsDAO;
import com.dlyd.application.gbm.service.domain.SysXtcsDO;
import com.dlyd.application.gbm.service.kit.DateKit;
import com.dlyd.application.gbm.service.service.impl.SysXtcsServiceImpl;
import com.dlyd.application.lib.esb.msg11.ESBMessage;
import com.dlyd.application.lib.webservice.WsSoapMessage;

import java.io.*;
import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.xml.soap.SOAPMessage;

import com.dlyd.application.sms.SmsConfig;
import com.dlyd.application.sms.connector.SmsMessage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.noc.hccp.platform.common.AppLog;
import org.noc.hccp.platform.common.PLOG;
import org.noc.hccp.platform.common.PlatformException;
import org.noc.hccp.platform.common.ctx.ConnectorContext;
import org.noc.hccp.platform.common.job.JobParameters;
import org.noc.hccp.platform.common.job.JobResult;
import org.noc.hccp.platform.common.job.JobScheduleConfiguration;
import org.noc.hccp.platform.service.ApplicationJob;
import org.noc.hccp.platform.service.Connector;
import org.noc.hccp.platform.service.ConnectorManager;
import org.noc.hccp.platform.service.ScheduledJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Base64Utils;

@JobScheduleConfiguration(
        path = "/政府债券/定时任务/自动上报",
        code = "com.dlyd.application.gbm.service.job.AutomaticReporting",
        name = "自动上报定时任务"
)
public class AutomaticReportingJob extends ApplicationJob implements ScheduledJob {

  @Autowired
  private TransactionReportingService transactionReportingService;
  @Autowired
  private ConnectorManager connectorManager;
  @Autowired
  private PathConfig pathConfig;
  @Autowired
  private SmsConfig smsConfig;
  @Autowired
  private SysXtcsServiceImpl sysXtcsService;

  @Override
  protected void start(JobParameters jobParameters) {
    ReportingBatch batch = null;
    try {
      SysXtcsDO sysXtcsDO = sysXtcsService.selectOne(AppConst.APP_ID);
      PLOG.normal.debug(sysXtcsDO.toString());
      String yesterday = DateKit.yesterday(sysXtcsDO.getXtrq(), "yyyyMMdd");
      batch = transactionReportingService.initializeDailyReporting(yesterday);
      transactionRecordsQuerySuccess(batch);
    } catch (Exception e) {
      PLOG.normal.error(e.getMessage(), e);
      try {
        transactionReportingService.queryTransactionsFailed(batch);
      } catch (Exception e2) {
        PLOG.normal.error(e2.getMessage(), e2);
      } finally {
        smsNotify(batch.getDate());
        finish(JobResult.FAIL, "查询核心交易明细失败");
      }
    }
  }


  private void transactionRecordsQuerySuccess(ReportingBatch batch) {
    String filename = String.format(AppConst.FileNameForamt.TRANSACTIONS, batch.getDate());
    List<KtkjInfo> list = new ArrayList<>();
    try (FileInputStream fileInputStream = FileUtils.openInputStream(new File(pathConfig.getNasCbsShareDownloadPath(), filename));
         BufferedInputStream inputStream = new BufferedInputStream(fileInputStream)) {
      List<String> lines = IOUtils.readLines(inputStream);
      for (String line : lines) {
        String[] split = line.split(AppConst.SPLIT_PIPE);
        KtkjInfo ktkjInfo = new KtkjInfo();
        ktkjInfo.setBankId(split[0]);
        ktkjInfo.setTransHour(split[1]);
        ktkjInfo.setTransAmount(split[2]);
        ktkjInfo.setHandFee(split[3]);
        ktkjInfo.setTotal(split[4]);
        ktkjInfo.setCurrency(split[5]);
        ktkjInfo.setCounterBankName(split[6]);
        ktkjInfo.setCounterBankId(split[7]);
        ktkjInfo.setPayeeName(split[8]);
        ktkjInfo.setVoucherNumber(split[9]);
        ktkjInfo.setTransType(split[10]);
        ktkjInfo.setUseFunds(split[11]);
        ktkjInfo.setBalance(split[12]);
        ktkjInfo.setLoanSign(split[13]);
        ktkjInfo.setRemarks(split[14]);
        ktkjInfo.setMark(split[15]);
        ktkjInfo.setPzNumber(split[16]);
        ktkjInfo.setVirtualBankId(split[17]);
        list.add(ktkjInfo);
      }
      transactionReportingService.queryTransactionsSuccess(batch, list);
      report(batch, list);
    } catch (PlatformException e) {
      PLOG.normal.error(e.getMessage(), e);
      try {
        transactionReportingService.queryTransactionsFailed(batch);
      } catch (Exception e2) {
        PLOG.normal.error(e2.getMessage(), e2);
      } finally {
        finish(JobResult.FAIL, "地方政府专项债券资金数据报送平台，[" + batch.getDate() + "]交易明细入库失败");
        smsNotify(batch.getDate(), "地方政府专项债券资金数据报送平台，[" + batch.getDate() + "]交易明细入库失败," + e.getMessage());
        return;
      }
    } catch (Exception e) {
      PLOG.normal.error(e.getMessage(), e);
      try {
        transactionReportingService.queryTransactionsFailed(batch);
      } catch (Exception e2) {
        PLOG.normal.error(e2.getMessage(), e2);
      } finally {
        PLOG.normal.info("登记核心[{}]交易明细失败", batch.getDate());
        finish(JobResult.FAIL, "登记核心[{" + batch.getDate() + "}]交易明细失败");
        smsNotify(batch.getDate());
        return;
      }
    }
  }



  private void report(ReportingBatch batch, List<KtkjInfo> list) {
    try {
      // 调用WS上报明细
      SOAPMessage soapMessage = transactionReportingService.buildReportingMessage(list);
      WsSoapMessage wsSoapMessage = new WsSoapMessage();
      wsSoapMessage.setSoapMessage(soapMessage);
      wsSoapMessage.setSoapAction("ktkjBankFlowSaveCS");
      wsSoapMessage.setSoapEndpoint("/axis2/services/MyService.MyServiceHttpSoap11Endpoint/");

      Connector wsConnector = connectorManager.acquireConnector("WS");
      ConnectorContext<WsSoapMessage> cc = createConnectorContext(WsSoapMessage.class);
      cc.setOutboundPayloadSupplier(() -> wsSoapMessage);
      cc.setInboundPayloadConsumer((inboundWsSoapMessage) -> reportFinish(batch, inboundWsSoapMessage));
      cc.setExceptionHandler((throwable) -> reportFailed(batch, throwable));
      wsConnector.submit(cc);
    } catch (Exception e) {
      PLOG.normal.error(e.getMessage(),e);
      try {
        transactionReportingService.reportTransactionsFailed(batch);
      } catch (Exception e2) {
        PLOG.normal.error(e2.getMessage(), e2);
      } finally {
        PLOG.normal.info("推送核心[{}]交易明细失败", batch.getDate());
        finish(JobResult.FAIL, "推送核心[{" + batch.getDate() + "}]交易明细失败");
        smsNotify(batch.getDate());
      }
    }
  }

  private void reportFailed(ReportingBatch batch, Throwable throwable) {
    PLOG.normal.error(throwable.getMessage(), throwable);
    try {
      transactionReportingService.reportTransactionsFailed(batch);
    } catch (Exception e2) {
      PLOG.normal.error(e2.getMessage(), e2);
    } finally {
      finish(JobResult.FAIL, "推送核心交易明细失败");
      smsNotify(batch.getDate());
    }
  }

  private void reportFinish(ReportingBatch batch, WsSoapMessage inboundWsSoapMessage) {
    try {
      String jsResult = transactionReportingService.getReportingResponse(inboundWsSoapMessage.getSoapMessage());
      boolean success = transactionReportingService.reportTransactionsSuccess(batch, jsResult);
      if (!success) {
        smsNotify(batch.getDate(), "地方政府专项债券资金数据报送平台,反馈：存在错误数据");
      }
      finish(JobResult.SUCCESS, "推送核心交易明细成功完成");
      PLOG.normal.info("推送核心交易明细成功完成");
    } catch (Exception e) {
      PLOG.normal.error(e.getMessage(), e);
      try {
        transactionReportingService.reportTransactionsFailed(batch);
      } catch (Exception e2) {
        PLOG.normal.error(e2.getMessage(), e2);
      } finally {
        finish(JobResult.FAIL, "推送核心交易明细失败");
        smsNotify(batch.getDate());
      }
    }
  }

  private void smsNotify(int date) {
    smsNotify(date, "政府债券项目库及全生命周期管理,[" + date + "]交易明细数据推送失败");
  }


  private void smsNotify(int date, String info) {
    final SmsMessage smsMessage = new SmsMessage();
    PLOG.normal.debug("政府债券项目库及全生命周期管理,[" + date + "]交易明细数据推送失败" + info);
    smsMessage.setContent(info);
    for (String phone : smsConfig.getPhone()) {
      smsMessage.addItem(phone);
    }
    Connector connector = connectorManager.acquireConnector("SMS-HTTP");
    ConnectorContext<SmsMessage> connectorContext = createConnectorContext(SmsMessage.class);
    connectorContext.setOutboundPayloadSupplier(() -> smsMessage);
    connectorContext.setInboundPayloadConsumer((responseMessage) ->
            PLOG.normal.info("预警短信发送,短信平台响应码：[{}]", responseMessage.getCode()));
    connectorContext.setExceptionHandler((throwable) ->
            PLOG.normal.error("预警短信发送通讯异常", throwable));
    connector.submit(connectorContext);
  }
}
