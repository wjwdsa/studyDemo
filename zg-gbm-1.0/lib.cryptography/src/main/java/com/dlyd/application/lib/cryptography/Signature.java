package com.dlyd.application.lib.cryptography;

public interface Signature {
  byte[] sign(byte[] source);
  boolean verify(byte[] source, byte[] sign);
}
