package com.dlyd.application.lib.cryptography.bc;

import com.dlyd.application.lib.cryptography.RSAEncryption;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class RSAEncryptionFactory extends AbstractFactoryBean<RSAEncryption> implements InitializingBean {

  private PrivateKey privateKey;
  private PublicKey publicKey;

  public void setPrivateKey(PrivateKey privateKey) {
    if (privateKey == null) {
      logger.error("privateKey is null");
    }
    else {
      logger.debug("set privateKey as" + privateKey.toString());
    }
    this.privateKey = privateKey;
  }

  public void setPublicKey(PublicKey publicKey) {
    logger.debug("set publicKey as"+publicKey.toString());
    this.publicKey = publicKey;
  }

  @Override
  public Class<?> getObjectType() {
    return RSAEncryption.class;
  }

  @Override
  protected RSAEncryption createInstance() throws Exception {
    RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
    RSAKeyParameters pubParameters = new RSAKeyParameters(false, rsaPublicKey.getModulus(), rsaPublicKey.getPublicExponent());

    RSAPrivateCrtKey rsaPrivateCrtKey = (RSAPrivateCrtKey) privateKey;

    BigInteger mod = rsaPrivateCrtKey.getModulus();
    BigInteger pubExp = rsaPrivateCrtKey.getPublicExponent();
    BigInteger privExp = rsaPrivateCrtKey.getPrivateExponent();
    BigInteger p = rsaPrivateCrtKey.getPrimeP();
    BigInteger q = rsaPrivateCrtKey.getPrimeQ();
    BigInteger pExp = rsaPrivateCrtKey.getPrimeExponentP();
    BigInteger qExp = rsaPrivateCrtKey.getPrimeExponentQ();
    BigInteger crtCoef = rsaPrivateCrtKey.getCrtCoefficient();

    if (mod == null) {
      logger.warn("mod is null");
      mod = BigInteger.ZERO;
    }
    if (pubExp == null) {
      logger.warn("pubExp is null");
      pubExp = BigInteger.ZERO;
    }
    if (privExp == null) {
      logger.warn("privExp is null");
      privExp = BigInteger.ZERO;
    }
    if (p == null) {
      logger.warn("p is null");
      p = BigInteger.ZERO;
    }
    if (q == null) {
      logger.warn("q is null");
      q = BigInteger.ZERO;
    }
    if (pExp == null) {
      logger.warn("pExp is null");
      pExp = BigInteger.ZERO;
    }
    if (qExp == null) {
      logger.warn("qExp is null");
      qExp = BigInteger.ZERO;
    }
    if (crtCoef == null) {
      logger.warn("crtCoef is null");
      crtCoef = BigInteger.ZERO;
    }

    RSAKeyParameters privParameters = new RSAPrivateCrtKeyParameters(mod, pubExp, privExp, p, q, pExp, qExp, crtCoef);

//    RSAKeyParameters privParameters = new RSAPrivateCrtKeyParameters(mod, pubExp, privExp, p, q, pExp, qExp, crtCoef);

    com.dlyd.application.lib.cryptography.bc.RSAEncryptionImpl impl = new com.dlyd.application.lib.cryptography.bc.RSAEncryptionImpl();
    impl.setPrivateKey(privParameters);
    impl.setPublicKey(pubParameters);
    return impl;
  }
}
