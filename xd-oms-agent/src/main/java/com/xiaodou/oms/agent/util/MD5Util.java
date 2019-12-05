package com.xiaodou.oms.agent.util;

import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * 
 * @ClassName: MD5Util
 * @author Zhaoxu.Yang
 * @date Jul 24, 2014 12:10:48 AM
 * 
 */
public final class MD5Util {
  private MD5Util() {

  }

  /**
   * 
   * @Title: getMD5ValiJson
   * @param bodyJson
   * @return String 返回类型
   * */
  @SuppressWarnings("unchecked")
  public static String getMD5ValiJson(String bodyJson) {
    if (StringUtils.isBlank(bodyJson)) {
      return null;
    }

    try {
      // bodyJson to tree map for keys sort
      Map<String, Object> bodyMap = Maps.newTreeMap();
      bodyMap.putAll(FastJsonUtil.fromJson(bodyJson, Map.class));

      // Ignore the two keys : sign,jsonStr
      bodyMap.remove("sign");
      bodyMap.remove("jsonStr");

      StringBuffer md5Sign = new StringBuffer(300);
      String productLine = bodyMap.get("productLine").toString();

      // Add the body : ignore the sign,jsonStr
      for (Object value : bodyMap.values().toArray()) {
        md5Sign.append(value);
      }

      // Add the tail : key
      String tail = OrderConfigService.getKey(productLine);
      md5Sign.append(tail);

      // MD5
      String sign = CommUtil.HEXAndMd5(md5Sign.toString());
      bodyMap.put("sign", sign);
      bodyMap.put("signType", "MD5");
      return FastJsonUtil.toJson(bodyMap);
    } catch (Exception e) {
      LoggerUtil.error("参数签名失败", e);
      return null;
    }
  }

}
