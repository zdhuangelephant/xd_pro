package com.xiaodou.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.common.util.log.LoggerUtil;

/**
 * 
 * @Title:EncryptUtil.java
 * 
 * @Description:
 * 
 * @author zhaoyang
 * @date Jan 17, 2014 10:25:53 AM
 * @version V1.0
 */
public class EncryptUtil {

  private static final String Algorithm = "DESede"; // 定义加密算法,可用
  // DES,DESede,Blowfish
  private static final byte[] ecodeStr = {(byte) 0xb9, (byte) 0x89, (byte) 0x84, (byte) 0xcf,
      (byte) 0x9f, (byte) 0xd0, (byte) 0xc5, 0x78, 0x19, (byte) 0xe9, (byte) 0x56, (byte) 0x55,
      (byte) 0xbc, (byte) 0x62, (byte) 0xda, (byte) 0x98, (byte) 0x82, (byte) 0e8, 0x4c,
      (byte) 0xda, (byte) 0x2f, (byte) 0xdf, (byte) 0xe6, 0x55};

  // keybyte为加密密钥，长度为24字节
  // src为被加密的数据缓冲区（源）
  public static byte[] encryptMode(byte[] keybyte, byte[] src) {
    try {
      // 生成密钥
      SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
      // 加密
      Cipher c1 = Cipher.getInstance(Algorithm);
      c1.init(Cipher.ENCRYPT_MODE, deskey);
      return c1.doFinal(src);// 在单一方面的加密或解密
    } catch (java.security.NoSuchAlgorithmException e1) {
      // TODO: handle exception
      e1.printStackTrace();
    } catch (javax.crypto.NoSuchPaddingException e2) {
      e2.printStackTrace();
    } catch (java.lang.Exception e3) {
      e3.printStackTrace();
    }
    return null;
  }

  // keybyte为加密密钥，长度为24字节
  // src为加密后的缓冲区
  public static byte[] decryptMode(byte[] keybyte, byte[] src) {
    try {
      // 生成密钥
      SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
      // 解密
      Cipher c1 = Cipher.getInstance(Algorithm + "/ECB/PKCS5Padding");
      c1.init(Cipher.DECRYPT_MODE, deskey);
      return c1.doFinal(src);
    } catch (java.security.NoSuchAlgorithmException e1) {
      // TODO: handle exception
      e1.printStackTrace();
    } catch (javax.crypto.NoSuchPaddingException e2) {
      e2.printStackTrace();
    } catch (java.lang.Exception e3) {
      e3.printStackTrace();
    }
    return null;
  }

  /*
   * 生成密钥 encodeKeyA.length=24
   */
  public static byte[] genCroptyKey(byte[] encodeKeyA, String randomStrB) {
    if (encodeKeyA == null) {
      return null;
    }
    byte[] A = encodeKeyA;
    byte[] B = new byte[24];
    byte[] C = randomStrB.getBytes();
    int alen = A.length;
    int clen = C.length;
    if (alen != 24 || (clen < 8 || clen > 20)) return null;
    int demension = alen - clen;
    for (int i = 0; i < C.length; i++) {
      B[i] = C[i];
    }

    int piont = 1;
    while (demension > 0) {
      if (demension > clen) {
        for (int i = 0; i < clen; i++) {
          B[clen * piont + i] = C[i];
        }
        piont++;

      } else {
        for (int i = 0; i < demension; i++) {
          B[clen * piont + i] = C[i];
        }
      }
      demension = demension - clen;

    }
    byte[] result = new byte[24];
    for (int i = 0; i < alen; i++) { // 0 ^ 1 | 2 &

      switch ((i + 1) % 3) {
        case 0:
          result[i] = (byte) (A[i] ^ B[i]);
          break;
        case 1:
          result[i] = (byte) (A[i] & B[i]);
          break;
        case 2:
          result[i] = (byte) (A[i] | B[i]);
          break;
      }

    }

    System.out.println("key=" + result.toString());

    return result;
  }

  public static String getBASE64(byte[] b) {
    String s = null;
    if (b != null) {
      s = Base64Utils.encode(b);
    }
    return s;
  }

  public static byte[] getFromBASE64(String s) {
    byte[] b = null;
    if (s != null) {
      try {
        b = Base64Utils.decode(s);
        return b;
      } catch (Exception e) {
        LoggerUtil.error("获取Base64编码异常", e);
      }
    }
    return b;
  }

  /**
   * 传输加密参数，组合
   * 
   * @param oraStr
   * @param key
   * @return
   */
  public static String restructParam(String oraStr) {

    byte[] keys = EncryptUtil.ecodeStr;
    if (StringUtils.isNotBlank(oraStr)) {

      String stampStr = EncryptUtil.RndString(10, null);
      byte[] encodeKeys = EncryptUtil.genCroptyKey(keys, stampStr);
      byte[] result = EncryptUtil.encryptMode(encodeKeys, oraStr.getBytes());
      String base64Str = EncryptUtil.getBASE64(result);
      try {
        String data = URLEncoder.encode(base64Str, "utf-8");
        String lastStr = data + "&stamp=" + URLEncoder.encode(stampStr, "utf-8");
        return lastStr;
      } catch (UnsupportedEncodingException e) {
        // TODO Auto-generated catch block
        System.out.println("加密编码错误：" + oraStr);
        LoggerUtil.error("加密编码错误：", e);
      }
    }
    return null;
  }

  /**
   * 生成随机字符串
   */
  public static String RndString(int Length, int[] Seed) {
    String strSep = ",";
    // char[] chrSep = strSep.ToCharArray();

    // 这里定义字符集
    String strChar =
        "0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z"
            + ",A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,W,X,Y,Z";

    String[] aryChar = strChar.split(strSep, strChar.length());

    String strRandom = "";
    Random Rnd;
    if (Seed != null && Seed.length > 0) {
      Rnd = new Random(Seed[0]);
    } else {
      Rnd = new Random();
    }

    // 生成随机字符串
    for (int i = 0; i < Length; i++) {
      strRandom += aryChar[Rnd.nextInt(aryChar.length)];
    }

    return strRandom;
  }

}
