package com.xiaodou.server.pay.util;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * 计算签名
 * <p/>
 * Date: 2014/7/18 Time: 15:59
 * 
 * @author Tian.Dong
 */
public class SignUtil {
  private static DecimalFormat dFormat = new DecimalFormat("#0.00");

  public static String sign(Object obj, String key) {
    String sign = null;
    Map<String, String> map = new TreeMap<String, String>();
    try {
      fillMap(map, obj);
    } catch (IllegalAccessException e) {
      LoggerUtil.error("SignUtil生成签名异常", e);
      return null;
    }
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, String> entry : map.entrySet()) {
      sb.append(entry.getValue());
    }
    sb.append(key);
    try {
      sign = CommUtil.HEXAndMd5(sb.toString()).toUpperCase();
    } catch (Exception e) {
      LoggerUtil.error("请求payment签名失败", e);
    }
    return sign;
  }

  private static void fillMap(Map<String, String> map, Object obj) throws IllegalAccessException {
    for (Field field : obj.getClass().getDeclaredFields()) {
      JSONField jsonField = field.getAnnotation(JSONField.class);
      if (jsonField == null || !jsonField.serialize()) {
        // 没有标注，或者标注为不序列化
        continue;
      }
      field.setAccessible(true);
      if (field.get(obj) == null) {
        continue;
      }
      if (field.getType().equals(String.class) || field.getType().equals(Integer.class)
          || field.getType().equals(Long.class)) {
        map.put(jsonField.name(), field.get(obj).toString());
      } else if (field.getType().equals(Double.class)) {
        map.put(jsonField.name(), dFormat.format(field.get(obj)));
      } else {
        fillMap(map, field.get(obj));
      }
    }

  }
}
