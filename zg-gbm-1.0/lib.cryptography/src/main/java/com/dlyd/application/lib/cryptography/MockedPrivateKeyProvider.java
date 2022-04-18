package com.dlyd.application.lib.cryptography;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class MockedPrivateKeyProvider extends AbstractFactoryBean<PrivateKey> implements PrivateKeyProvider, InitializingBean {

  private PrivateKey privateKey = null;


  @Override
  public void afterPropertiesSet() throws Exception {
    try {
      KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
      KeyPair kp = kpg.genKeyPair();
      privateKey = kp.getPrivate();
    } catch (Throwable e) {
      throw new SecurityException("mocking PrivateKey error:" + e.getMessage(), e);
    }
    super.afterPropertiesSet();
  }

  @Override
  public PrivateKey getKey() {
    return privateKey;
  }

  @Override
  public Class<?> getObjectType() {
    return PrivateKey.class;
  }

  @Override
  protected PrivateKey createInstance() throws Exception {
    return privateKey;
  }
}
