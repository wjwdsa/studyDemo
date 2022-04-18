package com.dlyd.application.gbm.config;

import org.noc.hccp.configuration.JSONConfigurationAgent;
import org.noc.hccp.configuration.annotation.ConfigurationAgent;
import org.springframework.stereotype.Component;

@Component("application.gbm.config.configurationAgent")
@ConfigurationAgent
public class ConfigurationAgentImpl extends JSONConfigurationAgent {

  public ConfigurationAgentImpl() {
    register(PathConfigSetUpImpl.class);
  }

}
