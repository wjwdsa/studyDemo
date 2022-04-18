package com.dlyd.application.gbm.service;

import org.noc.hccp.platform.common.PlatformException;

public class DataException extends PlatformException {

  public DataException() {
  }

  public DataException(String s) {
    super(s);
  }

  public DataException(String s, Throwable throwable) {
    super(s, throwable);
  }

  public DataException(Throwable throwable) {
    super(throwable);
  }
}
