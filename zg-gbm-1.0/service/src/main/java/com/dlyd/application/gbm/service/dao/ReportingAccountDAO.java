package com.dlyd.application.gbm.service.dao;

import java.util.List;

public interface ReportingAccountDAO {

  List<ReportingAccount> listNormal();

  void insert(ReportingAccount a);

  void update(ReportingAccount a);

  void delete(String accountNumber, int date);

  List<ReportingAccount> selectAll();
}
