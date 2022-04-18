package com.dlyd.application.lib.cryptography;

import java.lang.SecurityException;
import java.security.KeyStore;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

public class Pkcs12KeyStoreProvider implements KeyStoreProvider, InitializingBean {

  private KeyStore keyStore = null;
  private Resource resource = null;
  private Password password = null;

  public void setResource(Resource resource) {
    this.resource = resource;
  }

  public void setPassword(Password password) {
    this.password = password;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    if (resource == null) {
      throw new SecurityException("Must specify the KeyStore Resource");
    }
    if (!resource.exists() || !resource.isReadable()) {
      throw new SecurityException("Bad KeyStore Resource:" + resource.getURI());
    }
    try {
      keyStore = KeyStore.getInstance("PKCS12");
      keyStore.load(resource.getInputStream(), password.getPassword());
    } catch (Throwable e) {
      throw new SecurityException("Load KeyStore failed:" + e.getMessage(), e);
    }
  }

  @Override
  public KeyStore getStore() {
    return keyStore;
  }
}
