package com.dlyd.application.lib.cryptography;

public interface RSAEncryption {
  byte[] publicKeyEncrypt(byte[] source);
  byte[] publicKeyDecrypt(byte[] source);
  byte[] privateKeyEncrypt(byte[] source);
  byte[] privateKeyDecrypt(byte[] source);
}
