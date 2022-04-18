package com.dlyd.application.lib.webservice;

import org.noc.hccp.platform.common.msg.MessageException;

public class WsMessageException extends MessageException {

  public WsMessageException() {
  }

  public WsMessageException(String s) {
    super(s);
  }

  public WsMessageException(String s, Throwable throwable) {
    super(s, throwable);
  }

  public WsMessageException(Throwable throwable) {
    super(throwable);
  }
}
