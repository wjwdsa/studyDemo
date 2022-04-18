package com.dlyd.application.lib.cryptography;

import java.security.cert.Certificate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class KeyStoreCertificateProvider extends AbstractFactoryBean<Certificate> implements CertificateProvider, InitializingBean {

  private KeyStoreProvider keyStoreProvider;
  private String alias = null;
  private Certificate certificate = null;

  public void setKeyStoreProvider(KeyStoreProvider keyStoreProvider) {
    this.keyStoreProvider = keyStoreProvider;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    if (keyStoreProvider == null) {
      throw new SecurityException("KeyStorePrivateKeyProvider: must provide a KeyStoreProvider");
    }
    if (StringUtils.trimToNull(alias) == null) {
      throw new SecurityException("KeyStorePrivateKeyProvider: must provide an alias of the Certificate");
    }
    try {
      certificate = (Certificate) keyStoreProvider.getStore().getCertificate(alias);
    } catch (Throwable e) {
      throw new SecurityException("get Certificate from KeyStore error:" + e.getMessage(), e);
    }
    super.afterPropertiesSet();
  }

  @Override
  public Certificate getCertificate() {
    return certificate;
  }

  @Override
  public Class<?> getObjectType() {
    return Certificate.class;
  }

  @Override
  protected Certificate createInstance() throws Exception {
    return certificate;
  }
}
