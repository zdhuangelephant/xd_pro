package com.xiaodou.server.pay.payplatform.dc.wxh5pay;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 第四步：信任管理器 微信的信任证书 MyX509TrustManager 信任管理器
 * 
 * @author Mark
 */
public class MyX509TrustManager implements X509TrustManager {

  // 检查客户端证书
  public void checkClientTrusted(X509Certificate[] chain, String authType)
      throws CertificateException {}

  // 检查服务器端证书
  public void checkServerTrusted(X509Certificate[] chain, String authType)
      throws CertificateException {}

  // 返回受信任的X509证书数组
  public X509Certificate[] getAcceptedIssuers() {
    return null;
  }
}
