package com.dlyd.application.gbm.service.dao;

import com.dlyd.application.gbm.service.KtkjInfo;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface ReportingDetailDAO {

  List<ReportingDetail> list(int date);

  void insert(List<KtkjInfo> infoList, int date, int stage, int status, int batchNo) throws DataAccessException;

  int update(int date, int batchNo, String bankId, int stage, int status);

  int updateCodeAndPrompt(String voucherNumber, String code, String prompt);

  List<ReportingDetail> selectByCtlDate(int date);

  List<ReportingDetail> listByVoucherNumbers(List<String> voucherNumbers);

  void delete(int date);

  void updateSuccess(ReportingBatch batch);

}
