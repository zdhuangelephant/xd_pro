package com.xiaodou.common.util.warp;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xiaodou.common.enums.ClassType;
import com.xiaodou.common.util.log.LoggerUtil;


/**
 * 封装阿里巴巴fast json
 * 
 * Date: 2014/5/9 Time: 11:16
 * 
 * @author Tian.Dong
 */
public class FastJsonUtil {

  public static String toJson(Object obj, SerializerFeature... serializerFeature) {
    return JSON.toJSONString(obj, serializerFeature);
  }

  public static String toJson(Object obj) {
    return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
  }

  public static String toJsonWithDefaultVal(Object obj) {
    if (null == obj) return null;
    Class<? extends Object> objType = obj.getClass();
    if (ClassType.contains(objType)) {
      return ClassType.getStringValue(obj, objType);
    }
    if (!(obj instanceof Map) && !(obj instanceof Collection)) {
      Field[] fs = objType.getDeclaredFields();
      for (int i = 0; i < fs.length; i++) {
        try {
          Field f = fs[i];
          f.setAccessible(true); // 设置些属性是可以访问的
          Object val = f.get(obj);// 得到此属性的值
          if (null != val) continue;
          f.set(obj, ClassType.getDefaultValue(f.getType()));
        } catch (Exception e) {
          e.printStackTrace();
          LoggerUtil.error("toJsonWithDefault error", e);
        }
      }
    }
    return FastJsonUtil.toJson(obj);
  }

  public static <T> T fromJson(String json, Class<T> clazz) {
    return JSON.parseObject(json, clazz);
  }

  public static <T> T fromJsons(String json, TypeReference<T> clazz) {
    return JSON.parseObject(json, clazz);
  }

}
