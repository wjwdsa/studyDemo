package com.dlyd.application.lib.cryptography;

import java.lang.SecurityException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class CertificateExportedPublicKeyProvider extends AbstractFactoryBean<PublicKey> implements PublicKeyProvider, InitializingBean {

  private Certificate certificate;
  private PublicKey publicKey;

  public void setCertificate(Certificate certificate) {
    this.certificate = certificate;
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
    if (certificate == null) {
      throw new SecurityException("KeyStorePrivateKeyProvider: must provide a Certificate");
    }
    publicKey = certificate.getPublicKey();
    super.afterPropertiesSet();
  }
}
