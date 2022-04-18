package com.dlyd.application.lib.cryptography.bc;


import com.dlyd.application.lib.cryptography.RSAEncryption;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

class RSAEncryptionImpl implements RSAEncryption {

  private static final String ENCRYPT_TYPE = "RSA";
  private AsymmetricKeyParameter privateKey;
  private AsymmetricKeyParameter publicKey;

  void setPrivateKey(AsymmetricKeyParameter privateKey) {
    this.privateKey = privateKey;
  }

  public void setPublicKey(AsymmetricKeyParameter publicKey) {
    this.publicKey = publicKey;
  }

  @Override
  public byte[] publicKeyEncrypt(byte[] source) {
    try {
      AsymmetricBlockCipher e = new RSAEngine();
      //e = new org.bouncycastle.crypto.encodings.PKCS1Encoding(e);
      e.init(true, publicKey);

      return e.processBlock(source, 0, source.length);
    } catch (Throwable e) {
      throw new SecurityException("RSA PublicKey Encrypt Error:" + e.getMessage(), e);
    }
  }

  @Override
  public byte[] publicKeyDecrypt(byte[] source) {
    try {
      AsymmetricBlockCipher e = new RSAEngine();
      //e = new org.bouncycastle.crypto.encodings.PKCS1Encoding(e);
      e.init(false, publicKey);
      return e.processBlock(source, 0, source.length);
    } catch (Throwable e) {
      throw new SecurityException("RSA PublicKey Decrypt Error:" + e.getMessage(), e);
    }
  }

  @Override
  public byte[] privateKeyEncrypt(byte[] source) {
    try {
      AsymmetricBlockCipher e = new RSAEngine();
      //e = new org.bouncycastle.crypto.encodings.PKCS1Encoding(e);
      e.init(true, privateKey);

      return e.processBlock(source, 0, source.length);
    } catch (Throwable e) {
      throw new SecurityException("RSA PrivateKey Encrypt Error:" + e.getMessage(), e);
    }
  }

  @Override
  public byte[] privateKeyDecrypt(byte[] source) {
    try {
      AsymmetricBlockCipher e = new RSAEngine();
      //e = new org.bouncycastle.crypto.encodings.PKCS1Encoding(e);
      e.init(false, privateKey);

      return e.processBlock(source, 0, source.length);
    } catch (Throwable e) {
      throw new SecurityException("RSA PrivateKey Decrypt Error:" + e.getMessage(), e);
    }
  }
}
