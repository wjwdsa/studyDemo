package com.dlyd.application.lib.cryptography;

public interface Encryption {
  byte[] encrypt(byte[] source);
  byte[] decrypt(byte[] source);
}
