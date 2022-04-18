package com.dlyd.application.lib.esb.msg11;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.XMLEvent;
import org.noc.hccp.platform.common.PlatformException;

public class XMLEventUtils {

  public static void waitingForEndElement(XMLEventReader xmlEventReader, XMLEvent lastEvent, String name) throws XMLStreamException {
    if (lastEvent != null && matchEndElement(lastEvent, name)) {
      return;
    }
    XMLEvent event;
    while (xmlEventReader.hasNext()) {
      event = xmlEventReader.nextEvent(); // skip end of <field>
      if (event.isEndElement()) {
        EndElement endElement = event.asEndElement();
        if (endElement.getName().getLocalPart().equalsIgnoreCase(name)) {
          return;
        }
      }
    }
    throw new PlatformException(String.format("Expecting End Element [%s] is not found", name));
  }

  private static boolean matchEndElement(XMLEvent event, String name) {
    if (event.isEndElement()) {
      EndElement endElement = event.asEndElement();
      if (endElement.getName().getLocalPart().equalsIgnoreCase(name)) {
        return true;
      }
    }
    return false;
  }
}
