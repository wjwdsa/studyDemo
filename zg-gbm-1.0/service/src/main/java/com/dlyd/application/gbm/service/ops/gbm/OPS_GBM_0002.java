package com.dlyd.application.gbm.service.ops.gbm;

import com.dlyd.application.gbm.service.KtkjInfo;
import com.dlyd.application.gbm.service.TransactionReportingService;
import com.dlyd.application.gbm.service.common.AppConst;
import com.dlyd.application.gbm.service.common.PathConfig;
import com.dlyd.application.gbm.service.dao.ReportingBatch;
import com.dlyd.application.gbm.service.esb.ESBBeanNameResolver;
import com.dlyd.application.gbm.service.esb.ESBTransaction;
import com.dlyd.application.lib.esb.msg11.Array;
import com.dlyd.application.lib.esb.msg11.ESBMessage;
import com.dlyd.application.lib.esb.msg11.Field;
import com.dlyd.application.lib.esb.msg11.Struct;
import com.dlyd.application.lib.webservice.WsSoapMessage;
import com.dlyd.application.sms.connector.SmsMessage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.noc.hccp.platform.common.PLOG;
import org.noc.hccp.platform.common.PlatformException;
import org.noc.hccp.platform.common.ctx.ConnectorContext;
import org.noc.hccp.platform.common.job.JobResult;
import org.noc.hccp.platform.common.tx.PlatformTransaction;
import org.noc.hccp.platform.service.Connector;
import org.noc.hccp.platform.service.ConnectorManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.soap.SOAPMessage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

/**
 * /政府债券/ops重新推送
 */
@PlatformTransaction("OPS:GBM-0002")
public class OPS_GBM_0002 extends ESBTransaction {
  @Autowired
  private ConnectorManager connectorManager;
  @Autowired
  private TransactionReportingService transactionReportingService;
  @Autowired
  private PathConfig pathConfig;

  @Override
  protected String getTransactionCode() {
    return null;
  }

  @Override
  protected String getTransactionName() {
    return "/政府债券/ops重新推送";
  }

  @Override
  protected void start(ESBMessage esbMessage) {
    ESBMessage.Visitor visitor = esbMessage.getVisitor();
    String reportDate = visitor.getField("reportDate").getValue();
    ReportingBatch batch = null;
    try {
      batch = transactionReportingService.initializeDailyReporting(reportDate);
      transactionRecordsQuerySuccess(esbMessage, batch);
    } catch (Exception e) {
      PLOG.normal.error(e.getMessage(), e);
      try {
        transactionReportingService.queryTransactionsFailed(batch);
      } catch (Exception e2) {
        PLOG.normal.error(e2.getMessage(), e2);
      } finally {
        finish(esbMessage, "-1", e.getMessage());
      }
    }
  }


  private void transactionRecordsQuerySuccess(ESBMessage esbMessage, ReportingBatch batch) {
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
      report(esbMessage, batch, list);
    } catch (PlatformException e) {
      PLOG.normal.info("------------------------------------------------");
      PLOG.normal.error(e.getMessage(), e);
      try {
        transactionReportingService.queryTransactionsFailed(batch);
      } catch (Exception e2) {
        PLOG.normal.error(e2.getMessage(), e2);
      } finally {
        finish(esbMessage, "-1", e.getMessage());
      }
    } catch (Exception e) {
      PLOG.normal.error(e.getMessage(), e);
      try {
        transactionReportingService.queryTransactionsFailed(batch);
      } catch (Exception e2) {
        PLOG.normal.error(e2.getMessage(), e2);
      } finally {
        finish(esbMessage, "-1", e.getMessage());
      }
    }


  }

  private void report(ESBMessage esbMessage, ReportingBatch batch, List<KtkjInfo> list) {
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
      cc.setInboundPayloadConsumer((inboundWsSoapMessage) -> reportFinish(esbMessage, batch, inboundWsSoapMessage));
      cc.setExceptionHandler((throwable) -> reportFailed(esbMessage, batch, throwable));
      wsConnector.submit(cc);
    } catch (Exception e) {
      PLOG.normal.error(e.getMessage(),e);
      try {
        transactionReportingService.reportTransactionsFailed(batch);
      } catch (Exception e2) {
        PLOG.normal.error(e2.getMessage(), e2);
      } finally {
        PLOG.normal.info("推送核心[{}]交易明细失败", batch.getDate());
        finish(esbMessage, "-1", e.getMessage());
      }
    }
  }

  private void reportFailed(ESBMessage esbMessage, ReportingBatch batch, Throwable throwable) {
    PLOG.normal.error(throwable.getMessage(), throwable);
    try {
      transactionReportingService.reportTransactionsFailed(batch);
    } catch (Exception e2) {
      PLOG.normal.error(e2.getMessage(), e2);
    } finally {
      finish(esbMessage, "-1", throwable.getMessage());
    }
  }

  private void reportFinish(ESBMessage esbMessage, ReportingBatch batch, WsSoapMessage inboundWsSoapMessage) {
    PLOG.dev.debug("1111111111111111111111111111111111111111111");
    try {
      String jsResult = transactionReportingService.getReportingResponse(inboundWsSoapMessage.getSoapMessage());
      transactionReportingService.reportTransactionsSuccess(batch, jsResult);
      finish(esbMessage, "000000", "");
      PLOG.normal.info("success");
    } catch (Exception e) {
      PLOG.normal.error(e.getMessage(), e);
      try {
        transactionReportingService.reportTransactionsFailed(batch);
      } catch (Exception e2) {
        PLOG.normal.error(e2.getMessage(), e2);
      } finally {
        finish(esbMessage, "-1", e.getMessage());
      }
    }
  }


  private void finish(ESBMessage esbMessage, String code, String msg) {
    ESBMessage.Builder builder = esbMessage.newResponseBuilder();
    builder.setSH_RetStatus("S");
    builder.addSH_Ret(code, msg);
    finish(builder.build(), "finish");
  }


}
