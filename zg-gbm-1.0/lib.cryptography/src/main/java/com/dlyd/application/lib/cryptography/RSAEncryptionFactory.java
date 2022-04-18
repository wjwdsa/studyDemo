package com.dlyd.application.lib.cryptography;

import java.security.PrivateKey;
import java.security.PublicKey;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class RSAEncryptionFactory extends AbstractFactoryBean<RSAEncryption> implements InitializingBean {

  private PrivateKey privateKey;
  private PublicKey publicKey;

  public void setPrivateKey(PrivateKey privateKey) {
    this.privateKey = privateKey;
  }

  public void setPublicKey(PublicKey publicKey) {
    this.publicKey = publicKey;
  }

  @Override
  public Class<?> getObjectType() {
    return RSAEncryption.class;
  }

  @Override
  protected RSAEncryption createInstance() throws Exception {
    RSAEncryptionImpl impl = new RSAEncryptionImpl();
    impl.setPrivateKey(privateKey);
    impl.setPublicKey(publicKey);
    return impl;
  }
}
