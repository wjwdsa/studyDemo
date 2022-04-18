package com.dlyd.application.lib.esb.msg11;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.noc.hccp.platform.common.AppLog;
import org.noc.hccp.platform.common.PlatformException;
import org.noc.hccp.platform.common.msg.ByteMessageFactory;

class MessageFactoryImpl implements ByteMessageFactory<ESBMessage> {

  private final XMLInputFactory xmlInputFactory;
  private final XMLOutputFactory xmlOutputFactory;

  MessageFactoryImpl() {
    xmlInputFactory = XMLInputFactory.newFactory();
    xmlOutputFactory = XMLOutputFactory.newFactory();
  }

  @Override
  public ESBMessage toMessage(byte[] bytes) {
    try {
      AppLog.logger.debug(String.format("toMessage [%s]", new String(bytes, "UTF-8")));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    ESBMessage esbMessage = null;
    XMLEvent xmlEvent = null;
    try {
      XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new ByteArrayInputStream(bytes));
      while (xmlEventReader.hasNext()) {
        xmlEvent = xmlEventReader.nextEvent();
        if (xmlEvent.isStartElement()) {  // <service>
          StartElement seService = xmlEvent.asStartElement();
          if (seService.getName().getLocalPart().equalsIgnoreCase("service")) {
            esbMessage = new ESBMessage();
            while (xmlEventReader.hasNext()) {
              xmlEvent = xmlEventReader.nextEvent();
              if (xmlEvent.isStartElement()) {  // <sys-header>
                StartElement seStruct = xmlEvent.asStartElement();
                if (seStruct.getName().getLocalPart().equalsIgnoreCase("sys-header")) {
                  esbMessage.getSysHeader().parse(xmlEventReader, "sys-header");
                }
                if (seStruct.getName().getLocalPart().equalsIgnoreCase("app-header")) {
                  esbMessage.getAppHeader().parse(xmlEventReader, "app-header");
                }
                if (seStruct.getName().getLocalPart().equalsIgnoreCase("local-header")) {
                  esbMessage.getLocalHeader().parse(xmlEventReader, "local-header");
                }
                if (seStruct.getName().getLocalPart().equalsIgnoreCase("body")) {
                  esbMessage.getBody().parse(xmlEventReader, "body");
                }
              }
            }
          }
        }
      }
    } catch (XMLStreamException e) {
      throw new PlatformException(e.getMessage(), e);
    }
    if (esbMessage == null) {
      throw new PlatformException("无效的消息报文：报文中未包含<service>元素。");
    }
    return esbMessage;
  }

  @Override
  public byte[] toBytes(ESBMessage am) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      XMLStreamWriter xsw = xmlOutputFactory.createXMLStreamWriter(baos, "UTF-8");

      xsw.writeStartDocument("UTF-8", "1.0");
      xsw.writeStartElement("service");

      if (am.getSysHeader() != null) {
        am.getSysHeader().write(xsw, "sys-header");
      }

      if (am.getAppHeader() != null) {
        am.getAppHeader().write(xsw, "app-header");
      }

      if (am.getLocalHeader() != null) {
        am.getLocalHeader().write(xsw, "local-header");
      }

      if (am.getBody() != null) {
        am.getBody().write(xsw, "body");
      }

      xsw.writeEndElement();
      xsw.writeEndDocument();

      byte[] bytes = baos.toByteArray();
      try {
        AppLog.logger.debug(String.format("toBytes [%s]", new String(bytes, "UTF-8")));
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      return bytes;
    } catch (XMLStreamException e) {
      throw new PlatformException(e.getMessage(), e);
    }
    /*
    try {
      Package.Header nclHeader = new Package.Header();
      char bizState = Package.Header.BIZ_STATE_NORMAL;

      ByteArrayOutputStream out = new ByteArrayOutputStream();
      nclHeader.setTransactionCount(1);
      nclHeader.setBusinessCode(am.getString("0030"));
      nclHeader.setCommMode("T");
      nclHeader.setBizState(bizState);

      nclHeader.setTtyIP(am.getHeaderAsString(Const.PACKAGE_HEADER_ADDRESS, ""));
      Package pkg = newPackage();
      Iterator<Entry<String, Object>> ed = am.getDatas().entrySet().iterator();
      while (ed.hasNext()) {
        Entry<String, Object> d = ed.next();
        pkg.setValue(d.getKey(), d.getValue().toString());
      }
      byte[] pbytes = pkg.toByte();
      PackageUtils.cryptByteArray(pbytes, Const.nclchina, Const.ENCRYPT, 0);

      String plen = String.format("%1$04d", pbytes.length);
      String tlen = String.format("%1$08d", pbytes.length + 60);
      AppLog.logger.debug("Message to bytes plen:" + plen + ", tlen:" + tlen);
      out.write(tlen.getBytes());
      nclHeader.setLen(plen);
      out.write(nclHeader.toByte());
      out.write(pbytes);
      if (am.hasFiles()) {
        AppLog.logger.debug("Files in Message to bytes");
        PackageUtils.writeFile(out, am.getFiles());
      }
      out.write(Const.B0XFF);
      return out.toByteArray();
    } catch (IOException e) {
      throw new PlatformException(e.getMessage(), e);
    }
    */
  }
}
