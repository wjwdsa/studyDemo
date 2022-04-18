package com.dlyd.application.gbm.service.dao;

public interface ReportingBatchDAO {

  ReportingBatch select(int date, int no);

  void insert(ReportingBatch batch);

  void end(int date, int no, int status);

  void delete(int date);
}
