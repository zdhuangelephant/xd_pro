package com.xiaodou.common.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.thoughtworks.xstream.core.util.Base64Encoder;

/**
 * 
 * @Title:HmacSHA1.java
 * 
 * @Description:HmacSHA1摘要签名计算及校验
 * 
 * @author zhaoyang
 * @date Jan 17, 2014 10:32:18 AM
 * @version V1.0
 */
public class HmacSHA1 {

  public static class Pojo{
    private String scope;
    private String key;
    public String getScope() {
      return StringUtils.isNotBlank(key) ? scope+":"+key : scope;
    }
    public void setScope(String scope) {
      this.scope = scope;
    }
    public void setKey(String key) {
      this.key = key;
    }
    public long getDeadline() {
      return System.currentTimeMillis()/1000+3600;
    }
    
  }
  private static final String HMAC_SHA1 = "HmacSHA1";

  /**
   * 生成签名数据 *
   * 
   * @param keyValue 密钥
   * @param data 待加密的数据
   * @param encode 编码
   * 
   * @throws InvalidKeyException
   * @throws NoSuchAlgorithmException
   */
  public static String getSignature(String keyValue, String data, String encode) throws Exception {
    try {
      SecretKeySpec signingKey = new SecretKeySpec(keyValue.getBytes(encode), HMAC_SHA1);
      Mac mac = Mac.getInstance(HMAC_SHA1);
      mac.init(signingKey);
      byte[] rawHmac = mac.doFinal(data.getBytes(encode));
      return new Base64Encoder().encode(rawHmac);
    } catch (Exception e) {
      throw e;
    }
  }
  
  /**
   * MD5摘要值校验，根据合作方传来的摘要串和原始串，校验摘要值的合法性
   * 
   * @param password 摘要串
   * @param inputString 原始串
   * @param codeType 编码类型
   * @return MD5校验结果
   */
  public static boolean validateSignature(String password, String keyValue, String inputString,
      String codeType) {
    try {
      String inputSignature = getSignature(keyValue, inputString, codeType);
      System.out.println("sign:" + password + "，根据源串：" + inputString + "，生成的签名串:" + inputSignature);
      if (password.equalsIgnoreCase(inputSignature)) {
        return true;
      } else {
        return false;
      }
    } catch (Exception e) {
      return false;
    }

  }

}
