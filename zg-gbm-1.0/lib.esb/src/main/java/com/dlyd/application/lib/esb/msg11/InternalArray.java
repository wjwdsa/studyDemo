package com.dlyd.application.lib.esb.msg11;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

class InternalArray extends InternalCollection {

  private List<InternalStruct> structs = new ArrayList<>();

  InternalArray() {
  }

  void addStruct(InternalStruct s) {
    structs.add(s);
  }

  Iterator<InternalStruct> iterateStruct() {
    return structs.iterator();
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
        if (startElement.getName().getLocalPart().equalsIgnoreCase("struct")) {
          InternalStruct struct = new InternalStruct();
          struct.parse(xmlEventReader, "struct");
          addStruct(struct);
        }
      }
    }
  }

  void write(XMLStreamWriter xsw) throws XMLStreamException {
    xsw.writeStartElement("array");
    for (InternalStruct s : structs) {
      s.write(xsw, null);
    }
    xsw.writeEndElement();
  }
}
