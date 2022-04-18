package com.dlyd.application.gbm.service.dao;

import com.dlyd.application.gbm.service.KtkjInfo;

public class ReportingDetail {

  public static final int STAGE_LOADED = 1;
  public static final int STAGE_REPORTED = 2;
  public static final int STAGE_DELETED = 3;

  public static final int STATUS_RUNNING = 1;
  public static final int STATUS_COMPLETE = 2;

  private int ctlDate;
  private int ctlStage;
  private int ctlStatus;
  private int ctlLastBatchNo;

  private KtkjInfo info;

  public int getCtlDate() {
    return ctlDate;
  }

  public void setCtlDate(int ctlDate) {
    this.ctlDate = ctlDate;
  }

  public int getCtlStage() {
    return ctlStage;
  }

  public void setCtlStage(int ctlStage) {
    this.ctlStage = ctlStage;
  }

  public int getCtlStatus() {
    return ctlStatus;
  }

  public void setCtlStatus(int ctlStatus) {
    this.ctlStatus = ctlStatus;
  }

  public int getCtlLastBatchNo() {
    return ctlLastBatchNo;
  }

  public void setCtlLastBatchNo(int ctlLastBatchNo) {
    this.ctlLastBatchNo = ctlLastBatchNo;
  }

  public KtkjInfo getInfo() {
    return info;
  }

  public void setInfo(KtkjInfo info) {
    this.info = info;
  }
}
