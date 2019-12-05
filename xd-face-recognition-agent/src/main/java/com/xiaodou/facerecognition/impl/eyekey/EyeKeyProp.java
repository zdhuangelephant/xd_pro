package com.xiaodou.facerecognition.impl.eyekey;

import com.xiaodou.facerecognition.FaceRecognitionProp;

/**
 * @name EyeKeyProp
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月29日
 * @description EyeKey配置项
 * @version 1.0
 */
public class EyeKeyProp {

  /** APP_ID APPID参数前缀 */
  private static String APP_ID = "face.recognition.eyekey.appid";
  /** APP_KEY APPKEY参数前缀 */
  private static String APP_KEY = "face.recognition.eyekey.appkey";
  /** PROTOCOL 请求协议类型 */
  private static String PROTOCOL = "face.recognition.eyekey.protocol";
  /** HOST host地址 */
  private static String HOST = "face.recognition.eyekey.host";
  /** PORT port端口 */
  private static String PORT = "face.recognition.eyekey.port";
  /** TIMEOUT 超时时长 */
  private static String TIMEOUT = "face.recognition.eyekey.timeout";
  /** RETRYTIME 重试次数 */
  private static String RETRYTIME = "face.recognition.eyekey.retrytime";
  /** ADD_FACE_URL 添加face_url */
  private static String ADD_FACE_URL = "face.recognition.eyekey.url.addface";
  /** COMPARE_URL 比较face_url */
  private static String COMPARE_URL = "face.recognition.eyekey.url.compare";

  public static String getAppId() {
    return FaceRecognitionProp.getParams(APP_ID);
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

  public static String getAddFaceUrl() {
    return FaceRecognitionProp.getParams(ADD_FACE_URL);
  }

  public static String getCompareUrl() {
    return FaceRecognitionProp.getParams(COMPARE_URL);
  }
}
