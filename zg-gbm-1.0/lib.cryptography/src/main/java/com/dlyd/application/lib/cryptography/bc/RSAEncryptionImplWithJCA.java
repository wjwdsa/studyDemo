package com.dlyd.application.lib.cryptography.bc;

import com.dlyd.application.lib.cryptography.RSAEncryption;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import org.bouncycastle.jcajce.util.BCJcaJceHelper;
import org.bouncycastle.jcajce.util.JcaJceHelper;

class RSAEncryptionImplWithJCA implements RSAEncryption {
  private JcaJceHelper jcaJceHelper = new BCJcaJceHelper();
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
      Cipher cipher = jcaJceHelper.createCipher(ENCRYPT_TYPE);
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);
      return cipher.doFinal(source);
    } catch (Throwable e) {
      throw new SecurityException("RSA PublicKey Encrypt Error:" + e.getMessage(), e);
    }
  }

  @Override
  public byte[] publicKeyDecrypt(byte[] source) {
    try {
      Cipher cipher = jcaJceHelper.createCipher(ENCRYPT_TYPE);
      cipher.init(Cipher.DECRYPT_MODE, publicKey);
      return cipher.doFinal(source);
    } catch (Throwable e) {
      throw new SecurityException("RSA PublicKey Decrypt Error:" + e.getMessage(), e);
    }
  }

  @Override
  public byte[] privateKeyEncrypt(byte[] source) {
    try {
      Cipher cipher = jcaJceHelper.createCipher(ENCRYPT_TYPE);
      cipher.init(Cipher.ENCRYPT_MODE, privateKey);
      return cipher.doFinal(source);
    } catch (Throwable e) {
      throw new SecurityException("RSA PrivateKey Encrypt Error:" + e.getMessage(), e);
    }
  }

  @Override
  public byte[] privateKeyDecrypt(byte[] source) {
    try {
      Cipher cipher = jcaJceHelper.createCipher(ENCRYPT_TYPE);
      cipher.init(Cipher.DECRYPT_MODE, privateKey);
      return cipher.doFinal(source);
    } catch (Throwable e) {
      throw new SecurityException("RSA PrivateKey Decrypt Error:" + e.getMessage(), e);
    }
  }
}
