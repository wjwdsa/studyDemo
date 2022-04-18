package com.dlyd.application.lib.cryptography;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.util.Base64Utils;

public class PEMStringPublicKeyProvider extends AbstractFactoryBean<PublicKey> implements PublicKeyProvider, InitializingBean {

  private PublicKey publicKey;
  private String pem;

  public void setPem(String pem) {
    this.pem = pem;
  }

  @Override
  public PublicKey getKey() {
    return publicKey;
  }

  @Override
  public Class<?> getObjectType() {
    return PublicKey.class;
  }

  @Override
  protected PublicKey createInstance() throws Exception {
    return publicKey;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    byte[] encoded = Base64Utils.decodeFromString(pem);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
    publicKey = keyFactory.generatePublic(keySpec);
    super.afterPropertiesSet();
  }
}
