package com.dlyd.application.lib.cryptography;

public class PlainTextPassword implements Password {

  private static final char[] _EMPTY = "".toCharArray();
  private String password;

  public PlainTextPassword(String password) {
    this.password = password;
  }

  @Override
  public char[] getPassword() {
    return password != null ? password.toCharArray() : _EMPTY;
  }
}
