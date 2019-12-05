package com.xiaodou.facerecognition.impl.faceplus;

import java.util.Map;

import lombok.Data;

/**
 * @name @see com.xiaodou.facerecognition.impl.faceplus.FacePlusTransportEntity.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年10月31日
 * @description Face++数据传输对象
 * @version 1.0
 */
public class FacePlusTransportEntity {

  @Data
  public static class Compare {
    private String request_id;
    private Float confidence;
    private Map<String, Float> thresholds;
    private String image_id1;
    private String image_id2;
    private Integer time_used;
    private String error_message;
  }

}
