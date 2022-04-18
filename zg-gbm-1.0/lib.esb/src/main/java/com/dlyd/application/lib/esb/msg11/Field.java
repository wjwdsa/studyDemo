package com.dlyd.application.lib.esb.msg11;

public class Field extends Element {
  private String type;
  private int length;
  private String value;
  private int scale = 0;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public int getScale() {
    return scale;
  }

  public void setScale(int scale) {
    this.scale = scale;
  }

  public static Field createStringField(String name, int length, String value) {
    Field f = new Field();
    f.setName(name);
    f.type = InternalConst.ESB_FIELD_TYPE_STRINIG;
    f.length = length;
    f.value = value;
    return f;
  }

  public static Field createDecimalField(String name, int length, int scale, String value) {
    Field f = new Field();
    f.setName(name);
    f.type = InternalConst.ESB_FIELD_TYPE_DECIMAL;
    f.length = length;
    f.scale = scale;
    f.value = value;
    return f;
  }

  public static Field createDoubleField(String name, int length, int scale, String value) {
    Field f = new Field();
    f.setName(name);
    f.type = InternalConst.ESB_FIELD_TYPE_DOUBLE;
    f.length = length;
    f.scale = scale;
    f.value = value;
    return f;
  }

  static Field create(InternalData id) {
    Field f = new Field();
    f.setName(id.getName());
    f.type = id.getType();
    f.length = id.getLength();
    f.value = id.getValue();
    return f;
  }

  @Override
  public String toString() {
    return "Field{" +
        "type='" + type + '\'' +
        ", length=" + length +
        ", value='" + value + '\'' +
        '}';
  }
}
