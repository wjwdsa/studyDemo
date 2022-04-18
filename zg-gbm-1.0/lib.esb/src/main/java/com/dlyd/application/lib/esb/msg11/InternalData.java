package com.dlyd.application.lib.esb.msg11;

import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.noc.hccp.platform.common.PlatformException;

class InternalData extends InternalElement {

  private DataType dataType = DataType.Unspecified;
  private String _name;
  private InternalArray array = null;
  private InternalStruct struct = null;
  private String type;
  private int length = 0;
  private int scale = 0;
  private String value;

  InternalData(String name) {
    this._name = name;
  }

  String getName() {
    return _name;
  }

  void setName(String _name) {
    this._name = _name;
  }

  String getType() {
    return type;
  }

  void setType(String type) {
    this.type = type;
  }

  int getLength() {
    return length;
  }

  void setLength(int length) {
    this.length = length;
  }

  int getScale() {
    return scale;
  }

  void setScale(int scale) {
    this.scale = scale;
  }

  String getValue() {
    return value;
  }

  void setValue(String value) {
    this.value = value;
  }

  InternalArray getArray() {
    return array;
  }

  InternalStruct getStruct() {
    return struct;
  }

  DataType getDataType() {
    return dataType;
  }

  void parse(XMLEventReader xmlEventReader) throws XMLStreamException {
    while (xmlEventReader.hasNext()) {
      XMLEvent xmlEvent = xmlEventReader.nextEvent();

      if (xmlEvent.isStartElement()) {
        StartElement startElement = xmlEvent.asStartElement();
        if (startElement.getName().getLocalPart().equalsIgnoreCase("struct")) {
          this.dataType = DataType.Struct;
          this.struct = new InternalStruct();
          this.struct.parse(xmlEventReader, "struct");
        }
        if (startElement.getName().getLocalPart().equalsIgnoreCase("array")) {
          this.dataType = DataType.Array;
          this.array = new InternalArray();
          this.array.parse(xmlEventReader, "array");
        }
        if (startElement.getName().getLocalPart().equalsIgnoreCase("field")) {
          this.dataType = DataType.Field;

          Attribute typeAttr = startElement.getAttributeByName(new QName("type"));
          Attribute lengthAttr = startElement.getAttributeByName(new QName("length"));
          Attribute scaleAttr = startElement.getAttributeByName(new QName("scale"));

          if (typeAttr != null) {
            this.type = typeAttr.getValue();
          }
          if (lengthAttr != null) {
            this.length = Integer.parseInt(lengthAttr.getValue());
          }
          if (scaleAttr != null) {
            this.scale = Integer.parseInt(scaleAttr.getValue());
          }


          XMLEvent nextEvent = xmlEventReader.nextEvent();
          if (nextEvent.isCharacters()) {
            this.value = nextEvent.asCharacters().getData();
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

  @Override
  public String toString() {
    switch (dataType) {
      case Struct:
        return "Struct";
      case Array:
        return "Array";
      case Field:
        return String.format("Field {type:%s, length:%d, value:%s}", type, length, value);
      default:
        return "Bad Data";
    }
  }

  void write(XMLStreamWriter xsw) throws XMLStreamException {
    xsw.writeStartElement("data");
    xsw.writeAttribute("name", _name);
    switch (dataType) {
      case Struct:
        this.struct.write(xsw, null);
        break;
      case Array:
        this.array.write(xsw);
        break;
      case Field:
        xsw.writeStartElement("field");
        xsw.writeAttribute("type", type);
        xsw.writeAttribute("length", String.valueOf(length));
        if (value != null) {
          xsw.writeCharacters(value);
        }
        xsw.writeEndElement();
        break;
      default:
        throw new PlatformException("Bad Data");
    }
    xsw.writeEndElement();
  }

  static InternalData createStringField(String code, int dataLength, String value) {
    InternalData id = new InternalData(code);
    id.dataType = DataType.Field;
    id.length = dataLength;
    id.value = value;
    id.type = InternalConst.ESB_FIELD_TYPE_STRINIG;
    return id;
  }

  static InternalData create(Field field) {
    InternalData id = new InternalData(field.getName());
    id.dataType = DataType.Field;
    id.length = field.getLength();
    id.scale = field.getScale();
    id.value = field.getValue();
    id.type = field.getType();
    return id;
  }

  static InternalData create(Array array) {
    InternalData id = new InternalData(array.getName());
    id.array = new InternalArray();
    id.dataType = DataType.Array;
    Iterator<Struct> is = array.getStructs().iterator();
    while(is.hasNext()) {
      Struct s = is.next();
      id.array.addStruct(InternalStruct.create(s));
    }
    return id;
  }

  static InternalData createArrayData(String name) {
    InternalData id = new InternalData(name);
    id.dataType = DataType.Array;
    id.array = new InternalArray();
    return id;
  }

  static InternalData createStructData(String name) {
    InternalData id = new InternalData(name);
    id.dataType = DataType.Struct;
    id.struct = new InternalStruct();
    return id;
  }
}
