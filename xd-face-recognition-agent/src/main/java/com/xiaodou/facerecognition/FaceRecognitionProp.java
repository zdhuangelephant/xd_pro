package com.xiaodou.facerecognition;

import com.xiaodou.common.util.FileUtil;

/**
 * @name FaceRecognitionProp
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月29日
 * @description 人脸识别API配置项
 * @version 1.0
 */
public class FaceRecognitionProp {
  /**
   * 配置文件
   */
  private static FileUtil faceRecognitionFile = FileUtil
      .getInstance("/conf/custom/env/face_recognition.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (faceRecognitionFile == null)
      synchronized (FaceRecognitionProp.class) {
        if (faceRecognitionFile == null)
          faceRecognitionFile =
              FileUtil.getInstance("/conf/custom/env/face_recognition.properties");
      }
    return faceRecognitionFile;
  }

  /**
   * 获取参数值
   * 
   * @param code 参数key
   * @return 参数值
   */
  public static String getParams(String code) {
    return getProperty().getProperties(code);
  }

  /**
   * 获取Int型配置项
   * 
   * @param code
   * @return
   */
  public static int getInt(String code) {
    return getProperty().getPropertiesInt(code);
  }
}
