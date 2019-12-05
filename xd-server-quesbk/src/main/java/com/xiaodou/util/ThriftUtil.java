package com.xiaodou.util;

import java.lang.reflect.Method;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name @see com.xiaodou.thrift.util.ThriftUtil.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年7月20日
 * @description thrift工具类
 * @version 1.0
 */
public class ThriftUtil {
  private final static String PARAM_KEY_TEM = "%s&%s";

  public static Object changeParam(String paramValue, Class<?> paramType) {
    if (null == paramValue) {
      return null;
    }
    switch (ClassType.getTypeByClass(paramType)) {
      case String:
        return paramValue;
      case Int:
        return Integer.valueOf(paramValue);
      case Long:
        return Long.valueOf(paramValue);
      case Boolean:
        return Boolean.valueOf(paramValue);
      case Short:
        return Short.valueOf(paramValue);
      case Float:
        return Float.valueOf(paramValue);
      case Double:
        return Double.valueOf(paramValue);
      case Byte:
        return Byte.valueOf(paramValue);
      case Char:
        return Character.valueOf(paramValue.charAt(0));
      case Object:
        return FastJsonUtil.fromJson(paramValue, paramType);
      default:
        return paramValue;
    }
  }

  public static String changeResult(Object resultValue, Class<?> resultType) {
    if (null == resultValue) {
      return StringUtils.EMPTY;
    }
    switch (ClassType.getTypeByClass(resultType)) {
      case Object:
        return FastJsonUtil.toJson(resultValue);
      default:
        return resultValue.toString();
    }
  }

  public static String getParamterKey(Class<?> clazz, Method method) {
    return getParamterKey(clazz, method.getName());
  }

  public static String getParamterKey(Class<?> clazz, String methodName) {
    return String.format(PARAM_KEY_TEM, clazz.getSimpleName(), methodName);
  }

  enum ClassType {
    Int(Integer.class), Short(Short.class), Long(Long.class), Char(Character.class), Float(
        Float.class), Double(Double.class), Boolean(Boolean.class), Byte(Byte.class), String(
        String.class), Object(Object.class);
    private Class<?> clazz;

    private static final Map<Class<?>, ClassType> _typeMap = Maps.newHashMap();

    static {
      for (ClassType type : ClassType.values()) {
        _typeMap.put(type.clazz, type);
      }
    }

    public Class<?> getClazz() {
      return clazz;
    }

    public void setClazz(Class<?> clazz) {
      this.clazz = clazz;
    }

    ClassType(Class<?> clazz) {
      this.clazz = clazz;
    }

    public static ClassType getTypeByClass(Class<?> clazz) {
      if (!_typeMap.containsKey(clazz)) {
        return Object;
      }
      return _typeMap.get(clazz);
    }
  }

}
