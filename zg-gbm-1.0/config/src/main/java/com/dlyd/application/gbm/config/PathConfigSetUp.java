package com.dlyd.application.gbm.config;

public interface PathConfigSetUp {


  /**
   * 获取项目部署的用户目录
   *
   * @return
   */
  String getAppHomePath();

  /**
   * 获取NAS核心上传路径
   *
   * @return
   */
  String getNasCbsShareUploadPath();

  /**
   * 获取NAS核心下载路径
   *
   * @return
   */
  String getNasCbsShareDownloadPath();

  /**
   * 获取NAS前置上传路径
   *
   * @return
   */
  String getNasSiShareUploadPath();

  /**
   * 获取NAS下载上传路径
   *
   * @return
   */
  String getNasSiShareDownloadPath();

}
