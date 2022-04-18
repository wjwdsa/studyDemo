package com.dlyd.application.gbm.service.cs;


import com.dlyd.application.gbm.service.TransactionReportingService;
import com.dlyd.application.gbm.service.common.AppConst;
import com.dlyd.application.gbm.service.common.EsbHeader;
import com.dlyd.application.gbm.service.dao.ReportingAccount;
import com.dlyd.application.gbm.service.dao.SysXtcsDAO;
import com.dlyd.application.gbm.service.domain.SysXtcsDO;
import com.dlyd.application.gbm.service.kit.DateKit;
import com.dlyd.application.lib.esb.msg11.Array;
import com.dlyd.application.lib.esb.msg11.ESBMessage;
import com.dlyd.application.lib.esb.msg11.Field;
import com.dlyd.application.lib.esb.msg11.Struct;
import org.noc.hccp.platform.common.PLOG;
import org.noc.hccp.platform.common.ctx.ConnectorContext;
import org.noc.hccp.platform.common.job.JobParameters;
import org.noc.hccp.platform.common.job.JobResult;
import org.noc.hccp.platform.common.job.JobScheduleConfiguration;
import org.noc.hccp.platform.service.ApplicationJob;
import org.noc.hccp.platform.service.Connector;
import org.noc.hccp.platform.service.ConnectorManager;
import org.noc.hccp.platform.service.ScheduledJob;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.List;

@JobScheduleConfiguration(
        path = "/政府债券/定时任务/自动通知核心生成明细文件",
        code = "com.dlyd.application.gbm.service.job.AutomaticNotify",
        name = "自动通知核心生成明细文件定时任务"
)
public class AutomaticNotifyJob extends ApplicationJob implements ScheduledJob {

  @Autowired
  private TransactionReportingService transactionReportingService;
  @Autowired
  private ConnectorManager connectorManager;

  @Autowired
  private SysXtcsDAO sysXtcsDAO;


  @Override
  protected void start(JobParameters jobParameters) {
    try {
      SysXtcsDO sysXtcsDO = sysXtcsDAO.select(AppConst.APP_ID);
      String yesterday = DateKit.yesterday(sysXtcsDO.getXtrq(), "yyyyMMdd");
      List<ReportingAccount> reportingAccounts = transactionReportingService.listNormalReportingAccount();
      if (reportingAccounts == null || reportingAccounts.size() == 0) {
        finish(JobResult.FAIL, "通知核心生成[" + yesterday + "]明细文件失败");
        PLOG.normal.info("通知核心生成[{}]明细文件失败，无符合条件的账号", yesterday);
        return;
      }
      ESBMessage.Builder builder = EsbHeader.createRequestBuilerHeader(getPrimaryTraceNo(), "1220", "2013");
      builder.setSH_ServiceCode("SVR_FILE");
      builder.setField(Field.createStringField("START_DATE", 8, yesterday));
      builder.setField(Field.createStringField("END_DATE", 8, yesterday));
      builder.setField(Field.createStringField("TRAN_TYPE", 8, "4"));
      Array array = Array.create("ACCT_INFO");
      for (ReportingAccount reportingAccount : reportingAccounts) {
        Struct newStruct = array.newStruct();
        newStruct.addData(Field.createStringField("BASE_ACCT_NO", 8, reportingAccount.getAccountNumber()));
        newStruct.addData(Field.createStringField("ACCT_TYPE", 8, reportingAccount.getAccountType()));
        newStruct.addData(Field.createStringField("CCY", 8, "CNY"));
      }
      builder.setArray(array);
      Connector esbConnector = connectorManager.acquireConnector("ESB");
      ConnectorContext<ESBMessage> cc = createConnectorContext(ESBMessage.class);
      cc.setOutboundPayloadSupplier(() -> builder.build());
      cc.setInboundPayloadConsumer((inboundMessage) -> notifySuccess(yesterday, inboundMessage));
      cc.setExceptionHandler((throwable) -> notifyFailed(yesterday, throwable));
      esbConnector.submit(cc);
    } catch (ParseException e) {
      finish(JobResult.FAIL, "通知核心生成明细文件失败");
      PLOG.normal.info("通知核心生成明细文件失败");
      PLOG.normal.error(e.getMessage(), e);
    }
  }

  private void notifyFailed(String date, Throwable throwable) {
    // 查询失败，登记失败记录，等待人工处理
    PLOG.normal.error("通知核心生成[" + date + "]明细文件失败");
    PLOG.normal.error(throwable.getMessage(), throwable);
    finish(JobResult.FAIL, "通知核心生成[" + date + "]明细文件失败");
  }

  private void notifySuccess(String date, ESBMessage inboundMessage) {
    ESBMessage.Visitor responseVisitor = inboundMessage.getVisitor();
    String retCode = responseVisitor.getSH_Ret().getStructs().get(0).getField("RET_CODE").getValue();
    String retMsg = responseVisitor.getSH_Ret().getStructs().get(0).getField("RET_MSG").getValue();
    PLOG.normal.info("通知核心生产[{}]对账文件，[{}]{}", date, retCode, retMsg);
    finish(JobResult.SUCCESS, "通知核心生成[" + date + "]明细文件成功");
  }
}
