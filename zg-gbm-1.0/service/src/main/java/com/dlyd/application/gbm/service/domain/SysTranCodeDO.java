package com.dlyd.application.gbm.service.domain;

import java.io.Serializable;


/**
 * 系统交易代码表
 * SYS_TRAN_CODE
 *
 * @author yangbing
 * @date 2020-08-18 17:29:37
 */
public class SysTranCodeDO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 请求系统编号
   */
  private String sourceType;
  /**
   * 消息类型
   */
  private String messageType;
  /**
   * 消息编码
   */
  private String messageCode;
  /**
   * 平台交易代码
   */
  private String tranCode;
  /**
   * 说明
   */
  private String description;

  /**
   * 设置：请求系统编号
   */
  public void setSourceType(String sourceType) {
    this.sourceType = sourceType == null ? null : sourceType.trim();
  }

  /**
   * 获取：请求系统编号
   */
  public String getSourceType() {
    return sourceType == null ? null : sourceType.trim();
  }

  /**
   * 设置：消息类型
   */
  public void setMessageType(String messageType) {
    this.messageType = messageType == null ? null : messageType.trim();
  }

  /**
   * 获取：消息类型
   */
  public String getMessageType() {
    return messageType == null ? null : messageType.trim();
  }

  /**
   * 设置：消息编码
   */
  public void setMessageCode(String messageCode) {
    this.messageCode = messageCode == null ? null : messageCode.trim();
  }

  /**
   * 获取：消息编码
   */
  public String getMessageCode() {
    return messageCode == null ? null : messageCode.trim();
  }

  /**
   * 设置：平台交易代码
	 * @return
	 */
  public SysTranCodeDO setTranCode(String tranCode) {
    this.tranCode = tranCode == null ? null : tranCode.trim();
    return this;
  }

  /**
   * 获取：平台交易代码
   */
  public String getTranCode() {
    return tranCode == null ? null : tranCode.trim();
  }

  /**
   * 设置：说明
   *
   * @return
   */
  public SysTranCodeDO setDescription(String description) {
    this.description = description == null ? null : description.trim();
    return this;
  }

  /**
   * 获取：说明
   */
  public String getDescription() {
    return description == null ? null : description.trim();
  }

  @Override
  public String toString() {
    return "SysTranCodeDO{" +
            "sourceType='" + sourceType + '\'' +
            "messageType='" + messageType + '\'' +
            "messageCode='" + messageCode + '\'' +
            "tranCode='" + tranCode + '\'' +
            "description='" + description + '\'' +
            '}';
  }

}
