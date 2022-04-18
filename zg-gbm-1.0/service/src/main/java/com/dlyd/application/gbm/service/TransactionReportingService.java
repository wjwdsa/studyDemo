package com.dlyd.application.gbm.service;

import com.dlyd.application.gbm.service.dao.ReportingAccount;
import com.dlyd.application.gbm.service.dao.ReportingBatch;

import java.text.ParseException;
import java.util.List;
import javax.xml.soap.SOAPMessage;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionReportingService {

  /*
  每日定时任务执行时，初始化当天的记录
   */
  @Transactional
  ReportingBatch initializeDailyReporting(String reportDate) throws ParseException;

  /*
    从核心获取交易明细后，登记交易明细
     */
  @Transactional
  void queryTransactionsSuccess(ReportingBatch batch, List<KtkjInfo> list);

  void queryTransactionsFailed(ReportingBatch batch);

  @Transactional
  boolean reportTransactionsSuccess(ReportingBatch batch, String result);

  @Transactional
  boolean reportErrorTransactionsSuccess(String result);

  void reportTransactionsFailed(ReportingBatch batch);

  SOAPMessage buildReportingMessage(List<KtkjInfo> infoList);

  String getReportingResponse(SOAPMessage response);

  List<ReportingAccount> listNormalReportingAccount();

  List<KtkjInfo> buildErrorReport(List<String> voucherNumbers);
}
