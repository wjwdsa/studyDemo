package com.dlyd.application.gbm.service.cbs;

import com.dlyd.application.gbm.service.common.AppConst;
import com.dlyd.application.gbm.service.common.EsbHeader;
import com.dlyd.application.gbm.service.esb.ESBTransaction;
import com.dlyd.application.gbm.service.service.SysXtcsService;
import com.dlyd.application.lib.esb.msg11.ESBMessage;
import com.dlyd.application.lib.esb.msg11.Field;
import org.apache.commons.lang3.StringUtils;
import org.noc.hccp.platform.common.AppLog;
import org.noc.hccp.platform.common.tx.PlatformTransaction;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 核心日切通知
 */
@PlatformTransaction("CBS:1000-1001")
public class CBS_1000_1001 extends ESBTransaction {
  @Autowired
  private SysXtcsService sysXtcsService;

  @Override
  protected String getTransactionCode() {
    return null;
  }

  @Override
  protected String getTransactionName() {
    return null;
  }

  @Override
  protected void start(ESBMessage esbMessage) {
    ESBMessage.Visitor visitor = esbMessage.getVisitor();
    String rundate = visitor.getField("RUNDATE").getValue();
    boolean change = sysXtcsService.updateXtrq(AppConst.APP_ID, rundate);
    if (change) {
      AppLog.logger.info("日切成功");
      transactionNotify();
    } else {
      AppLog.logger.info("日切失败");
    }


    ESBMessage.Builder builder = esbMessage.newResponseBuilder();
    builder.setSH_RetStatus("S");
    builder.addSH_Ret("000000", "成功");

    builder.setSH_TranDate(visitor.getSH_TranDate());
    builder.setSH_TranTimestamp(visitor.getSH_TranTimestamp());
    builder.setSH_SeqNo(visitor.getSH_SeqNo());


    builder.setSH_ServiceCode("SVR_PSS");
    builder.setSH_BranchId("001");
    builder.setSH_SourceBranchNo("001");
    builder.setSH_DestBranchNo("001");
    builder.setSH_MessageType("1010");
    builder.setSH_MessageCode("1001");
    finish(builder.build(), "finish");
  }


  private void transactionNotify() {
    ESBMessage.Builder requestBuilerHeader = EsbHeader.createRequestBuilerHeader(StringUtils.EMPTY, "1220", "2013-4");
    launchAsyncTransaction(requestBuilerHeader.build(), null);
  }


}
