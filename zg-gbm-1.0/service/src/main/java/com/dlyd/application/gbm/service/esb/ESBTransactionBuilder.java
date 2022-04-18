package com.dlyd.application.gbm.service.esb;

import com.dlyd.application.gbm.service.dao.SysTranCodeDAO;
import com.dlyd.application.gbm.service.domain.SysTranCodeDO;
import com.dlyd.application.lib.cryptography.RSAEncryption;
import com.dlyd.application.lib.cryptography.Signature;
import com.dlyd.application.lib.esb.msg11.ESBMessage;
import org.noc.hccp.platform.common.PLOG;
import org.noc.hccp.platform.service.Transaction;
import org.noc.hccp.platform.service.TransactionBuilder;
import org.noc.hccp.platform.service.TransactionInstanceProvider;

public class ESBTransactionBuilder implements TransactionBuilder<ESBMessage> {


  private SysTranCodeDAO sysTranCodeDAO;

  private Signature signature;
  private RSAEncryption rsaEncryption;

  public void setSysTranCodeDAO(SysTranCodeDAO sysTranCodeDAO) {
    this.sysTranCodeDAO = sysTranCodeDAO;
  }

  public void setSignature(Signature signature) {
    this.signature = signature;
  }

  public void setRsaEncryption(RSAEncryption rsaEncryption) {
    this.rsaEncryption = rsaEncryption;
  }

  private TransactionInstanceProvider transactionInstanceProvider;

  public void setTransactionInstanceProvider(TransactionInstanceProvider transactionInstanceProvider) {
    this.transactionInstanceProvider = transactionInstanceProvider;
  }


  @Override
  public Transaction createTransaction(ESBMessage message) {
    String sourceType = message.getVisitor().getSH_SourceType();
    String messageType = message.getVisitor().getSH_MessageType();
    String messageCode = message.getVisitor().getSH_MessageCode();

    SysTranCodeDO tranCodeDO = sysTranCodeDAO.select(sourceType, messageType, messageCode);
    PLOG.normal.info("交易请求渠道编号:[{}]，消息类型[{}],消息编码[{}]，平台交易代码:[{}]{}", sourceType, messageType, messageCode, tranCodeDO.getTranCode(), tranCodeDO.getDescription());

    ESBTransaction transaction = transactionInstanceProvider.getTransactionInstance(tranCodeDO.getTranCode());
    transaction.setSignature(signature);
    transaction.setRsaEncryption(rsaEncryption);

    PLOG.normal.debug(String.format("TRACE_SESSION createTX:%s bean:%s", message.getMessageContext().getPrimaryTraceNo(), transaction.toString()));
    return (Transaction) transaction;
  }

}
