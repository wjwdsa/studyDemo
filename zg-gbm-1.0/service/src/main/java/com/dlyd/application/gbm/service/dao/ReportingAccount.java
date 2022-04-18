package com.dlyd.application.gbm.service.dao;

public class ReportingAccount {
  public static final int STATUS_NORMAL = 1;
  public static final int STATUS_DELETE = 2;
  private String accountNumber;
  private String accountType;
  private String documentReferenceNo;
  private int status;
  private int addDate;
  private int deleteDate;

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public String getDocumentReferenceNo() {
    return documentReferenceNo;
  }

  public void setDocumentReferenceNo(String documentReferenceNo) {
    this.documentReferenceNo = documentReferenceNo;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getAddDate() {
    return addDate;
  }

  public void setAddDate(int addDate) {
    this.addDate = addDate;
  }

  public int getDeleteDate() {
    return deleteDate;
  }

  public void setDeleteDate(int deleteDate) {
    this.deleteDate = deleteDate;
  }
}
