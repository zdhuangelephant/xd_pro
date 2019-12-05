package com.xiaodou.userCenter.service.http;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.userCenter.enums.Especially;
import com.xiaodou.userCenter.model.http.GeocodingResponse;
import com.xiaodou.userCenter.model.http.GeocodingResponse.Result;
import com.xiaodou.userCenter.util.HttpUtil;


@Service("httpService")
public class HttpService {

  private static final String Geocoding_Url = "http://api.map.baidu.com/geocoder/v2/";
  private static final String ak = "EQHDc9PxQkZcdlZTfAMZKpWWGATLSCTa";

  /**
   * 
   * @description 逆地理编码服务
   * @author 李德洪
   * @Date 2017年5月15日
   * @param longitude 经度坐标
   * @param Latitude 纬度坐标
   * lat<纬度>,lng<经度>
   */
  public static Result geocodingByBaidu(float latitude,float longitude) {
    // 发送请求
    String jsonStr =
        HttpUtil.sendGet(Geocoding_Url, "location=" + latitude + "," + longitude + "&output=json&ak=" + ak);
    GeocodingResponse response = FastJsonUtil.fromJson(jsonStr, GeocodingResponse.class);
    if (null != response && response.getStatus() == 0) {
      return response.getResult();
    }
    return null;
  }

  public static boolean isEspecially(String cityCode) {
    for (Especially e : Especially.values()) {
      if (cityCode.equals(e.getCityCode())) return true;
    }
    return false;
  }
  
}
