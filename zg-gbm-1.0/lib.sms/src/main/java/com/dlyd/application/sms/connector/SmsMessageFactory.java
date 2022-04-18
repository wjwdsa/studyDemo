package com.dlyd.application.sms.connector;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.HttpHeaderNames;
import org.apache.commons.lang3.StringUtils;
import org.noc.hccp.platform.common.msg.*;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;

public class SmsMessageFactory implements HttpMessageFactory<SmsMessage> {

  private String authorizationUserName;
  private String authorizationPassword;

  public void setAuthorizationUserName(String authorizationUserName) {
    this.authorizationUserName = authorizationUserName;
  }

  public void setAuthorizationPassword(String authorizationPassword) {
    this.authorizationPassword = authorizationPassword;
  }

  @Override
  public SmsMessage fromRequest(HttpRequestHolder requestHolder) {
    throw new UnsupportedOperationException("Not implemented.");
  }

  @Override
  public void toResponse(SmsMessage am, HttpResponseBuilder responseBuilder) {
    throw new UnsupportedOperationException("Not implemented.");
  }

  @Override
  public SmsMessage fromResponse(HttpResponseHolder responseHolder) {
    try {
      SmsMessage message = new SmsMessage();
      JSONObject jo = JSON.parseObject(new String(responseHolder.getContentBytes(), "UTF-8"));
      if (jo.containsKey("code")) {
        message.setCode(jo.getInteger("code"));
      }
      if (jo.containsKey("msg")) {
        message.setMsg(jo.getString("msg"));
      }
      if (jo.containsKey("uuid")) {
        message.setUuid(jo.getString("uuid"));
      }
      return message;
    } catch (Throwable e) {
      throw new MessageException(e.getMessage(), e);
    }
  }

  @Override
  public void toRequest(SmsMessage outboundPayload, HttpRequestBuilder requestBuilder) {
    try {
      JSONObject jo = new JSONObject();
      String batchName = StringUtils.trimToNull(outboundPayload.getBatchName());
      if (batchName != null) {
        jo.put("batchName", batchName);
      }
      JSONArray jaItems = new JSONArray();
      for (String item : outboundPayload.getItems()) {
        JSONObject joItem = new JSONObject();
        joItem.put("to", item);
        jaItems.add(joItem);
      }
      jo.put("items", jaItems);
      jo.put("content", StringUtils.trimToEmpty(outboundPayload.getContent()));
      jo.put("msgType", "sms");
      requestBuilder.setHeader(HttpHeaderNames.ACCEPT, "application/json");
      requestBuilder.setHeader(HttpHeaderNames.AUTHORIZATION, Base64Utils.encodeToString(
              String.format("%s:%s", authorizationUserName, DigestUtils.md5DigestAsHex(authorizationPassword.getBytes())
              ).getBytes("UTF-8")));
      requestBuilder.json(jo.toJSONString().getBytes("UTF-8"));
      requestBuilder.url(outboundPayload.getUrl()).post();
    } catch (Throwable e) {
      throw new MessageException(e.getMessage(), e);
    }
  }
}
