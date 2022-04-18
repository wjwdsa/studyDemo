package com.dlyd.application.lib.esb.listener;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.noc.hccp.platform.common.AppLog;
import org.noc.hccp.platform.service.ListenerExceptionHandler;


public class ESBListenerExceptionHandler extends ChannelOutboundHandlerAdapter implements ListenerExceptionHandler {

  private static final byte[] BC0300 = "0300".getBytes();
  private static final byte[] B0XFF = {-1};

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    AppLog.logger.error(cause.getMessage(), cause);
    /* TODO compose Response ESBMessage here
    ByteBuf buf = Unpooled.directBuffer(8 + 60 + 4 + 1);
    buf.writeBytes(SizeUtils.longToString8(64));
    buf.writerIndex(8 + 46);
    buf.writeByte(0x31);
    buf.writerIndex(8 + 60);
    buf.writeBytes(BC0300);
    buf.writeBytes(B0XFF);
    */
    ByteBuf buf = Unpooled.EMPTY_BUFFER;
    ctx.writeAndFlush(buf).addListener(ChannelFutureListener.CLOSE);
    ReferenceCountUtil.release(buf);
  }
}
