package com.dlyd.application.lib.cryptography;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface AESEncryption {
  /**
   * 加密
   * @param source 明文
   * @return
   */
  byte[] Encrypt(byte[] source) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException;

  /**
   * 解密
   * @param source 密文
   * @return
   */
  byte[] Decrypt(byte[] source) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException;
}
