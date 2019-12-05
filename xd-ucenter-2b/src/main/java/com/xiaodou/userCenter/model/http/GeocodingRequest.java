package com.xiaodou.userCenter.model.http;

import lombok.Data;

@Data
public class GeocodingRequest {
  /**
   * 输出格式为json或者xml
   * 是否必需：  否
   * 默认值：xml
   */
  private String output;
  /**
   * 含义：经纬度
   * 是否必需：  是
   */
  private String location;
  private String ak;
  
}
