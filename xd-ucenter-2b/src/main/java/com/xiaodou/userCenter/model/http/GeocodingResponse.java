package com.xiaodou.userCenter.model.http;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GeocodingResponse extends ResponseBase{

  private Integer status;
  private String message;
  private Result result;

  @Data
  public static class Result {
    private AddressComponent addressComponent;
    //城市id（不再更新）
    private String cityCode;
  }

  @Data
  public static class AddressComponent {
    //国家
    private String country;
    //省名
    private String province;
    //城市名
    private String city;
    //区县名
    private String district;
    //行政区划代码
    private String adcode;
  }
  
}
