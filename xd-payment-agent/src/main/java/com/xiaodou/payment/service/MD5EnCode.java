package com.xiaodou.payment.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5EnCode {

  public static String MD5Sign(String strs)
      throws NoSuchAlgorithmException, UnsupportedEncodingException {
    String result;
    char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
        'E', 'F'};
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(strs.getBytes("utf-8"));
    byte tmp[] = md.digest();// MD5 的计算结果是一个 128 位的长整数，用字节表示就是 16 个字节
    char chars[] = new char[16 * 2];// 每个字节用 16 进制表示   需要 32 个字符
    for (int i = 0; i < 16; i++) {// 从第一个字节开始，对 MD5 的每一个字节
      // 转换成 16 进制字符的转换
      byte byte0 = tmp[i];// 取第 i 个字节
      chars[2 * i] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换
      chars[2 * i + 1] = hexDigits[byte0 & 0xf];// 取字节中低 4 位的数字转换
    }
    result = new String(chars);// 换后的结果转换为字符串
    return result;
  }
}
