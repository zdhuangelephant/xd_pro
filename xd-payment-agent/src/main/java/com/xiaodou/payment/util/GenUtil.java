package com.xiaodou.payment.util;

import com.alibaba.fastjson.annotation.JSONField;

import java.lang.reflect.Field;

/**
 * Date: 2014/7/24
 * Time: 18:55
 *
 * @author Tian.Dong
 */
public class GenUtil {

  public static String generateParams(Object req) throws IllegalAccessException {
    StringBuilder params = new StringBuilder();
    for (Field field : req.getClass().getDeclaredFields()) {
      JSONField jsonField = field.getAnnotation(JSONField.class);
      if (jsonField == null || !jsonField.serialize()) {
        //没有标注，或者标注为不序列化
        continue;
      }
      field.setAccessible(true);
      if (field.get(req) == null) {
        continue;
      }
      params.append(jsonField.name());
      params.append("=");
      params.append(field.get(req).toString());
      params.append("&");
    }
    return params.toString();
  }
}
