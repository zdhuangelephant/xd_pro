package com.xiaodou.resources.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
   
public class DESUtil {
  //算法名称 
  public static final String KEY_ALGORITHM = "DES";
  //算法名称/加密模式/填充方式 
  //DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
  public static final String CIPHER_ALGORITHM = "DES";

  /**
   *   
   * 生成密钥key对象
   * @param KeyStr 密钥字符串 
   * @return 密钥对象 
   * @throws InvalidKeyException   
   * @throws NoSuchAlgorithmException   
   * @throws InvalidKeySpecException   
   * @throws Exception 
   */
  private static SecretKey keyGenerator(String keyStr) throws Exception {
      byte input[] = hexStringToBytes(keyStr);
      DESKeySpec desKey = new DESKeySpec(input);
      //创建一个密匙工厂，然后用它把DESKeySpec转换成
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
      SecretKey securekey = keyFactory.generateSecret(desKey);
      return securekey;
  }

//  private static int parse(char c) {
//      if (c >= 'a') return (c - 'a' + 10) & 0x0f;
//      if (c >= 'A') return (c - 'A' + 10) & 0x0f;
//      return (c - '0') & 0x0f;
//  }
//
//  // 从十六进制字符串到字节数组转换 
//  public static byte[] HexString2Bytes(String hexstr) {
//      byte[] b = new byte[hexstr.length() / 2];
//      int j = 0;
//      for (int i = 0; i < b.length; i++) {
//          char c0 = hexstr.charAt(j++);
//          char c1 = hexstr.charAt(j++);
//          b[i] = (byte) ((parse(c0) << 4) | parse(c1));
//      }
//      return b;
//  }

  /** 
   * 加密数据
   * @param data 待加密数据
   * @param key 密钥
   * @return 加密后的数据 
   */
  public static String encrypt(String data, String key) throws Exception {
      Key deskey = keyGenerator(key);
      // 实例化Cipher对象，它用于完成实际的加密操作
      Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
      SecureRandom random = new SecureRandom();
      // 初始化Cipher对象，设置为加密模式
      cipher.init(Cipher.ENCRYPT_MODE, deskey, random);
      byte[] results = cipher.doFinal(data.getBytes());
      // 该部分是为了与加解密在线测试网站（http://tripledes.online-domain-tools.com/）的十六进制结果进行核对
      for (int i = 0; i < results.length; i++) {
          System.out.print(results[i] + " ");
      }
      System.out.println();
      // 执行加密操作。加密后的结果通常都会用Base64编码进行传输 
      return bytesToHexString(Base64.encodeBase64(results));
  }

  /** 
   * 解密数据 
   * @param data 待解密数据 
   * @param key 密钥 
   * @return 解密后的数据 
   */
  public static String decrypt(String data, String key) throws Exception {
      Key deskey = keyGenerator(key);
      Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
      //初始化Cipher对象，设置为解密模式
      cipher.init(Cipher.DECRYPT_MODE, deskey);
      // 执行解密操作
      return new String(cipher.doFinal(Base64.decodeBase64(hexStringToBytes(data))));
  }
  
  public static String bytesToHexString(byte[] src){  
    StringBuilder stringBuilder = new StringBuilder("");  
    if (src == null || src.length <= 0) {  
        return null;  
    }  
    for (int i = 0; i < src.length; i++) {  
        int v = src[i] & 0xFF;  
        String hv = Integer.toHexString(v);  
        if (hv.length() < 2) {  
            stringBuilder.append(0);  
        }  
        stringBuilder.append(hv);  
    }  
    return stringBuilder.toString();  
 }  


 public static byte[] hexStringToBytes(String hexString) {  
    if (hexString == null || hexString.equals("")) {  
        return null;  
    }  
    hexString = hexString.toUpperCase();  
    int length = hexString.length() / 2;  
    char[] hexChars = hexString.toCharArray();  
    byte[] d = new byte[length];  
    for (int i = 0; i < length; i++) {  
        int pos = i * 2;  
        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
    }  
    return d;  
 }
 
 private static byte charToByte(char c) {  
   return (byte) "0123456789ABCDEF".indexOf(c);  
} 

  public static void main(String[] args) throws Exception {
      String source = "eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个eedkkkkls;我是好人呀姐姐斤斤计较斤斤计较斤斤计较就斤斤计较斤斤计较是生生世世生生世世生生世世生生世世让我跟哥哥哥哥哥哥哥哥开启全民密密麻麻密密麻麻毛线啊个";
      System.out.println("原文: " + source);
      String key = "A1B2C3D4E5F60708";
      String encryptData = encrypt(source, key);
      System.out.println("加密后: " + encryptData);
      String decryptData = decrypt(encryptData, key);
      System.out.println("解密后: " + decryptData);
  }
}

