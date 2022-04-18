package com.dlyd.application.gbm.service.common;

import com.dlyd.application.gbm.config.PathConfigSetUp;
import org.noc.hccp.configuration.AbstractDynamicConfigurable;
import org.noc.hccp.configuration.ConfigurationInstance;

public class PathConfig extends AbstractDynamicConfigurable {

  private PathConfigSetUp pathConfigSetUp;

  private String appHomePath;
  private String nasCbsShareUploadPath;
  private String nasCbsShareDownloadPath;
  private String nasSiShareUploadPath;
  private String nasSiShareDownloadPath;

  @Override
  protected Class<?> getConfigurationClass() {
    return PathConfigSetUp.class;
  }

  @Override
  protected String getConfigurationCode() {
    return "service.si.pay.config.pathConfig";
  }

  @Override
  protected String getConfigurationPath() {
    return "/平台/服务/NAS共享路径信息配置";
  }

  @Override
  public void refreshConfiguration(ConfigurationInstance configurationInstance) {
    if (configurationInstance instanceof PathConfigSetUp) {
      pathConfigSetUp = (PathConfigSetUp) configurationInstance;
      appHomePath = pathConfigSetUp.getAppHomePath();
      nasCbsShareUploadPath = pathConfigSetUp.getNasCbsShareUploadPath();
      nasCbsShareDownloadPath = pathConfigSetUp.getNasCbsShareDownloadPath();
      nasSiShareUploadPath = pathConfigSetUp.getNasSiShareUploadPath();
      nasSiShareDownloadPath = pathConfigSetUp.getNasSiShareDownloadPath();
    }
  }

  @Override
  public void invalidateConfiguration() {

  }


  public String getAppHomePath() {
    return appHomePath;
  }

  public String getNasCbsShareUploadPath() {
    return nasCbsShareUploadPath;
  }

  public String getNasCbsShareDownloadPath() {
    return nasCbsShareDownloadPath;
  }

  public String getNasSiShareUploadPath() {
    return nasSiShareUploadPath;
  }

  public String getNasSiShareDownloadPath() {
    return nasSiShareDownloadPath;
  }
}
