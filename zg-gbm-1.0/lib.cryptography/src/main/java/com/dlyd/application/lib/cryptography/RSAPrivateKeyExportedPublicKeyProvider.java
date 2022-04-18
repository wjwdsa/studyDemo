package com.dlyd.application.lib.cryptography;

import java.lang.SecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPublicKeySpec;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class RSAPrivateKeyExportedPublicKeyProvider extends AbstractFactoryBean<PublicKey> implements PublicKeyProvider, InitializingBean {

  private PrivateKey privateKey;
  private PublicKey publicKey;

  public RSAPrivateKeyExportedPublicKeyProvider(PrivateKey privateKey) {
    this.privateKey = privateKey;
  }

  public void setPrivateKey(PrivateKey privateKey) {
    this.privateKey = privateKey;
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
    if (privateKey == null) {
      throw new SecurityException("KeyStorePrivateKeyProvider: must provide a PrivateKey");
    }
    if (privateKey instanceof RSAPrivateCrtKey) {
      RSAPrivateCrtKey privk = (RSAPrivateCrtKey) privateKey;
      RSAPublicKeySpec publicKeySpec = new java.security.spec.RSAPublicKeySpec(privk.getModulus(), privk.getPublicExponent());
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      publicKey = keyFactory.generatePublic(publicKeySpec);
      super.afterPropertiesSet();
    } else {
      throw new SecurityException("KeyStorePrivateKeyProvider: must be a RSA PrivateKey");
    }
  }
}
