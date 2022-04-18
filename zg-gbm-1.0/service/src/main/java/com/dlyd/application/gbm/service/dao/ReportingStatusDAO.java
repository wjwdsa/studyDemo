package com.dlyd.application.gbm.service.dao;

public interface ReportingStatusDAO {

  void insert(ReportingStatus status);

  ReportingStatus select(int date);

  void update(int date, int stage, int status);

  int iagBatchNo(int date);

  void delete(int date);
}
