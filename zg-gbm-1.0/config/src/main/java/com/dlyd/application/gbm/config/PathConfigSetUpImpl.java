package com.dlyd.application.gbm.config;


import org.noc.hccp.configuration.ConfigurationDataException;
import org.noc.hccp.configuration.ManagedConfigurationField;
import org.noc.hccp.configuration.annotation.ConfigurationInstance;
import org.noc.hccp.configuration.annotation.ConfigurationValue;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@ConfigurationInstance(impl = PathConfigSetUp.class)
public class PathConfigSetUpImpl extends org.noc.hccp.configuration.ConfigurationInstance implements PathConfigSetUp {

  /**
   * 应用用户路径
   */
  private static final String APP_HOME_PATH = "APP_HOME_PATH";
  /**
   * NAS核心共享上传路径
   */

  private static final String NAS_CBS_SHARE_UPLOAD_PATH = "NAS_CBS_SHARE_UPLOAD_PATH";
  /**
   * NAS核心共享下载路径
   */
  private static final String NAS_CBS_SHARE_DOWNLOAD_PATH = "NAS_CBS_SHARE_DOWNLOAD_PATH";
  /**
   * NAS社保上传文件共享路径
   */
  private static final String NAS_SI_SHARE_UPLOAD_PATH = "NAS_SI_SHARE_UPLOAD_PATH";
  /**
   * NAS社保下载文件共享路径
   */
  private static final String NAS_SI_SHARE_DOWNLOAD_PATH = "NAS_SI_SHARE_DOWNLOAD_PATH";

  private String appHomePath;
  private String nasCbsShareUploadPath;
  private String nasCbsShareDownloadPath;
  private String nasSiShareUploadPath;
  private String nasSiShareDownloadPath;

  @Override
  @ConfigurationValue(code = APP_HOME_PATH, title = "应用用户路径", order = 1)
  public String getAppHomePath() {
    return appHomePath;
  }

  public void setAppHomePath(String appHomePath) {
    this.appHomePath = appHomePath;
  }

  @Override
  @ConfigurationValue(code = NAS_CBS_SHARE_UPLOAD_PATH, title = "NAS核心共享上传路径", order = 2)
  public String getNasCbsShareUploadPath() {
    return nasCbsShareUploadPath;
  }

  public void setNasCbsShareUploadPath(String nasCbsShareUploadPath) {
    this.nasCbsShareUploadPath = nasCbsShareUploadPath;
  }

  @Override
  @ConfigurationValue(code = NAS_CBS_SHARE_DOWNLOAD_PATH, title = "NAS核心共享下载路径", order = 3)
  public String getNasCbsShareDownloadPath() {
    return nasCbsShareDownloadPath;
  }

  public void setNasCbsShareDownloadPath(String nasCbsShareDownloadPath) {
    this.nasCbsShareDownloadPath = nasCbsShareDownloadPath;
  }

  @Override
  @ConfigurationValue(code = NAS_SI_SHARE_UPLOAD_PATH, title = "NAS政府债券上传文件共享路径", order = 4)
  public String getNasSiShareUploadPath() {
    return nasSiShareUploadPath;
  }

  public void setNasSiShareUploadPath(String nasSiShareUploadPath) {
    this.nasSiShareUploadPath = nasSiShareUploadPath;
  }

  @Override
  @ConfigurationValue(code = NAS_SI_SHARE_DOWNLOAD_PATH, title = "NAS政府债券下载文件共享路径", order = 5)
  public String getNasSiShareDownloadPath() {
    return nasSiShareDownloadPath;
  }

  public void setNasSiShareDownloadPath(String nasSiShareDownloadPath) {
    this.nasSiShareDownloadPath = nasSiShareDownloadPath;
  }

  @Override
  public void setConfigurableEntityFieldValues(List<ManagedConfigurationField> list) {
    for (ManagedConfigurationField mcf : list) {
      if (APP_HOME_PATH.equals(mcf.getCode())) {
        mcf.setValue(appHomePath);
        continue;
      }
      if (NAS_CBS_SHARE_UPLOAD_PATH.equals(mcf.getCode())) {
        mcf.setValue(nasCbsShareUploadPath);
        continue;
      }
      if (NAS_CBS_SHARE_DOWNLOAD_PATH.equals(mcf.getCode())) {
        mcf.setValue(nasCbsShareDownloadPath);
        continue;
      }
      if (NAS_SI_SHARE_UPLOAD_PATH.equals(mcf.getCode())) {
        mcf.setValue(nasSiShareUploadPath);
        continue;
      }
      if (NAS_SI_SHARE_DOWNLOAD_PATH.equals(mcf.getCode())) {
        mcf.setValue(nasSiShareDownloadPath);
        continue;
      }
    }
  }

  @Override
  public void updateFieldValues(Map<String, Object> map) throws ConfigurationDataException {
    if (map != null) {
      Iterator<Map.Entry<String, Object>> ime = map.entrySet().iterator();
      while (ime.hasNext()) {
        Map.Entry<String, Object> me = ime.next();
        String code = me.getKey();
        String value = String.valueOf(me.getValue());
        if (APP_HOME_PATH.equals(code)) {
          this.appHomePath = value;
          continue;
        }
        if (NAS_CBS_SHARE_UPLOAD_PATH.equals(code)) {
          this.nasCbsShareUploadPath = value;
          continue;
        }
        if (NAS_CBS_SHARE_DOWNLOAD_PATH.equals(code)) {
          this.nasCbsShareDownloadPath = value;
          continue;
        }
        if (NAS_SI_SHARE_UPLOAD_PATH.equals(code)) {
          this.nasSiShareUploadPath = value;
          continue;
        }
        if (NAS_SI_SHARE_DOWNLOAD_PATH.equals(code)) {
          this.nasSiShareDownloadPath = value;
          continue;
        }
      }
    }
  }

  @Override
  public void delete() {

  }
}
