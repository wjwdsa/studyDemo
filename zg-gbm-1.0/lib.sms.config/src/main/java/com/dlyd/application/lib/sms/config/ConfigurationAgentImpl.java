package com.dlyd.application.lib.sms.config;

import org.noc.hccp.configuration.JSONConfigurationAgent;
import org.noc.hccp.configuration.annotation.ConfigurationAgent;
import org.springframework.stereotype.Component;

@Component("application.gbm.sms.config.configurationAgent")
@ConfigurationAgent
public class ConfigurationAgentImpl extends JSONConfigurationAgent {

  public ConfigurationAgentImpl() {
    register(SmsNotifySetUpImpl.class);
  }

}
