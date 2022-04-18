package com.dlyd.application.gbm.test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;



/**
 *
 */

/**
 * @Desc
 * @author 马权
 *
 * @Date 2020年10月23日 下午5:18:15
 */
public class AES {

  public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
    String de="sdaklfdsalkfjkdsafjdlaskf";
    String decryptAES = encryptAES(de, "2021051129042379");
    System.out.println(decryptAES);
    String decryptAES2 = decryptAES(decryptAES,"2021051129042379");
    System.out.println(decryptAES2);
  }
  static final String ALGORITHM = "AES";
  static Charset charset = Charset.forName("UTF-8");
  public static  String encryptAES(String content, String pubKey) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException  { // 加密
    SecretKey secretKey = getSecretKey(pubKey);
    byte[] aes = aes(content.getBytes(charset),Cipher.ENCRYPT_MODE,secretKey);
    return parseByte2HexStr(aes);
  }

  public static  String decryptAES(String contentArray, String pubKey) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException { // 解密
    SecretKey secretKey = getSecretKey(pubKey);
    byte[] parseHexStr2Byte = parseHexStr2Byte(contentArray);
    byte[] result =  aes(parseHexStr2Byte,Cipher.DECRYPT_MODE,secretKey);
    return new String(result,charset);
  }

  private static  byte[] aes(byte[] contentArray, int mode, SecretKey secretKey)
          throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(mode, secretKey);
    byte[] result = cipher.doFinal(contentArray);
    return result;
  }

  public static  SecretKey getSecretKey(String key) throws UnsupportedEncodingException {
    try {
      //1.构造密钥生成器，指定为AES算法,不区分大小写
      KeyGenerator keygen=KeyGenerator.getInstance(ALGORITHM);
      //解决linux 系统下面出错问题
      SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
      secureRandom.setSeed(key.getBytes(charset));
      //2.根据ecnodeRules规则初始化密钥生成器
      //生成一个128位的随机源,根据传入的字节数组
      keygen.init(128,  secureRandom);

      //3.产生原始对称密钥
      SecretKey original_key=keygen.generateKey();
      //4.获得原始对称密钥的字节数组
      byte [] raw=original_key.getEncoded();
      //5.根据字节数组生成AES密钥
      SecretKey key1=new SecretKeySpec(raw, ALGORITHM);
      return key1;
    } catch (NoSuchAlgorithmException    e) {
      e.printStackTrace();
    }
    return null;
  }


  /**
   * 將二進制轉換成16進制
   *
   * @param buf
   * @return
   */
  public static String parseByte2HexStr(byte buf[]) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < buf.length; i++) {
      String hex = Integer.toHexString(buf[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      sb.append(hex.toUpperCase());
    }
    return sb.toString();
  }

  /**
   * 將16進制轉換為二進制
   *
   * @param hexStr
   * @return
   */
  public static byte[] parseHexStr2Byte(String hexStr) {
    if (hexStr.length() < 1)
      return null;
    byte[] result = new byte[hexStr.length() / 2];
    for (int i = 0; i < hexStr.length() / 2; i++) {
      int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
      int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
      result[i] = (byte) (high * 16 + low);
    }
    return result;
  }
}
