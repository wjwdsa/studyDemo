package com.dlyd.application.gbm.test;

import org.noc.hccp.platform.common.PLOG;

import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;



/**
 *
 */

/**
 * @Desc
 * @author 马权
 *
 * @Date 2020年10月23日 下午5:18:26
 */
public class RSA {
  /**
   * RSA公钥加密
   *
   * @param str
   *            加密字符串
   * @param publicKey
   *            公钥
   * @return 密文
   * @throws Exception
   *             加密过程中的异常信息
   */
  static Charset charset = Charset.forName("UTF-8");
  public static String encrypt(String str,String publicKey) throws Exception {
    PLOG.normal.debug("--------------------------");
    //base64编码的公钥
    byte[] decoded = Base64.getDecoder().decode(publicKey.getBytes(charset));
    RSAPublicKey pubKey= (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
    //RAS加密
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.ENCRYPT_MODE,pubKey);
    String outStr=Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes(charset)));
    return outStr;
  }

  /**
   * RSA私钥解密
   *
   * @param str
   *            加密字符串
   * @param privateKey
   *            私钥
   * @return 铭文
   * @throws Exception
   * @throws Exception
   *             解密过程中的异常信息
   */
  public static String decrypt(String str,String privateKey) throws Exception  {
    //Base64解码加密后的字符串
    byte[] inputByte = Base64.getDecoder().decode(str.getBytes(charset));
    //Base64编码的私钥
    byte[] decoded = Base64.getDecoder().decode(privateKey.getBytes(charset));
    PrivateKey priKeyRSA = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
    //RSA解密
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.DECRYPT_MODE,priKeyRSA);
    String outStr=new String(cipher.doFinal(inputByte));
    return outStr;
  }

}
