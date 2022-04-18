package com.dlyd.application.lib.esb.pm;

import io.netty.channel.ChannelPipeline;
import org.noc.hccp.platform.common.netty.StringLengthFieldBasedFrameDecoder;
import org.noc.hccp.platform.common.netty.StringLengthFieldPrepender;
import org.noc.hccp.platform.service.ServiceConnectorNioTcpChannelInitializer;

public class ServiceConnectorChannelInitializer extends ServiceConnectorNioTcpChannelInitializer {

  @Override
  protected void configPipeline(ChannelPipeline channelPipeline) {
    channelPipeline.addLast(new StringLengthFieldBasedFrameDecoder(Short.MAX_VALUE, 0, 8, 0, 8));
    channelPipeline.addLast(new StringLengthFieldPrepender(8));
  }
}
