package com.dlyd.application.lib.esb.pm;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import org.noc.hccp.platform.common.AppLog;
import org.noc.hccp.platform.common.netty.StringLengthFieldBasedFrameDecoder;
import org.noc.hccp.platform.common.netty.StringLengthFieldPrepender;
import org.noc.hccp.platform.service.ListenerExceptionHandler;
import org.noc.hccp.platform.service.ListenerOfflineResponseProvider;
import org.noc.hccp.platform.service.ServiceListenerNioTcpChannelInitializerForPlatformMessage;

public class ServiceListenerChannelInitializer extends ServiceListenerNioTcpChannelInitializerForPlatformMessage {

  @Override
  protected ListenerExceptionHandler getExceptionHandler() {
    return new ListenerExceptionHandler() {
      @Override
      public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) {
        AppLog.logger.error(throwable.getMessage(), throwable);
        channelHandlerContext.channel().close();
      }
    };
  }

  @Override
  protected ListenerOfflineResponseProvider getOfflineResponseProvider() {
    return null;
  }

  @Override
  protected void configInboundPipeline(ChannelPipeline pipeline) {
    pipeline.addLast(new StringLengthFieldBasedFrameDecoder(Short.MAX_VALUE, 0, 8, 0, 8));
  }

  @Override
  protected void configOutboundPipeline(ChannelPipeline pipeline) {
    pipeline.addLast(new StringLengthFieldPrepender(8));
  }
}
