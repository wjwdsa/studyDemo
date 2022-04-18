package com.dlyd.application.sms;

import com.dlyd.application.lib.sms.config.SmsNotifySetUp;
import org.apache.commons.lang3.StringUtils;
import org.noc.hccp.configuration.AbstractDynamicConfigurable;
import org.noc.hccp.configuration.ConfigurationInstance;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class SmsConfig extends AbstractDynamicConfigurable {
  public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
  private SmsNotifySetUp smsNotifySetUp;

  private Set<String> phone = new HashSet<>();


  @Override
  protected Class<?> getConfigurationClass() {
    return SmsNotifySetUp.class;
  }

  @Override
  protected String getConfigurationCode() {
    return "service.sms.config";
  }

  @Override
  protected String getConfigurationPath() {
    return "/预警参数配置";
  }

  @Override
  public void refreshConfiguration(ConfigurationInstance configurationInstance) {
    if (configurationInstance instanceof SmsNotifySetUp) {
      smsNotifySetUp = (SmsNotifySetUp) configurationInstance;
      phone.clear();
      for (String code : StringUtils.split(smsNotifySetUp.getWarnPhone(), "|")) {
        phone.add(code);
      }
    }
  }

  @Override
  public void invalidateConfiguration() {

  }

  public Set<String> getPhone() {
    return phone;
  }

}
