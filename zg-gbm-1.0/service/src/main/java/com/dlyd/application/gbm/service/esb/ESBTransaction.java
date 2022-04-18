package com.dlyd.application.gbm.service.esb;

import com.dlyd.application.lib.cryptography.RSAEncryption;
import com.dlyd.application.lib.cryptography.Signature;
import com.dlyd.application.lib.esb.msg11.ESBMessage;
import org.noc.hccp.platform.common.msg.Message;
import org.noc.hccp.platform.common.tx.TransactionResult;
import org.noc.hccp.platform.service.ApplicationTransaction;

public abstract class ESBTransaction extends ApplicationTransaction<ESBMessage> {

  private Signature signature;
  private RSAEncryption rsaEncryption;

  void setSignature(Signature signature) {
    this.signature = signature;
  }

  void setRsaEncryption(RSAEncryption rsaEncryption) {
    this.rsaEncryption = rsaEncryption;
  }

  public Signature getSignature() {
    return signature;
  }

  public RSAEncryption getRsaEncryption() {
    return rsaEncryption;
  }


  @Override
  protected void determineTransactionResult(ESBMessage finishMessage) {
    if ("00".equals(finishMessage.getVisitor().getSH_RetStatus())) {
      setTransactionResult(TransactionResult.SUCCESS);
    } else {
      setTransactionResult(TransactionResult.FAIL);
    }
  }

//  @Override
//  public void finish(Message message, String s) {
//    super.finish(message, s);
//  }


}

