package com.dlyd.application.lib.cryptography;

import java.lang.SecurityException;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;

class RSAEncryptionBCImpl implements RSAEncryption {

  private static final String ENCRYPT_TYPE = "RSA";
  private PrivateKey privateKey;
  private PublicKey publicKey;

  void setPrivateKey(PrivateKey privateKey) {
    this.privateKey = privateKey;
  }

  void setPublicKey(PublicKey publicKey) {
    this.publicKey = publicKey;
    String s = "D:\\YB\\Documents\\WeChat\\WeChat Files\\wxid_tek5v483cb3o22\\FileStorage\\File\\2019-08";
  }

  @Override
  public byte[] publicKeyEncrypt(byte[] source) {
    return new byte[0];
  }

  @Override
  public byte[] publicKeyDecrypt(byte[] source) {
    RSAPublicKey PubKey = (RSAPublicKey) publicKey;
    BigInteger mod = PubKey.getModulus();
    BigInteger pubExp = PubKey.getPublicExponent();
    RSAKeyParameters pubParameters = new RSAKeyParameters(false, mod,
            pubExp);
    AsymmetricBlockCipher eng = new RSAEngine();
//    if (type == PKCS1)
//      eng = new PKCS1Encoding(eng);
    eng.init(false, pubParameters);
    byte[] data = null;
    try {
      data = eng.processBlock(source, 0, source.length);
      return data;
    } catch (Exception e) {
      throw new SecurityException("RSA PublicKey Decrypt Error:" + e.getMessage(), e);
    }
  }

  @Override
  public byte[] privateKeyEncrypt(byte[] source) {

    RSAPrivateCrtKey prvKey = (RSAPrivateCrtKey) privateKey;
    BigInteger mod = prvKey.getModulus();
    BigInteger pubExp = prvKey.getPublicExponent();
    BigInteger privExp = prvKey.getPrivateExponent();
    BigInteger pExp = prvKey.getPrimeExponentP();
    BigInteger qExp = prvKey.getPrimeExponentQ();
    BigInteger p = prvKey.getPrimeP();
    BigInteger q = prvKey.getPrimeQ();
    BigInteger crtCoef = prvKey.getCrtCoefficient();
    RSAKeyParameters privParameters = new RSAPrivateCrtKeyParameters(mod,
            pubExp, privExp, p, q, pExp, qExp, crtCoef);
    AsymmetricBlockCipher eng = new RSAEngine();
//    if (type == PKCS1)
//      eng = new PKCS1Encoding(eng);
    eng.init(true, privParameters);
    byte[] data = null;
    try {
      data = eng.processBlock(source, 0, source.length);
      return data;
    } catch (Exception e) {
      throw new SecurityException("RSA PrivateKey Encrypt Error:" + e.getMessage(), e);
    }
  }

  @Override
  public byte[] privateKeyDecrypt(byte[] source) {
    return new byte[0];
  }
}
