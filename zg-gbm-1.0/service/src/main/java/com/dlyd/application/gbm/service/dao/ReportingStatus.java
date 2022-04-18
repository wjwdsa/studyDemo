package com.dlyd.application.gbm.service.dao;

public class ReportingStatus {
  public static final int STAGE_LOADING = 1;  // 从核心获取明细
  public static final int STAGE_REPORTING = 2; // 推送
  public static final int STAGE_FINISH = 3; // 完成

  public static final int STATUS_RUNNING = 1;
  public static final int STATUS_COMPLETE = 2;
  public static final int STATUS_FAILED = 3;

  private int date;
  private int stage;
  private int status;
  private int currentBatchNo;

  public int getDate() {
    return date;
  }

  public void setDate(int date) {
    this.date = date;
  }

  public int getStage() {
    return stage;
  }

  public void setStage(int stage) {
    this.stage = stage;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getCurrentBatchNo() {
    return currentBatchNo;
  }

  public void setCurrentBatchNo(int currentBatchNo) {
    this.currentBatchNo = currentBatchNo;
  }
}
