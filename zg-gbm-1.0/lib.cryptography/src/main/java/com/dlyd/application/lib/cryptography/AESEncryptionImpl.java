package com.dlyd.application.lib.cryptography;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AESEncryptionImpl implements AESEncryption {
  /**
   * 密钥算法
   */
  private final String ALGORITHM = "AES";
  /**
   * 加解密算法/工作模式/填充方式
   */
  private final String ALGORITHM_STR = "AES/ECB/PKCS5Padding";

  private String key;

  public void setKey(String key) {
    this.key = key;
  }


  @Override
  public byte[] Encrypt(byte[] source) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
    SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
    Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
    return cipher.doFinal(source);
  }

  @Override
  public byte[] Decrypt(byte[] source) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
    SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
    Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
    return cipher.doFinal(source);
  }
}
