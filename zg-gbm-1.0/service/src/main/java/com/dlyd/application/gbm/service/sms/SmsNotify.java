package com.dlyd.application.gbm.service.sms;

import com.dlyd.application.gbm.service.esb.ESBTransaction;
import com.dlyd.application.lib.esb.msg11.ESBMessage;
import com.dlyd.application.sms.SmsConfig;
import com.dlyd.application.sms.connector.SmsMessage;
import org.noc.hccp.platform.common.PLOG;
import org.noc.hccp.platform.common.ctx.ConnectorContext;
import org.noc.hccp.platform.common.tx.PlatformTransaction;
import org.noc.hccp.platform.service.Connector;
import org.noc.hccp.platform.service.ConnectorManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 短信通知
 */
@PlatformTransaction("TX:NotifySms")
public class SmsNotify extends ESBTransaction {

  @Autowired
  private ConnectorManager connectorManager;
  @Autowired
  private SmsConfig smsConfig;

  @Override
  protected String getTransactionCode() {
    return "FailSms";
  }

  @Override
  protected String getTransactionName() {
    return "预警短信";
  }

  @Override
  protected void start(ESBMessage esbMessage) {
    ESBMessage close = esbMessage.close();
    finish(close, "finish");

    final SmsMessage smsMessage = new SmsMessage();
    smsMessage.setContent("政府债券项目库及全生命周期管理,交易明细数据推送失败");
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
