package com.xiaodou.common.util;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * <p>
 * RSA公钥/私钥/签名工具包
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 * 
 */
public class RSAUtils {


  /**
   * 加密算法RSA
   */
  public static final String KEY_ALGORITHM = "RSA";

  /**
   * 签名算法
   */
  public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

  /**
   * 获取公钥的key
   */
  private static final String PUBLIC_KEY = "LocatorPublicKey";

  /**
   * 获取私钥的key
   */
  private static final String PRIVATE_KEY = "LocatorPrivateKey";

  /**
   * RSA最大加密明文大小
   */
  private static final int MAX_ENCRYPT_BLOCK = 117;

  /**
   * RSA最大解密密文大小
   */
  private static final int MAX_DECRYPT_BLOCK = 128;

  /** provider BouncyCastleProvider */
  private static final BouncyCastleProvider provider = new BouncyCastleProvider();

  /**
   * <p>
   * 生成密钥对(公钥和私钥)
   * </p>
   * 
   * @return
   * @throws Exception
   */
  public static Map<String, Object> genKeyPair() throws Exception {
    KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
    keyPairGen.initialize(1024);
    KeyPair keyPair = keyPairGen.generateKeyPair();
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    Map<String, Object> keyMap = new HashMap<String, Object>(2);
    keyMap.put(PUBLIC_KEY, publicKey);
    keyMap.put(PRIVATE_KEY, privateKey);
    return keyMap;
  }

  /**
   * <p>
   * 用私钥对信息生成数字签名
   * </p>
   * 
   * @param data 已加密数据
   * @param privateKey 私钥(BASE64编码)
   * 
   * @return
   * @throws Exception
   */
  public static String sign(byte[] data, String privateKey) throws Exception {
    byte[] keyBytes = Base64Utils.decode(privateKey);
    // byte[] keyBytes = Base64.decodeBase64(privateKey.getBytes());
    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
    PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
    Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
    signature.initSign(privateK);
    signature.update(data);
    return Base64Utils.encode(signature.sign());
  }

  /**
   * <p>
   * 校验数字签名
   * </p>
   * 
   * @param data 已加密数据
   * @param publicKey 公钥(BASE64编码)
   * @param sign 数字签名
   * 
   * @return
   * @throws Exception
   * 
   */
  public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
    byte[] keyBytes = Base64Utils.decode(publicKey);
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
    PublicKey publicK = keyFactory.generatePublic(keySpec);
    Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
    signature.initVerify(publicK);
    signature.update(data);
    return signature.verify(Base64Utils.decode(sign));
  }

  /**
   * 获取Cipher实例
   * 
   * @param transformation
   * @param provider
   * @return
   * @throws NoSuchAlgorithmException
   * @throws NoSuchPaddingException
   */
  private static Cipher getInstance(String transformation, Provider provider)
      throws NoSuchAlgorithmException, NoSuchPaddingException {
    if (null == provider)
      return Cipher.getInstance(transformation);
    else
      return Cipher.getInstance(transformation, provider);
  }


  /**
   * <P>
   * 私钥解密
   * </p>
   * 
   * @param encryptedData 已加密数据
   * @param privateKey 私钥(BASE64编码)
   * @return
   * @throws Exception
   */
  public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
      throws Exception {
    return decryptByPrivateKey(encryptedData, privateKey, null);
  }

  /**
   * <P>
   * 私钥解密
   * </p>
   * 
   * @param encryptedData 已加密数据
   * @param privateKey 私钥(BASE64编码)
   * @return
   * @throws Exception
   */
  public static byte[] decryptByPrivateKeyAndBouncyCastleProvider(byte[] encryptedData,
      String privateKey) throws Exception {
    return decryptByPrivateKey(encryptedData, privateKey, provider);
  }

  /**
   * <P>
   * 私钥解密
   * </p>
   * 
   * @param encryptedData 已加密数据
   * @param privateKey 私钥(BASE64编码)
   * @return
   * @throws Exception
   */
  private static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey,
      Provider provider) throws Exception {
    byte[] keyBytes = Base64Utils.decode(privateKey);
    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
    Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
    Cipher cipher = getInstance(keyFactory.getAlgorithm(), provider);
    cipher.init(Cipher.DECRYPT_MODE, privateK);
    int inputLen = encryptedData.length;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int offSet = 0;
    byte[] cache;
    int i = 0;
    // 对数据分段解密
    while (inputLen - offSet > 0) {
      if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
        cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
      } else {
        cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
      }
      out.write(cache, 0, cache.length);
      i++;
      offSet = i * MAX_DECRYPT_BLOCK;
    }
    byte[] decryptedData = out.toByteArray();
    out.close();
    return decryptedData;
  }

  /**
   * <p>
   * 公钥解密
   * </p>
   * 
   * @param encryptedData 已加密数据
   * @param publicKey 公钥(BASE64编码)
   * @return
   * @throws Exception
   */
  public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
    return decryptByPublicKey(encryptedData, publicKey, null);
  }

  /**
   * <p>
   * 公钥解密
   * </p>
   * 
   * @param encryptedData 已加密数据
   * @param publicKey 公钥(BASE64编码)
   * @return
   * @throws Exception
   */
  public static byte[] decryptByPublicKeyAndBouncyCastleProvider(byte[] encryptedData,
      String publicKey) throws Exception {
    return decryptByPublicKey(encryptedData, publicKey, provider);
  }

  /**
   * <p>
   * 公钥解密
   * </p>
   * 
   * @param encryptedData 已加密数据
   * @param publicKey 公钥(BASE64编码)
   * @return
   * @throws Exception
   */
  private static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey, Provider provider)
      throws Exception {
    byte[] keyBytes = Base64Utils.decode(publicKey);
    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
    Key publicK = keyFactory.generatePublic(x509KeySpec);
    Cipher cipher = getInstance(keyFactory.getAlgorithm(), provider);
    cipher.init(Cipher.DECRYPT_MODE, publicK);
    int inputLen = encryptedData.length;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int offSet = 0;
    byte[] cache;
    int i = 0;
    // 对数据分段解密
    while (inputLen - offSet > 0) {
      if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
        cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
      } else {
        cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
      }
      out.write(cache, 0, cache.length);
      i++;
      offSet = i * MAX_DECRYPT_BLOCK;
    }
    byte[] decryptedData = out.toByteArray();
    out.close();
    return decryptedData;
  }

  /**
   * <p>
   * 公钥加密
   * </p>
   * 
   * @param data 源数据
   * @param publicKey 公钥(BASE64编码)
   * @return
   * @throws Exception
   */
  public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
    return encryptByPublicKey(data, publicKey, null);
  }

  /**
   * <p>
   * 公钥加密
   * </p>
   * 
   * @param data 源数据
   * @param publicKey 公钥(BASE64编码)
   * @return
   * @throws Exception
   */
  public static byte[] encryptByPublicKeyAndBouncyCastleProvider(byte[] data, String publicKey)
      throws Exception {
    return encryptByPublicKey(data, publicKey, provider);
  }


  /**
   * <p>
   * 公钥加密
   * </p>
   * 
   * @param data 源数据
   * @param publicKey 公钥(BASE64编码)
   * @return
   * @throws Exception
   */
  private static byte[] encryptByPublicKey(byte[] data, String publicKey, Provider provider)
      throws Exception {
    byte[] keyBytes = Base64Utils.decode(publicKey);
    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
    Key publicK = keyFactory.generatePublic(x509KeySpec);
    // 对数据加密
    Cipher cipher = getInstance(keyFactory.getAlgorithm(), provider);
    cipher.init(Cipher.ENCRYPT_MODE, publicK);
    int inputLen = data.length;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int offSet = 0;
    byte[] cache;
    int i = 0;
    // 对数据分段加密
    while (inputLen - offSet > 0) {
      if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
        cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
      } else {
        cache = cipher.doFinal(data, offSet, inputLen - offSet);
      }
      out.write(cache, 0, cache.length);
      i++;
      offSet = i * MAX_ENCRYPT_BLOCK;
    }
    byte[] encryptedData = out.toByteArray();
    out.close();
    return encryptedData;
  }

  /**
   * <p>
   * 私钥加密
   * </p>
   * 
   * @param data 源数据
   * @param privateKey 私钥(BASE64编码)
   * @return
   * @throws Exception
   */
  public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
    return encryptByPrivateKey(data, privateKey, null);
  }

  /**
   * <p>
   * 私钥加密
   * </p>
   * 
   * @param data 源数据
   * @param privateKey 私钥(BASE64编码)
   * @return
   * @throws Exception
   */
  public static byte[] encryptByPrivateKeyAndBouncyCastleProvider(byte[] data, String privateKey)
      throws Exception {
    return encryptByPrivateKey(data, privateKey, provider);
  }

  /**
   * <p>
   * 私钥加密
   * </p>
   * 
   * @param data 源数据
   * @param privateKey 私钥(BASE64编码)
   * @return
   * @throws Exception
   */
  private static byte[] encryptByPrivateKey(byte[] data, String privateKey, Provider provider)
      throws Exception {
    byte[] keyBytes = Base64Utils.decode(privateKey);
    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
    Cipher cipher = getInstance(keyFactory.getAlgorithm(), provider);
    Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
    cipher.init(Cipher.ENCRYPT_MODE, privateK);
    int inputLen = data.length;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int offSet = 0;
    byte[] cache;
    int i = 0;
    // 对数据分段加密
    while (inputLen - offSet > 0) {
      if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
        cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
      } else {
        cache = cipher.doFinal(data, offSet, inputLen - offSet);
      }
      out.write(cache, 0, cache.length);
      i++;
      offSet = i * MAX_ENCRYPT_BLOCK;
    }
    byte[] encryptedData = out.toByteArray();
    out.close();
    return encryptedData;
  }

  /**
   * <p>
   * 获取私钥
   * </p>
   * 
   * @param keyMap 密钥对
   * @return
   * @throws Exception
   */
  public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
    Key key = (Key) keyMap.get(PRIVATE_KEY);
    return Base64Utils.encode(key.getEncoded());
  }

  /**
   * <p>
   * 获取公钥
   * </p>
   * 
   * @param keyMap 密钥对
   * @return
   * @throws Exception
   */
  public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
    Key key = (Key) keyMap.get(PUBLIC_KEY);
    return Base64Utils.encode(key.getEncoded());
  }

}
