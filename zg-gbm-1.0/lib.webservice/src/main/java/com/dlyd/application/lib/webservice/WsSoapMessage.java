package com.dlyd.application.lib.webservice;

import javax.xml.soap.SOAPMessage;
import org.noc.hccp.platform.common.msg.Message;

public class WsSoapMessage extends Message<WsSoapMessage> {
  private SOAPMessage soapMessage;
  private String soapAction;
  private String soapEndpoint;

  public SOAPMessage getSoapMessage() {
    return soapMessage;
  }

  public void setSoapMessage(SOAPMessage soapMessage) {
    this.soapMessage = soapMessage;
  }

  public String getSoapAction() {
    return soapAction;
  }

  public void setSoapAction(String soapAction) {
    this.soapAction = soapAction;
  }

  public String getSoapEndpoint() {
    return soapEndpoint;
  }

  public void setSoapEndpoint(String soapEndpoint) {
    this.soapEndpoint = soapEndpoint;
  }

  @Override
  public WsSoapMessage clone() {
    return null;
  }

  @Override
  public WsSoapMessage empty() {
    return null;
  }
}
