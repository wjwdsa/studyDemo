package com.dlyd.application.lib.sms.config;

import org.noc.hccp.configuration.ConfigurationDataException;
import org.noc.hccp.configuration.ManagedConfigurationField;
import org.noc.hccp.configuration.annotation.ConfigurationInstance;
import org.noc.hccp.configuration.annotation.ConfigurationValue;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@ConfigurationInstance(impl = SmsNotifySetUp.class)
public class SmsNotifySetUpImpl extends org.noc.hccp.configuration.ConfigurationInstance implements SmsNotifySetUp {

  private static final String WARN_PHONE = "WARN_PHONE";

  private String warnPhone;


  public SmsNotifySetUpImpl() {

  }


  @Override
  @ConfigurationValue(code = WARN_PHONE, title = "预警电话(多个电话之间以|分割)", order = 8)
  public String getWarnPhone() {
    return warnPhone;
  }

  public void setWarnPhone(String warnPhone) {
    this.warnPhone = warnPhone;
  }


  @Override
  public void setConfigurableEntityFieldValues(List<ManagedConfigurationField> list) {
    for (ManagedConfigurationField mcf : list) {

      if (WARN_PHONE.equals(mcf.getCode())) {
        mcf.setValue(warnPhone);
        continue;
      }
    }
  }

  @Override
  public void updateFieldValues(Map<String, Object> map) throws ConfigurationDataException {
    if (map != null) {
      Iterator<Entry<String, Object>> ime = map.entrySet().iterator();
      while (ime.hasNext()) {
        Entry<String, Object> me = ime.next();
        String code = me.getKey();
        String value = String.valueOf(me.getValue());
        if (WARN_PHONE.equals(code)) {
          this.warnPhone = (String) value;
          continue;
        }
      }
    }
  }

  @Override
  public void delete() {

  }

}
