package com.dlyd.application.lib.esb.listener;

import io.netty.channel.ChannelPipeline;
import org.noc.hccp.platform.common.msg.ByteMessageFactory;
import org.noc.hccp.platform.common.msg.BytesToMessageConverterWithMessageFactory;
import org.noc.hccp.platform.common.msg.MessageToBytesConverterWithMessageFactory;
import org.noc.hccp.platform.common.netty.StringLengthFieldBasedFrameDecoder;
import org.noc.hccp.platform.common.netty.StringLengthFieldPrepender;
import org.noc.hccp.platform.service.NioTcpConnectorChannelInitializer;

public class ESBConnectorSocketChannelInitializer extends NioTcpConnectorChannelInitializer {

  private ByteMessageFactory packageFactory;
  private BytesToMessageConverterWithMessageFactory bytes2MessageConverter = new BytesToMessageConverterWithMessageFactory();
  private MessageToBytesConverterWithMessageFactory message2BytesConverter = new MessageToBytesConverterWithMessageFactory();

  public void setMessageFactory(ByteMessageFactory packageFactory) {
    this.packageFactory = packageFactory;
    message2BytesConverter.setMessageFactory(packageFactory);
    bytes2MessageConverter.setMessageFactory(packageFactory);
  }

  @Override
  protected void configInboundPipeline(ChannelPipeline pipeline) {
    pipeline.addLast(new StringLengthFieldPrepender(8));
    pipeline.addLast(new StringLengthFieldBasedFrameDecoder(Short.MAX_VALUE, 0, 8, 0, 8));
    pipeline.addLast(bytes2MessageConverter);
    pipeline.addLast(message2BytesConverter);
  }

  @Override
  protected void configOutboundPipeline(ChannelPipeline pipeline) {
    pipeline.addLast(message2BytesConverter);
  }
}
