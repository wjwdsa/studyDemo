package com.dlyd.application.lib.esb.msg11;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.apache.commons.lang3.StringUtils;
import org.noc.hccp.platform.common.PlatformException;

class InternalStruct extends InternalCollection {

  private Map<String, InternalData> datas = new LinkedHashMap<>();

  InternalStruct() {
  }

  void addData(InternalData data) {
    datas.put(data.getName(), data);
  }

  InternalData getData(String name) {
    if (datas.containsKey(name)) {
      return datas.get(name);
    }
    return null;
  }

  Iterator<InternalData> iterateData() {
    return datas.values().iterator();
  }

  void parse(XMLEventReader xmlEventReader, String endElementName) throws XMLStreamException {
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
            InternalData data = new InternalData(nameAttr.getValue());
            data.parse(xmlEventReader);
            addData(data);
          }
        }
      }
    }
  }

  void write(XMLStreamWriter xsw, String s) throws XMLStreamException {
    if (s != null) {
      if (StringUtils.isEmpty(s)) {
        throw new PlatformException("Name of Element is EMPTY");
      }
      xsw.writeStartElement(s);
    } else {
      xsw.writeStartElement("struct");
    }

    Iterator<InternalData> di = datas.values().iterator();
    while (di.hasNext()) {
      di.next().write(xsw);
    }

    xsw.writeEndElement();

  }

  static InternalStruct create(Struct s) {
    InternalStruct is = new InternalStruct();
    Iterator<Element> ie = s.iteratorData();
    while (ie.hasNext()) {
      Element e = ie.next();
      if (e instanceof Field) {
        is.addData(InternalData.create((Field) e));
      }
      if (e instanceof Array) {
        is.addData(InternalData.create((Array) e));
      }
    }
    return is;
  }
}
