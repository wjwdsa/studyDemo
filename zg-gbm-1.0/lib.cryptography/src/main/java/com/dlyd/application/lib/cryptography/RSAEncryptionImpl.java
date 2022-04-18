package com.dlyd.application.lib.cryptography;

import java.lang.SecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.Cipher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class RSAEncryptionImpl implements RSAEncryption {

  private Logger log = LogManager.getLogger("RSAEncryption");
  private static final String ENCRYPT_TYPE = "RSA";
  private PrivateKey privateKey;
  private PublicKey publicKey;

  void setPrivateKey(PrivateKey privateKey) {
    this.privateKey = privateKey;
  }

  void setPublicKey(PublicKey publicKey) {
    this.publicKey = publicKey;
  }

  @Override
  public byte[] publicKeyEncrypt(byte[] source) {
    try {
      Cipher cipher = Cipher.getInstance(ENCRYPT_TYPE);
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);
      return cipher.doFinal(source);
    } catch (Throwable e) {
      throw new SecurityException("RSA PublicKey Encrypt Error:" + e.getMessage(), e);
    }
  }

  @Override
  public byte[] publicKeyDecrypt(byte[] source) {
    try {
      Cipher cipher = Cipher.getInstance(ENCRYPT_TYPE);
      cipher.init(Cipher.DECRYPT_MODE, publicKey);
      return cipher.doFinal(source);
    } catch (Throwable e) {
      log.error(e.getMessage(), e);
      throw new SecurityException("RSA PublicKey Decrypt Error:" + e.getMessage(), e);
    }
  }

  @Override
  public byte[] privateKeyEncrypt(byte[] source) {
    try {
      RSAPrivateCrtKey rsaPrivateCrtKey = (RSAPrivateCrtKey) privateKey;
      RSAPublicKeySpec spec = new RSAPublicKeySpec(rsaPrivateCrtKey.getModulus(), rsaPrivateCrtKey.getPrivateExponent());
      Key fakePrivateKey = KeyFactory.getInstance("RSA").generatePublic(spec);

      Cipher cipher = Cipher.getInstance(ENCRYPT_TYPE);
      cipher.init(Cipher.ENCRYPT_MODE, fakePrivateKey);
      return cipher.doFinal(source);
    } catch (Throwable e) {
      throw new SecurityException("RSA PrivateKey Encrypt Error:" + e.getMessage(), e);
    }
  }

  @Override
  public byte[] privateKeyDecrypt(byte[] source) {
    try {
      Cipher cipher = Cipher.getInstance(ENCRYPT_TYPE);
      cipher.init(Cipher.DECRYPT_MODE, privateKey);
      return cipher.doFinal(source);
    } catch (Throwable e) {
      throw new SecurityException("RSA PrivateKey Decrypt Error:" + e.getMessage(), e);
    }
  }
}
