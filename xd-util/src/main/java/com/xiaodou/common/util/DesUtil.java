package com.xiaodou.common.util;

import java.security.Key;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @Title:DesUtil.java
 * 
 * @Description:DES加解密工具类
 * 
 * @author zhaoyang
 * @date Jan 17, 2014 10:25:07 AM
 * @version V1.0
 */
public class DesUtil {

  // 解密数据
  public static String decrypt(String message, String key) throws Exception {

    byte[] bytesrc = convertHexString(message);
    Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
    IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));

    cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

    byte[] retByte = cipher.doFinal(bytesrc);
    return new String(retByte);
  }

  public static byte[] encrypt(String message, String key) throws Exception {
    Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

    DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
    IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

    return cipher.doFinal(message.getBytes("UTF-8"));
  }

  public static byte[] convertHexString(String ss) {
    byte digest[] = new byte[ss.length() / 2];
    for (int i = 0; i < digest.length; i++) {
      String byteString = ss.substring(2 * i, 2 * i + 2);
      int byteValue = Integer.parseInt(byteString, 16);
      digest[i] = (byte) byteValue;
    }

    return digest;
  }

  public static String toHexString(byte b[]) {
    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < b.length; i++) {
      String plainText = Integer.toHexString(0xff & b[i]);
      if (plainText.length() < 2) plainText = "0" + plainText;
      hexString.append(plainText);
    }

    return hexString.toString();
  }

  /**
   * DES解密(此解密方法最初用于看购兑换券密码的解密)
   * 
   * @param strKey |DES加密KEY
   * @param strEncrypted |加密串
   * @param keyEncode |加密KEY字符编码
   * @param decrptEncode | 解密后原串的字符编码
   * @return 解密后的原串
   * @author bjfyzhao 2012-01-09 10:30
   */
  public static String decrypt(String strKey, String strEncrypted, String keyEncode,
      String decrptEncode) {
    String strDecrypted = null;

    try {
      Base64 objBase64 = new Base64();
      byte[] bysDecoded = objBase64.decode(strEncrypted.getBytes());

      DESKeySpec objDesKeySpec = new DESKeySpec(strKey.getBytes(keyEncode));
      SecretKeyFactory objKeyFactory = SecretKeyFactory.getInstance("DES");
      SecretKey objSecretKey = objKeyFactory.generateSecret(objDesKeySpec);

      Cipher objCipher = Cipher.getInstance("DES/ECB/NoPadding");
      objCipher.init(Cipher.DECRYPT_MODE, objSecretKey);
      strDecrypted = new String(objCipher.doFinal(bysDecoded), decrptEncode).trim();

    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return strDecrypted;
  }

  /**
   * ================Base64
   */
  // DES加密的私钥，必须是8位长的字符串
  static byte[] ivBytes = {0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD,
      (byte) 0xEF};

  public static String encryptBase64(String data, String keyStr) throws Exception {
    AlgorithmParameterSpec iv = new IvParameterSpec(ivBytes);
    DESKeySpec keySpec = new DESKeySpec(keyStr.getBytes());// 设置密钥参数
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂
    Key key = keyFactory.generateSecret(keySpec);// 得到密钥对象
    Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");// 得到加密对象Cipher
    enCipher.init(Cipher.ENCRYPT_MODE, key, iv);// 设置工作模式为加密模式，给出密钥和向量
    byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
    return Base64Utils.encode(pasByte);
  }

  public static String decryptBase64(String data, String keyStr) throws Exception {
    AlgorithmParameterSpec iv = new IvParameterSpec(ivBytes);
    DESKeySpec keySpec = new DESKeySpec(keyStr.getBytes());// 设置密钥参数
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂
    Key key = keyFactory.generateSecret(keySpec);// 得到密钥对象
    Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    deCipher.init(Cipher.DECRYPT_MODE, key, iv);
    byte[] pasByte = deCipher.doFinal(Base64Utils.decode(data));
    return new String(pasByte, "UTF-8");
  }

  /**
   * ============================= 以上代码无法支持中文互相转换关键问题在这: java byte : -128~127 C# byte : 0~255
   * 首先把byte[] 变成　sbyte[] 然后实现　sbyte[]的　base64转换 base64是相同的，可以去参考下baidu c#的介绍 附　byte- sbyte 对b 进行下处理
   * sbyte [] bSByte = new sbyte [b.Length]; for ( int i = 0 ; i < b.Length; i ++ ) { if (b[i] > 127
   * ) bSByte[i] = ( sbyte )(b[i] - 256 ); else bSByte[i] = ( sbyte )b[i]; }
   */

  /** 字符串默认键值 */
  private static String strDefaultKey = "national";

  /** 加密工具 */
  private Cipher encryptCipher = null;

  /** 解密工具 */
  private Cipher decryptCipher = null;

  /**
   * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[] hexStr2ByteArr(String
   * strIn) 互为可逆的转换过程
   * 
   * @param arrB 需要转换的byte数组
   * @return 转换后的字符串
   * @throws Exception 本方法不处理任何异常，所有异常全部抛出
   */
  public static String byteArr2HexStr(byte[] arrB) throws Exception {
    int iLen = arrB.length;
    // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
    StringBuffer sb = new StringBuffer(iLen * 2);
    for (int i = 0; i < iLen; i++) {
      int intTmp = arrB[i];
      // 把负数转换为正数
      while (intTmp < 0) {
        intTmp = intTmp + 256;
      }
      // 小于0F的数需要在前面补0
      if (intTmp < 16) {
        sb.append("0");
      }
      sb.append(Integer.toString(intTmp, 16));
    }
    return sb.toString();
  }

  /**
   * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB) 互为可逆的转换过程
   * 
   * @param strIn 需要转换的字符串
   * @return 转换后的byte数组
   * @throws Exception 本方法不处理任何异常，所有异常全部抛出
   * @author <a href="mailto:leo841001@163.com">LiGuoQing</a>
   */
  public static byte[] hexStr2ByteArr(String strIn) throws Exception {
    byte[] arrB = strIn.getBytes();
    int iLen = arrB.length;

    // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
    byte[] arrOut = new byte[iLen / 2];
    for (int i = 0; i < iLen; i = i + 2) {
      String strTmp = new String(arrB, i, 2);
      arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
    }
    return arrOut;
  }

  /**
   * 默认构造方法，使用默认密钥
   * 
   * @throws Exception
   */
  public DesUtil() throws Exception {
    this(strDefaultKey);
  }

  /**
   * 指定密钥构造方法
   * 
   * @param strKey 指定的密钥
   * @throws Exception
   */
  @SuppressWarnings("restriction")
  public DesUtil(String strKey) throws Exception {
    Security.addProvider(new com.sun.crypto.provider.SunJCE());
    Key key = getKey(strKey.getBytes());

    encryptCipher = Cipher.getInstance("DES");
    encryptCipher.init(Cipher.ENCRYPT_MODE, key);

    decryptCipher = Cipher.getInstance("DES");
    decryptCipher.init(Cipher.DECRYPT_MODE, key);
  }

  /**
   * 加密字节数组
   * 
   * @param arrB 需加密的字节数组
   * @return 加密后的字节数组
   * @throws Exception
   */
  public byte[] encrypt(byte[] arrB) throws Exception {
    return encryptCipher.doFinal(arrB);
  }

  /**
   * 加密字符串
   * 
   * @param strIn 需加密的字符串
   * @return 加密后的字符串
   * @throws Exception
   */
  public String encrypt(String strIn) throws Exception {
    return byteArr2HexStr(encrypt(strIn.getBytes()));
  }

  /**
   * 解密字节数组
   * 
   * @param arrB 需解密的字节数组
   * @return 解密后的字节数组
   * @throws Exception
   */
  public byte[] decrypt(byte[] arrB) throws Exception {
    return decryptCipher.doFinal(arrB);
  }

  /**
   * 解密字符串
   * 
   * @param strIn 需解密的字符串
   * @return 解密后的字符串
   * @throws Exception
   */
  public String decrypt(String strIn) throws Exception {
    return new String(decrypt(hexStr2ByteArr(strIn)));
  }

  /**
   * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
   * 
   * @param arrBTmp 构成该字符串的字节数组
   * @return 生成的密钥
   * @throws java.lang.Exception
   */
  private Key getKey(byte[] arrBTmp) throws Exception {
    // 创建一个空的8位字节数组（默认值为0）
    byte[] arrB = new byte[8];

    // 将原始字节数组转换为8位
    for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
      arrB[i] = arrBTmp[i];
    }

    // 生成密钥
    Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

    return key;
  }

}
