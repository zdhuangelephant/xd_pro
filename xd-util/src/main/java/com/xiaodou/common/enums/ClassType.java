package com.xiaodou.common.enums;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name @see com.xiaodou.common.enums.ClassType.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年9月23日
 * @description Class类型
 * @version 1.0
 */
public enum ClassType {
  iNteger(Integer.class) {
    @Override
    public Object getFromString(String value) {
      return Integer.parseInt(value);
    }

    @Override
    Object getDefaultValue() {
      return 0;
    }
  },
  sHort(Short.class) {
    @Override
    public Object getFromString(String value) {
      return Short.parseShort(value);
    }

    @Override
    Object getDefaultValue() {
      return (short) 0;
    }
  },
  lOng(Long.class) {
    @Override
    public Object getFromString(String value) {
      return Long.parseLong(value);
    }

    @Override
    Object getDefaultValue() {
      return 0l;
    }
  },
  cHaracter(Character.class) {
    @Override
    public Object getFromString(String value) {
      return Character.valueOf(value.charAt(0));
    }

    @Override
    Object getDefaultValue() {
      return (char) 0;
    }
  },
  fLoat(Float.class) {
    @Override
    public Object getFromString(String value) {
      return Float.parseFloat(value);
    }

    @Override
    Object getDefaultValue() {
      return 0f;
    }
  },
  dOuble(Double.class) {
    @Override
    public Object getFromString(String value) {
      return Double.parseDouble(value);
    }

    @Override
    Object getDefaultValue() {
      return 0d;
    }
  },
  bOolean(Boolean.class) {
    @Override
    public Object getFromString(String value) {
      return Boolean.parseBoolean(value);
    }

    @Override
    Object getDefaultValue() {
      return Boolean.FALSE;
    }
  },
  bYte(Byte.class) {
    @Override
    public Object getFromString(String value) {
      return Byte.parseByte(value);
    }

    @Override
    Object getDefaultValue() {
      return (byte) 0;
    }
  },
  sTring(String.class) {
    @Override
    public Object getFromString(String value) {
      return value;
    }

    @Override
    Object getDefaultValue() {
      return StringUtils.EMPTY;
    }

    @Override
    String getStringValue(Object value) {
      return JSON.toJSONString(value, SerializerFeature.DisableCircularReferenceDetect);
    }
  },
  lIst(List.class) {
    @Override
    Object getFromString(String value) {
      return FastJsonUtil.fromJson(value, List.class);
    }

    @Override
    Object getDefaultValue() {
      return Lists.newArrayList();
    }

    @Override
    String getStringValue(Object value) {
      return JSON.toJSONString(value, SerializerFeature.DisableCircularReferenceDetect);
    }
  },
  aRrayList(ArrayList.class) {

    @Override
    Object getFromString(String value) {
      return FastJsonUtil.fromJson(value, ArrayList.class);
    }

    @Override
    Object getDefaultValue() {
      return Lists.newArrayList();
    }

    @Override
    String getStringValue(Object value) {
      return JSON.toJSONString(value, SerializerFeature.DisableCircularReferenceDetect);
    }

  },
  lInkedList(LinkedList.class) {

    @Override
    Object getFromString(String value) {
      return FastJsonUtil.fromJson(value, LinkedList.class);
    }

    @Override
    Object getDefaultValue() {
      return Lists.newLinkedList();
    }

    @Override
    String getStringValue(Object value) {
      return JSON.toJSONString(value, SerializerFeature.DisableCircularReferenceDetect);
    }
  },
  mAp(Map.class) {

    @Override
    Object getFromString(String value) {
      return FastJsonUtil.fromJson(value, Map.class);
    }

    @Override
    Object getDefaultValue() {
      return Maps.newHashMap();
    }

    @Override
    String getStringValue(Object value) {
      return JSON.toJSONString(value, SerializerFeature.DisableCircularReferenceDetect);
    }
  },
  hAshMap(HashMap.class) {

    @Override
    Object getFromString(String value) {
      return FastJsonUtil.fromJson(value, HashMap.class);
    }

    @Override
    Object getDefaultValue() {
      return Maps.newHashMap();
    }

    @Override
    String getStringValue(Object value) {
      return JSON.toJSONString(value, SerializerFeature.DisableCircularReferenceDetect);
    }
  },
  tReeMap(TreeMap.class) {
    @Override
    Object getFromString(String value) {
      return FastJsonUtil.fromJson(value, TreeMap.class);
    }

    @Override
    Object getDefaultValue() {
      return Maps.newTreeMap();
    }

    @Override
    String getStringValue(Object value) {
      return JSON.toJSONString(value, SerializerFeature.DisableCircularReferenceDetect);
    }
  },
  lInkedHashMap(LinkedHashMap.class) {
    @Override
    Object getFromString(String value) {
      return FastJsonUtil.fromJson(value, LinkedHashMap.class);
    }

    @Override
    Object getDefaultValue() {
      return Maps.newLinkedHashMap();
    }

    @Override
    String getStringValue(Object value) {
      return JSON.toJSONString(value, SerializerFeature.DisableCircularReferenceDetect);
    }
  },
  cOncurrentHashMap(ConcurrentHashMap.class) {
    @Override
    Object getFromString(String value) {
      return FastJsonUtil.fromJson(value, ConcurrentHashMap.class);
    }

    @Override
    Object getDefaultValue() {
      return Maps.newConcurrentMap();
    }

    @Override
    String getStringValue(Object value) {
      return JSON.toJSONString(value, SerializerFeature.DisableCircularReferenceDetect);
    }

  },
  sEt(Set.class) {

    @Override
    Object getFromString(String value) {
      return FastJsonUtil.fromJson(value, Set.class);
    }

    @Override
    Object getDefaultValue() {
      return Sets.newHashSet();
    }

    @Override
    String getStringValue(Object value) {
      return JSON.toJSONString(value, SerializerFeature.DisableCircularReferenceDetect);
    }
  },
  hAshSet(HashSet.class) {

    @Override
    Object getFromString(String value) {
      return FastJsonUtil.fromJson(value, HashSet.class);
    }

    @Override
    Object getDefaultValue() {
      return Sets.newHashSet();
    }

    @Override
    String getStringValue(Object value) {
      return JSON.toJSONString(value, SerializerFeature.DisableCircularReferenceDetect);
    }
  },
  tImestamp(Timestamp.class) {
    @Override
    Object getFromString(String value) {
      return Timestamp.valueOf(value);
    }

    @Override
    Object getDefaultValue() {
      return new Timestamp(System.currentTimeMillis());
    }

    @Override
    String getStringValue(Object value) {
      return DateUtil.SDF_FULL.format(value);
    }
  },
  dAte(Date.class) {

    @Override
    Object getFromString(String value) {
      try {
        return DateUtil.SDF_FULL.parse(value);
      } catch (ParseException e) {
        return null;
      }
    }

    @Override
    Object getDefaultValue() {
      return new Date();
    }

    @Override
    String getStringValue(Object value) {
      return DateUtil.SDF_FULL.format(value);
    }
  };
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

  abstract Object getFromString(String value);

  abstract Object getDefaultValue();

  String getStringValue(Object value) {
    return value.toString();
  }

  public static Object getValueFromString(String paramValue, Class<?> paramType) {
    if (StringUtils.isBlank(paramValue)) return null;
    if (!_typeMap.containsKey(paramType)) {
      if (StringUtils.isJsonBlank(paramValue)) return null;
      return FastJsonUtil.fromJson(paramValue, paramType);
    }
    return _typeMap.get(paramType).getFromString(paramValue);
  }

  public static String getStringValue(Object resultValue, Class<?> paramType) {
    if (null == resultValue) return StringUtils.EMPTY;
    if (!_typeMap.containsKey(paramType)) {
      return JSON.toJSONString(resultValue, SerializerFeature.DisableCircularReferenceDetect);
    }
    return _typeMap.get(paramType).getStringValue(resultValue);
  }

  public static Object getDefaultValue(Class<?> paramType) {
    if (!_typeMap.containsKey(paramType)) {
      return null;
    }
    return _typeMap.get(paramType).getDefaultValue();
  }

  public static boolean contains(Class<?> clazz) {
    return null != _typeMap && _typeMap.size() > 0 && _typeMap.containsKey(clazz);
  }
}
