package com.xiaodou.goeasy.publish;

import com.google.gson.Gson;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.goeasy.GoEasy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Publisher
{
  private String appkey;
  private String PUBLISH_URL = "http://goeasy.io/goeasy/publish";
  private static final int CONNECT_TIMEOUT = 3000;
  private static final int READ_TIMEOUT = 3000;
  private int maxRetries = 5;
  private Gson gson = new Gson();

  public Publisher(String appkey) {
    this.appkey = appkey;
  }

  public void publish(String channel, String content, boolean retained, PublishListener publishListener)
  {
    if (isEmpty(channel)) {
      publishListener.onFailed(new GoEasyError(GoEasyErrorCode.PARAMETER_ERROR.code(), "Channel is required"));
      return;
    }
    if (isEmpty(content)) {
      publishListener.onFailed(new GoEasyError(GoEasyErrorCode.PARAMETER_ERROR.code(), "Content is required"));
      return;
    }

    int retried = 0;
    boolean success = false;
    String guid = UUID.randomUUID().toString();
    while ((!(success)) && (retried <= this.maxRetries)) {
      synchronized (guid) {
        try {
          if (retried > 0) {
            LoggerUtil.debug(String.format("retring {} times", retried));
            guid.wait(70L);
          }
        } catch (InterruptedException e) {
          LoggerUtil.error(String.format("Delay publish[{}] error.", guid), e);
        }
      }
      try {
        URL url = new URL(this.PUBLISH_URL);

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept-Charset", "utf-8");
        conn.setRequestProperty("contentType", "utf-8");
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(3000);
        if (this.PUBLISH_URL.startsWith("https://")) {
          System.setProperty("https.protocols", "TLSv1");
          TrustManager[] tm = { new PubSubX509TrustManager() };
          SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
          sslContext.init(null, tm, new SecureRandom());
          SSLSocketFactory ssf = sslContext.getSocketFactory();
          HttpsURLConnection httpsConn = (HttpsURLConnection)conn;
          httpsConn.setSSLSocketFactory(ssf);
        }
        String encodedChannel = URLEncoder.encode(channel, "utf-8");
        String encodedContent = URLEncoder.encode(content, "utf-8");
        String input = "channel=" + encodedChannel + "&content=" + encodedContent + "&retained=" + retained + "&appkey=" + this.appkey + "&guid=" + guid + "&retried=" + retried + "&artifactVersion=" + GoEasy.artifactVersion;
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes("utf-8"));
        os.flush();

        int responseCode = conn.getResponseCode();
        showResult(conn, responseCode, publishListener);

        conn.disconnect();
        success = true;
      } catch (ConnectException e) {
        LoggerUtil.error("Publish failed code[408] with message[Unreachable or timeout]", e);
        onFailed(retried, publishListener, GoEasyErrorCode.UNREACHABLE_TIMEOUT);
      } catch (SocketException e) {
        LoggerUtil.error("Publish failed code[408] with message[Unreachable or timeout]", e);
        onFailed(retried, publishListener, GoEasyErrorCode.UNREACHABLE_TIMEOUT);
      } catch (SocketTimeoutException e) {
        LoggerUtil.error("Publish failed code[408] with message[Unreachable or timeout]", e);
        onFailed(retried, publishListener, GoEasyErrorCode.UNREACHABLE_TIMEOUT.code(), content);
      } catch (UnknownHostException e) {
        LoggerUtil.error("Publish failed code[408] with message[Unreachable or timeout]", e);
        onFailed(retried, publishListener, 408, "java.net.UnknownHostException: " + this.PUBLISH_URL);
      } catch (Exception e) {
        LoggerUtil.error("Publish failed.", e);
        onFailed(retried, publishListener, GoEasyErrorCode.UNKNOWN_ERROR);
      } finally {
        ++retried;
      }
    }
  }

  private void onFailed(int retried, PublishListener publishListener, GoEasyErrorCode errorCode) {
    if (retried == this.maxRetries)
      publishListener.onFailed(new GoEasyError(errorCode.code(), errorCode.content()));
  }

  private void onFailed(int retried, PublishListener publishListener, int customCode, String customMessage)
  {
    if (retried == this.maxRetries)
      publishListener.onFailed(new GoEasyError(customCode, customMessage));
  }

  private void showResult(HttpURLConnection conn, int responseCode, PublishListener publishListener)
    throws IOException
  {
    if (responseCode == 200) {
      publishListener.onSuccess();
    } else {
      BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
      String result = null;
      String temp = in.readLine();
      while (temp != null) {
        if (result != null)
          result = result + temp;
        else
          result = temp;
        temp = in.readLine();
      }

      GoEasyError goEasyError = (GoEasyError)this.gson.fromJson(result, GoEasyError.class);
      publishListener.onFailed(goEasyError);
    }
  }

  private boolean isEmpty(String text) {
    return ((text == null) || (text.trim().length() == 0));
  }

  public void setHttps(boolean https) {
    if (https)
      this.PUBLISH_URL = this.PUBLISH_URL.replace("http://", "https://");
  }

  public void setPublisUrl(String url)
  {
    this.PUBLISH_URL = url;
  }

  class PubSubX509TrustManager
    implements X509TrustManager
  {
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s)
      throws CertificateException
    {
    }

    public void checkServerTrusted(X509Certificate[] x509Certificates, String s)
      throws CertificateException
    {
    }

    public X509Certificate[] getAcceptedIssuers()
    {
      return null;
    }
  }
}
