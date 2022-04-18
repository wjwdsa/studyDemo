package com.dlyd.application.lib.esb.msg11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Array extends Element {

  private List<Struct> structs = new ArrayList<>();

  private void addStruct(Struct s) {
    structs.add(s);
  }

  public List<Struct> getStructs() {
    return Collections.unmodifiableList(structs);
  }

  public static Array create(String name) {
    Array a = new Array();
    a.setName(name);
    return a;
  }

  public Struct newStruct() {
    Struct s = new Struct();
    addStruct(s);
    return s;
  }

  static Array create(InternalData id) {
    Array a = new Array();
    InternalArray ia = id.getArray();
    Iterator<InternalStruct> iis = ia.iterateStruct();
    while (iis.hasNext()) {
      a.structs.add(Struct.create(iis.next()));
    }
    return a;
  }
}
