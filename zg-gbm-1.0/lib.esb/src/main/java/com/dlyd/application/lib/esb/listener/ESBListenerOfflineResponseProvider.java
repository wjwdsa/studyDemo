package com.dlyd.application.lib.esb.listener;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.noc.hccp.platform.service.ByteBufListenerOfflineResponseProvider;

public class ESBListenerOfflineResponseProvider extends ByteBufListenerOfflineResponseProvider {

  private static final byte[] BC0402 = "0402".getBytes();
  private static final byte[] B0XFF = {-1};

  @Override
  protected ByteBuf createResponse(byte[] request) {
    /* TODO compose Offline Response ESBMessage bytes here
    ByteBuf buf = Unpooled.directBuffer(8 + 60 + 4 + 1);
    buf.writeBytes(SizeUtils.longToString8(64));
    buf.writerIndex(8 + 46);
    buf.writeByte(0x31);
    buf.writerIndex(8 + 60);
    buf.writeBytes(BC0402);
    buf.writeBytes(B0XFF);
    return buf;
    */
    return Unpooled.EMPTY_BUFFER;
  }
}
