package com.dlyd.application.lib.esb.listener;

import io.netty.channel.ChannelPipeline;
import org.noc.hccp.platform.common.msg.ByteMessageFactory;
import org.noc.hccp.platform.common.msg.MessageToBytesConverterWithMessageFactory;
import org.noc.hccp.platform.common.netty.StringLengthFieldBasedFrameDecoder;
import org.noc.hccp.platform.common.netty.StringLengthFieldPrepender;
import org.noc.hccp.platform.service.ByteBufToBytesDecoder;
import org.noc.hccp.platform.service.ListenerExceptionHandler;
import org.noc.hccp.platform.service.ListenerOfflineResponseProvider;
import org.noc.hccp.platform.service.ServiceListenerNioTcpChannelInitializer;

public class ESBListenerChannelInitializer extends ServiceListenerNioTcpChannelInitializer {

  private ESBListenerOfflineResponseProvider offlineResponseProvider = new ESBListenerOfflineResponseProvider();
  private ESBListenerExceptionHandler exceptionHandler = new ESBListenerExceptionHandler();
  private ByteMessageFactory messageFactory;
  private ByteBufToBytesDecoder byteBufToBytesDecoder = new ByteBufToBytesDecoder();
  //private BytesToMessageConverterWithMessageFactory bytes2MessageConverter = new BytesToMessageConverterWithMessageFactory();
  private MessageToBytesConverterWithMessageFactory message2BytesConverter = new MessageToBytesConverterWithMessageFactory();

  public void setMessageFactory(ByteMessageFactory messageFactory) {
    super.setMessageFactory(messageFactory);
    this.messageFactory = messageFactory;
    message2BytesConverter.setMessageFactory(messageFactory);
    //bytes2MessageConverter.setMessageFactory(packageFactory);
  }

  @Override
  protected ListenerOfflineResponseProvider getOfflineResponseProvider() {
    return offlineResponseProvider;
  }

  @Override
  protected ListenerExceptionHandler getExceptionHandler() {
    return exceptionHandler;
  }

  @Override
  protected void configInboundPipeline(ChannelPipeline pipeline) {
    pipeline.addLast(new StringLengthFieldBasedFrameDecoder(Short.MAX_VALUE, 0, 8, 0, 8));
    pipeline.addLast(byteBufToBytesDecoder);
    //pipeline.addLast(bytes2MessageConverter);
  }

  @Override
  protected void configOutboundPipeline(ChannelPipeline pipeline) {
    pipeline.addLast(new StringLengthFieldPrepender(8));
    pipeline.addLast(message2BytesConverter);
  }
}
