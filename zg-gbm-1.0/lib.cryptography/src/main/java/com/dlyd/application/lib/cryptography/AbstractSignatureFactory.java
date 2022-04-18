package com.dlyd.application.lib.cryptography;

import org.springframework.beans.factory.config.AbstractFactoryBean;

public abstract class AbstractSignatureFactory extends AbstractFactoryBean<Signature> {

  @Override
  public Class<?> getObjectType() {
    return Signature.class;
  }
}
