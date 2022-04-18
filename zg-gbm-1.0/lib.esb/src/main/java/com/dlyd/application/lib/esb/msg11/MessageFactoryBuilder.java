package com.dlyd.application.lib.esb.msg11;

import org.noc.hccp.platform.common.msg.ByteMessageFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class MessageFactoryBuilder extends AbstractFactoryBean<ByteMessageFactory> {

  @Override
  public Class<?> getObjectType() {
    return ByteMessageFactory.class;
  }

  @Override
  protected ByteMessageFactory createInstance() throws Exception {

    ByteMessageFactory packageFactory = new MessageFactoryImpl();
    return packageFactory;
  }
}
