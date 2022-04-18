package com.dlyd.application.gbm.service.dao;

import java.sql.Timestamp;

public class ReportingBatch {

  public static final int STATUS_RUNNING = 1;
  public static final int STATUS_FINISH = 2;
  public static final int STATUS_FAILED = 3;
  private int date;
  private int no;
  private int status;
  private Timestamp beginTime;
  private Timestamp endTime;

  public int getDate() {
    return date;
  }

  public void setDate(int date) {
    this.date = date;
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Timestamp getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Timestamp beginTime) {
    this.beginTime = beginTime;
  }

  public Timestamp getEndTime() {
    return endTime;
  }

  public void setEndTime(Timestamp endTime) {
    this.endTime = endTime;
  }
}
