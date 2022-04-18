package com.dlyd.application.lib.esb.pm;

import com.dlyd.application.lib.esb.msg11.XMLEventUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.apache.commons.lang3.StringUtils;
import org.noc.hccp.platform.common.AppLog;
import org.noc.hccp.platform.common.PlatformException;
import org.noc.hccp.platform.common.msg.PlatformMessage;
import org.noc.hccp.platform.common.msg.PlatformMessage.E;
import org.noc.hccp.platform.common.msg.PlatformMessage.L;
import org.noc.hccp.platform.common.msg.PlatformMessage.M;
import org.noc.hccp.platform.common.msg.PlatformMessage.V;
import org.noc.hccp.platform.common.msg.PlatformMessage.Visitor;
import org.noc.hccp.platform.common.msg.PlatformMessageConverter;
import org.noc.hccp.platform.common.msg.PlatformMessageUtils;

public class PlatformMessageConverterImpl implements PlatformMessageConverter {

  private final XMLInputFactory xmlInputFactory;
  private final XMLOutputFactory xmlOutputFactory;

  public PlatformMessageConverterImpl() {
    xmlInputFactory = XMLInputFactory.newFactory();
    xmlOutputFactory = XMLOutputFactory.newFactory();
  }

  @Override
  public byte[] fromPlatformMessage(PlatformMessage platformMessage) {
    try {
      Visitor visitor = platformMessage.visitor();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      XMLStreamWriter xsw = xmlOutputFactory.createXMLStreamWriter(baos, "UTF-8");

      xsw.writeStartDocument("UTF-8", "1.0");
      xsw.writeStartElement("service");

      Optional<E> oe;
      oe = visitor.getResponse().get(PMC.SYS_HEADER);
      if (oe.isPresent()) {
        writeMap(xsw, "sys-header", (M) oe.get());
      }

      oe = visitor.getResponse().get(PMC.APP_HEADER);
      if (oe.isPresent()) {
        writeMap(xsw, "app-header", (M) oe.get());
      }

      oe = visitor.getResponse().get(PMC.LOCAL_HEADER);
      if (oe.isPresent()) {
        writeMap(xsw, "local-header", (M) oe.get());
      }

      oe = visitor.getResponse().get(PMC.BODY);
      if (oe.isPresent()) {
        writeMap(xsw, "body", (M) oe.get());
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
  }

  private String getOptionalMetaValue(V v, String key) {
    String r = v.getMeta(key);
    return r != null ? r : "";
  }

  private void writeMap(XMLStreamWriter xsw, String tag, M map) throws XMLStreamException {
    if (tag != null) {
      if (StringUtils.isEmpty(tag)) {
        throw new PlatformException("Name of Element is EMPTY");
      }
      xsw.writeStartElement(tag);
    } else {
      xsw.writeStartElement("struct");
    }

    Iterator<Entry<String, E>> ei = map.iterator();
    while (ei.hasNext()) {
      Entry<String, E> ee = ei.next();
      String name = ee.getKey();
      E elm = ee.getValue();
      xsw.writeStartElement("data");
      xsw.writeAttribute("name", name);
      switch (elm.getType()) {
        case Map:
          writeMap(xsw, null, (M) elm);
          break;
        case List:
          writeArray(xsw, (L) elm);
          break;
        case Value:
          V v = (V)elm;
          xsw.writeStartElement("field");
          xsw.writeAttribute("type", getOptionalMetaValue(v, "type"));
          xsw.writeAttribute("length", getOptionalMetaValue(v, "length"));
          if ("double".equalsIgnoreCase(getOptionalMetaValue(v,"type"))) {
            xsw.writeAttribute("scale", getOptionalMetaValue(v, "scale"));
          }
          if (v.get() != null) {
            xsw.writeCharacters(v.get());
          }
          xsw.writeEndElement();
          break;
        default:
          throw new PlatformException("Bad Data");
      }
      xsw.writeEndElement();
    }
    xsw.writeEndElement();
  }

  private void writeArray(XMLStreamWriter xsw, L list) throws XMLStreamException {
    xsw.writeStartElement("array");
    List<M> maps = list.maps();
    if (maps != null) {
      for(M map : maps) {
        writeMap(xsw, null, map);
      }
    }
    xsw.writeEndElement();
  }

  @Override
  public PlatformMessage toPlatformMessage(byte[] bytes) {
    try {
      AppLog.logger.debug(String.format("toMessage [%s]", new String(bytes, "UTF-8")));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    PlatformMessage.Builder esbMessage = null;
    XMLEvent xmlEvent;
    try {
      XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new ByteArrayInputStream(bytes));
      while (xmlEventReader.hasNext()) {
        xmlEvent = xmlEventReader.nextEvent();
        if (xmlEvent.isStartElement()) {  // <service>
          StartElement seService = xmlEvent.asStartElement();
          if (seService.getName().getLocalPart().equalsIgnoreCase("service")) {
            esbMessage = PlatformMessageUtils.newBuilder();
            while (xmlEventReader.hasNext()) {
              xmlEvent = xmlEventReader.nextEvent();
              if (xmlEvent.isStartElement()) {  // <sys-header>
                StartElement seStruct = xmlEvent.asStartElement();
                if (seStruct.getName().getLocalPart().equalsIgnoreCase("sys-header")) {
                  M sysHeader = esbMessage.getRequest().newMap(PMC.SYS_HEADER);
                  parseStruct(xmlEventReader, "sys-header", sysHeader);
                }
                if (seStruct.getName().getLocalPart().equalsIgnoreCase("app-header")) {
                  M appHeader = esbMessage.getRequest().newMap(PMC.APP_HEADER);
                  parseStruct(xmlEventReader, "app-header", appHeader);
                }
                if (seStruct.getName().getLocalPart().equalsIgnoreCase("local-header")) {
                  M localHeader = esbMessage.getRequest().newMap(PMC.LOCAL_HEADER);
                  parseStruct(xmlEventReader, "local-header", localHeader);
                }
                if (seStruct.getName().getLocalPart().equalsIgnoreCase("body")) {
                  M body = esbMessage.getRequest().newMap(PMC.BODY);
                  parseStruct(xmlEventReader, "body", body);
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
    return esbMessage.build();
  }

  private void parseStruct(XMLEventReader xmlEventReader, String endElementName, M resultMap) throws XMLStreamException {
    while (xmlEventReader.hasNext()) {
      XMLEvent xmlEvent = xmlEventReader.nextEvent();
      if (xmlEvent.isEndElement()) {
        EndElement endElement = xmlEvent.asEndElement();
        if (endElement.getName().getLocalPart().equalsIgnoreCase(endElementName)) {
          break;
        }
      }
      if (xmlEvent.isStartElement()) {
        StartElement startElement = xmlEvent.asStartElement();
        if (startElement.getName().getLocalPart().equalsIgnoreCase("data")) {
          Attribute nameAttr = startElement.getAttributeByName(new QName("name"));
          if (nameAttr != null) {
            String dataName = nameAttr.getValue();
            parseData(xmlEventReader, dataName, resultMap);
          }
        }
      }
    }
  }

  private void parseArray(XMLEventReader xmlEventReader, String endElementName, L resultArray) throws XMLStreamException {
    while (xmlEventReader.hasNext()) {
      XMLEvent xmlEvent = xmlEventReader.nextEvent();
      if (xmlEvent.isEndElement()) {
        EndElement endElement = xmlEvent.asEndElement();
        if (endElement.getName().getLocalPart().equalsIgnoreCase(endElementName)) {
          break;
        }
      }
      if (xmlEvent.isStartElement()) {
        StartElement startElement = xmlEvent.asStartElement();
        if (startElement.getName().getLocalPart().equalsIgnoreCase("struct")) {
          M childMap = resultArray.newMap();
          parseStruct(xmlEventReader, "struct", childMap);
        }
      }
    }
  }

  private void parseData(XMLEventReader xmlEventReader, String dataName, M resultMap) throws XMLStreamException {
    while (xmlEventReader.hasNext()) {
      XMLEvent xmlEvent = xmlEventReader.nextEvent();

      if (xmlEvent.isStartElement()) {
        StartElement startElement = xmlEvent.asStartElement();
        if (startElement.getName().getLocalPart().equalsIgnoreCase("struct")) {
          M childMap = resultMap.newMap(dataName);
          parseStruct(xmlEventReader, "struct", childMap);
        }
        if (startElement.getName().getLocalPart().equalsIgnoreCase("array")) {
          L resultArray = resultMap.newList(dataName);
          parseArray(xmlEventReader, "array", resultArray);
        }
        if (startElement.getName().getLocalPart().equalsIgnoreCase("field")) {
          V value = resultMap.newValue(dataName);

          Attribute typeAttr = startElement.getAttributeByName(new QName("type"));
          Attribute lengthAttr = startElement.getAttributeByName(new QName("length"));
          Attribute scaleAttr = startElement.getAttributeByName(new QName("scale"));

          if (typeAttr != null) {
            value.setMeta("type", typeAttr.getValue());
          }
          if (lengthAttr != null) {
            value.setMeta("length", lengthAttr.getValue());
          }
          if (scaleAttr != null) {
            value.setMeta("scale", scaleAttr.getValue());
          }
          XMLEvent nextEvent = xmlEventReader.nextEvent();
          if (nextEvent.isCharacters()) {
            value.set(nextEvent.asCharacters().getData());
          }
          XMLEventUtils.waitingForEndElement(xmlEventReader, nextEvent, "field");
        }
      }
      if (xmlEvent.isEndElement()) {
        EndElement endElement = xmlEvent.asEndElement();
        if (endElement.getName().getLocalPart().equalsIgnoreCase("data")) {
          return;
        }
      }
    }
    throw new PlatformException("Malformed xml message, <data> and </data> is not matched");
  }
}
