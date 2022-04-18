package com.dlyd.application.gbm.service.ops.gbm;

import com.dlyd.application.gbm.service.KtkjInfo;
import com.dlyd.application.gbm.service.TransactionReportingService;
import com.dlyd.application.gbm.service.common.AppConst;
import com.dlyd.application.gbm.service.common.EsbHeader;
import com.dlyd.application.gbm.service.dao.ReportingBatch;
import com.dlyd.application.gbm.service.esb.ESBTransaction;
import com.dlyd.application.gbm.service.service.SysXtcsService;
import com.dlyd.application.lib.esb.msg11.Array;
import com.dlyd.application.lib.esb.msg11.ESBMessage;
import com.dlyd.application.lib.esb.msg11.Struct;
import com.dlyd.application.lib.webservice.WsSoapMessage;
import org.apache.commons.lang3.StringUtils;
import org.noc.hccp.platform.common.AppLog;
import org.noc.hccp.platform.common.PLOG;
import org.noc.hccp.platform.common.ctx.ConnectorContext;
import org.noc.hccp.platform.common.job.JobResult;
import org.noc.hccp.platform.common.tx.PlatformTransaction;
import org.noc.hccp.platform.service.Connector;
import org.noc.hccp.platform.service.ConnectorManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.soap.SOAPMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * /政府债券/ops重新推送
 */
@PlatformTransaction("OPS:GBM-0001")
public class OPS_GBM_0001 extends ESBTransaction {
  @Autowired
  private ConnectorManager connectorManager;
  @Autowired
  private TransactionReportingService transactionReportingService;

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
    try {
      ESBMessage.Visitor visitor = esbMessage.getVisitor();
      Array voucherNumberArray = visitor.getArray("voucherNumberArray");
      List<String> voucherNumbers = new ArrayList<>();
      List<Struct> structs = voucherNumberArray.getStructs();
      for (Struct struct : structs) {
        String voucherNumber = struct.getField("voucherNumber").getValue();
        voucherNumbers.add(voucherNumber);
      }
      List<KtkjInfo> ktkjInfoList = transactionReportingService.buildErrorReport(voucherNumbers);
      // 调用WS上报明细
      SOAPMessage soapMessage = transactionReportingService.buildReportingMessage(ktkjInfoList);
      WsSoapMessage wsSoapMessage = new WsSoapMessage();
      wsSoapMessage.setSoapMessage(soapMessage);
      wsSoapMessage.setSoapAction("ktkjBankFlowSaveCS");
      wsSoapMessage.setSoapEndpoint("/axis2/services/MyService.MyServiceHttpSoap11Endpoint/");

      Connector wsConnector = connectorManager.acquireConnector("WS");
      ConnectorContext<WsSoapMessage> cc = createConnectorContext(WsSoapMessage.class);
      cc.setOutboundPayloadSupplier(() -> wsSoapMessage);
      cc.setInboundPayloadConsumer((inboundWsSoapMessage) -> reportFinish(esbMessage, inboundWsSoapMessage));
      cc.setExceptionHandler((throwable) -> reportFailed(esbMessage, throwable));
      wsConnector.submit(cc);
    } catch (Exception e) {
      finish(esbMessage, "-1", "程序异常");
      PLOG.normal.error(e.getMessage(),e);
    }
  }


  private void reportFailed(ESBMessage esbMessage, Throwable throwable) {
    finish(esbMessage, "-1", "通讯异常");
    PLOG.normal.error(throwable);
  }

  private void reportFinish(ESBMessage esbMessage, WsSoapMessage inboundWsSoapMessage) {
    try {
      String jsResult = transactionReportingService.getReportingResponse(inboundWsSoapMessage.getSoapMessage());
      transactionReportingService.reportErrorTransactionsSuccess(jsResult);
      finish(esbMessage, "000000", "success");
      PLOG.normal.info("推送核心交易明细成功完成");
    } catch (Exception e) {
      PLOG.normal.error(e.getMessage(),e);
      finish(esbMessage, "-1", e.getMessage());
    }
  }

  private void finish(ESBMessage esbMessage, String code, String msg) {
    ESBMessage.Builder builder = esbMessage.newResponseBuilder();
    builder.setSH_RetStatus("S");
    builder.addSH_Ret(code, msg);
    finish(builder.build(), "finish");
//    finish
  }


}
