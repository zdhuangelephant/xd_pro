package com.xiaodou.facerecognition.impl.eyekey;

import java.util.List;

import lombok.Data;

/**
 * @name EyeKeyTransportEntity
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月29日
 * @description EyeKey数据传输对象
 * @version 1.0
 */
public class EyeKeyTransportEntity {
  @Data
  public static class Check {
    private String message;
    // 1011 图片获取异常
    // 0000 正常
    private String res_code;
    private List<Face> face;
    private Double similarity;
  }

  @Data
  public static class Face {
    private String face_id;
  }
}
