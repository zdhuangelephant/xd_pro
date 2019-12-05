package com.xiaodou.forum.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * 封装阿里巴巴fast json
 * 
 * Date: 2014/5/9 Time: 11:16
 * 
 * @author Tian.Dong
 */
public class FastJsonUtil {

  public static String toJson(Object obj) {
    return JSON.toJSONString(obj);
  }

  public static <T> T fromJson(String json, Class<T> clazz) {
    return JSON.parseObject(json, clazz);
  }

  public static <T> T fromJsons(String json, TypeReference<T> clazz) {
    return JSON.parseObject(json, clazz);
  }

}
