package com.dlyd.application.lib.cryptography;

import java.security.PrivateKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class KeyStorePrivateKeyProvider extends AbstractFactoryBean<PrivateKey> implements PrivateKeyProvider, InitializingBean {

  private KeyStoreProvider keyStoreProvider;
  private String alias = null;
  private Password password = null;
  private PrivateKey privateKey = null;

  public void setKeyStoreProvider(KeyStoreProvider keyStoreProvider) {
    this.keyStoreProvider = keyStoreProvider;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public void setPassword(Password password) {
    this.password = password;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    if (keyStoreProvider == null) {
      throw new SecurityException("KeyStorePrivateKeyProvider: must provide a KeyStoreProvider");
    }
    if (StringUtils.trimToNull(alias) == null) {
      throw new SecurityException("KeyStorePrivateKeyProvider: must provide an alias of the PrivateKey");
    }
    if (password == null) {
      throw new SecurityException("KeyStorePrivateKeyProvider: must provide an password of the PrivateKey");
    }
    try {
      privateKey = (PrivateKey) keyStoreProvider.getStore().getKey(alias, password.getPassword());
      if (privateKey == null) {
        throw new SecurityException("PrivateKey not exists in KeyStore with alias:" + alias);
      }
    }
    catch (SecurityException se) {
      throw se;
    }
    catch (Throwable e) {
      throw new SecurityException("get PrivateKey from KeyStore error:" + e.getMessage(), e);
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
