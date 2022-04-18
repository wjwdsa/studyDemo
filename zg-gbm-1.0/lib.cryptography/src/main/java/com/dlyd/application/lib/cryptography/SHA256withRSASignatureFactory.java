package com.dlyd.application.lib.cryptography;

import java.security.PrivateKey;
import java.security.PublicKey;

public class SHA256withRSASignatureFactory extends AbstractSignatureFactory {

  private PrivateKey privateKey = null;
  private PublicKey publicKey = null;

  public void setPrivateKey(PrivateKey privateKey) {
    this.privateKey = privateKey;
  }

  public void setPublicKey(PublicKey publicKey) {
    this.publicKey = publicKey;
  }


    @Override
  protected Signature createInstance() throws Exception {
    RSASignatureImpl impl = new RSASignatureImpl();
    impl.setAlgorithmName("SHA256withRSA");
    impl.setPrivateKey(privateKey);
    impl.setPublicKey(publicKey);
    return impl;
  }
}
