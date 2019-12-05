package com.xiaodou.facerecognition.impl.faceplus;

import com.xiaodou.facerecognition.FaceRecognitionProp;

/**
 * @name @see com.xiaodou.facerecognition.impl.faceplus.FacePlusProp.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年10月31日
 * @description Face++配置项
 * @version 1.0
 */
public class FacePlusProp {
  /** APP_SECRET APPSECRET参数前缀 */
  private static String APP_SECRET = "face.recognition.faceplus.appsecret";
  /** APP_KEY APPKEY参数前缀 */
  private static String APP_KEY = "face.recognition.faceplus.appkey";
  /** PROTOCOL 请求协议类型 */
  private static String PROTOCOL = "face.recognition.faceplus.protocol";
  /** HOST host地址 */
  private static String HOST = "face.recognition.faceplus.host";
  /** PORT port端口 */
  private static String PORT = "face.recognition.faceplus.port";
  /** TIMEOUT 超时时长 */
  private static String TIMEOUT = "face.recognition.faceplus.timeout";
  /** RETRYTIME 重试次数 */
  private static String RETRYTIME = "face.recognition.faceplus.retrytime";
  /** COMPARE_URL 比较face_url */
  private static String COMPARE_URL = "face.recognition.faceplus.url.compare";

  public static String getAppSecret() {
    return FaceRecognitionProp.getParams(APP_SECRET);
  }

  public static String getAppKey() {
    return FaceRecognitionProp.getParams(APP_KEY);
  }

  public static String getProtocol() {
    return FaceRecognitionProp.getParams(PROTOCOL);
  }

  public static String getHost() {
    return FaceRecognitionProp.getParams(HOST);
  }

  public static Integer getTimeout() {
    return FaceRecognitionProp.getInt(TIMEOUT);
  }

  public static Integer getRetrytime() {
    return FaceRecognitionProp.getInt(RETRYTIME);
  }

  public static Integer getPort() {
    return FaceRecognitionProp.getInt(PORT);
  }

  public static String getCompareUrl() {
    return FaceRecognitionProp.getParams(COMPARE_URL);
  }
}
