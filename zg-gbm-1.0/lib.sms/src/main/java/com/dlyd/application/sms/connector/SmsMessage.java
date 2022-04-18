package com.dlyd.application.sms.connector;

import org.noc.hccp.platform.common.msg.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SmsMessage extends Message {

  private int code;
  private String msg;
  private String uuid;

  private String batchName;
  private List<String> items = new ArrayList<>();
  private String content;

  private String url = "/api/message/mass/send";

  String getUrl() {
    return url;
  }

  /**
   * 请求url，不需包含ip地址和端口部分，默认为：/api/message/mass/send
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * 获取响应码
   */
  public int getCode() {
    return code;
  }

  void setCode(int code) {
    this.code = code;
  }

  /**
   * 获取响应信息
   */
  public String getMsg() {
    return msg;
  }

  void setMsg(String msg) {
    this.msg = msg;
  }

  /**
   * 获取响应UUID
   */
  public String getUuid() {
    return uuid;
  }

  void setUuid(String uuid) {
    this.uuid = uuid;
  }

  String getBatchName() {
    return batchName;
  }

  /**
   * 设置batchName批次名称
   */
  public void setBatchName(String batchName) {
    this.batchName = batchName;
  }

  List<String> getItems() {
    return items;
  }

  /**
   * 添加目的手机号码
   */
  public void addItem(String item) {
    this.items.add(item);
  }

  /**
   * 添加目的手机号码
   */
  public boolean addAll(Collection<String> c) {
    return items.addAll(c);
  }

  String getContent() {
    return content;
  }

  /**
   * 设置发送内容
   */
  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public Message clone() {
    return null;
  }

  @Override
  public Message empty() {
    return null;
  }

  @Override
  public String toString() {
    return "SmsMessage{" +
            "code=" + code +
            ", msg='" + msg + '\'' +
            ", uuid='" + uuid + '\'' +
            ", batchName='" + batchName + '\'' +
            ", items=" + items +
            ", content='" + content + '\'' +
            ", url='" + url + '\'' +
            '}';
  }
}
