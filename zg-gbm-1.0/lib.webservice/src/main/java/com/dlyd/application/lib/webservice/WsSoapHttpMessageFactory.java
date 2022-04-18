package com.dlyd.application.lib.webservice;

import io.netty.util.AsciiString;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map.Entry;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.noc.hccp.platform.common.PLOG;
import org.noc.hccp.platform.common.msg.HttpMessageFactory;
import org.noc.hccp.platform.common.msg.HttpRequestBuilder;
import org.noc.hccp.platform.common.msg.HttpRequestHolder;
import org.noc.hccp.platform.common.msg.HttpResponseBuilder;
import org.noc.hccp.platform.common.msg.HttpResponseHolder;

public class WsSoapHttpMessageFactory implements HttpMessageFactory<WsSoapMessage> {

  private MessageFactory soapMessageFactory = MessageFactory.newInstance();

  public WsSoapHttpMessageFactory() throws SOAPException {
  }

  @Override
  public WsSoapMessage fromRequest(HttpRequestHolder httpRequestHolder) {
    try {
      PLOG.normal.info("fromRequest: [{}]",new String(httpRequestHolder.getContentBytes(),"UTF-8"));
      SOAPMessage soapMessage = soapMessageFactory.createMessage(null, new ByteArrayInputStream(httpRequestHolder.getContentBytes()));
      WsSoapMessage wsSoapMessage = new WsSoapMessage();
      wsSoapMessage.setSoapMessage(soapMessage);
      wsSoapMessage.setSoapEndpoint(httpRequestHolder.getUri());
      for (Entry<String, String> header : httpRequestHolder.getHeaders()) {
        if ("SOAPAction".equalsIgnoreCase(header.getKey())) {
          wsSoapMessage.setSoapAction(header.getValue());
        }
      }
      return wsSoapMessage;
    } catch (Throwable e) {
      PLOG.normal.debug(e.getMessage(), e);
      throw new WsMessageException(e);
    }
  }

  @Override
  public void toResponse(WsSoapMessage wsSoapMessage, HttpResponseBuilder httpResponseBuilder) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public WsSoapMessage fromResponse(HttpResponseHolder httpResponseHolder) {

    try {
      PLOG.normal.info("fromResponse: [{}]",new String(httpResponseHolder.getContentBytes(),"UTF-8"));
      SOAPMessage soapMessage = soapMessageFactory.createMessage(null, new ByteArrayInputStream(httpResponseHolder.getContentBytes()));
      WsSoapMessage wsSoapMessage = new WsSoapMessage();
      wsSoapMessage.setSoapMessage(soapMessage);
      return wsSoapMessage;
    } catch (Throwable e) {
      PLOG.normal.debug(e.getMessage(), e);
      throw new WsMessageException(e);
    }
  }

  @Override
  public void toRequest(WsSoapMessage wsSoapMessage, HttpRequestBuilder httpRequestBuilder) {
    try {
      httpRequestBuilder.contentType("json;charset=UTF-8");
      httpRequestBuilder.setHeader(AsciiString.of("SOAPAction"), "urn:ktkjBankFlowSave");
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      wsSoapMessage.getSoapMessage().writeTo(baos);
      httpRequestBuilder.content(baos.toByteArray());
      PLOG.normal.info("toRequest: [{}]",baos.toString());
      httpRequestBuilder.post();
      httpRequestBuilder.url(wsSoapMessage.getSoapEndpoint());
    } catch (Throwable e) {
      PLOG.normal.debug(e.getMessage(), e);
      throw new WsMessageException(e);
    }
  }
}
