package com.dlyd.application.lib.esb.msg11;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.noc.hccp.platform.common.PlatformException;

public class Struct extends Element {

  private InternalStruct back = null;
  private Map<String, Element> datas = new HashMap<>();

  Struct(InternalStruct back) {
    this.back = back;
  }

  Struct() {
    // for Struct in Array
    setName("");
  }

  public void addData(Element data) {
    datas.put(data.getName(), data);
  }

  Iterator<Element> iteratorData() {
    return datas.values().iterator();
  }

  public Field getField(String name) {
    if (back == null) {
      return null;
    }
    InternalData id = back.getData(name);
    if (id != null) {
      if (id.getDataType() == DataType.Field) {
        return Field.create(id);
      }
      throw new PlatformException("Wrong Data Type");
    }
    return null;
  }

  public Array getArray(String name) {
    if (back == null) {
      return null;
    }
    InternalData id = back.getData(name);
    if (id != null) {
      if (id.getDataType() == DataType.Array) {
        return Array.create(id);
      }
      throw new PlatformException("Wrong Data Type");
    }
    return null;
  }

  static Struct create(InternalStruct internalStruct) {
    return new Struct(internalStruct);
  }
}
