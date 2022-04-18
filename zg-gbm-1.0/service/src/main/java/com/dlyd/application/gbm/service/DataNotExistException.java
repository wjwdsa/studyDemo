package com.dlyd.application.gbm.service;

public class DataNotExistException extends DataException {

  public DataNotExistException() {
  }

  public DataNotExistException(String s) {
    super(s);
  }

  public DataNotExistException(String s, Throwable throwable) {
    super(s, throwable);
  }

  public DataNotExistException(Throwable throwable) {
    super(throwable);
  }
}
