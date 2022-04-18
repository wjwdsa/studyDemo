package com.dlyd.application.lib.cryptography;

import java.lang.SecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;

class RSASignatureImpl implements Signature {

  private String algorithmName = null;
  private PrivateKey privateKey = null;
  private PublicKey publicKey = null;

  void setAlgorithmName(String algorithmName) {
    this.algorithmName = algorithmName;
  }

  void setPrivateKey(PrivateKey privateKey) {
    this.privateKey = privateKey;
  }

  void setPublicKey(PublicKey publicKey) {
    this.publicKey = publicKey;
  }

  @Override
  public byte[] sign(byte[] source) {
    try {
      java.security.Signature signature = java.security.Signature.getInstance(algorithmName);
      signature.initSign(privateKey);
      signature.update(source);
      return signature.sign();
    } catch (Throwable e) {
      throw new SecurityException("Sign Error:" + e.getMessage(), e);
    }
  }

  @Override
  public boolean verify(byte[] source, byte[] sign) {
    try {
      java.security.Signature signature = java.security.Signature.getInstance(algorithmName);
      signature.initVerify(publicKey);
      signature.update(source);
      return signature.verify(sign);
    } catch (Throwable e) {
      throw new SecurityException("Verify Error:" + e.getMessage(), e);
    }
  }
}
