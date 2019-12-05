package com.xiaodou.mysqladmin.common.utils;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

public class ConvertUtils {
  static {
    registerDateConverter();
  }

  public static <T> List<Object> convertElementPropertyToList(Collection<T> collection,
      String propertyName) {
    List<Object> list = Lists.newArrayList();
    try {
      for (Iterator<T> localIterator = collection.iterator(); localIterator.hasNext();) {
        Object obj = localIterator.next();
        list.add(PropertyUtils.getProperty(obj, propertyName));
      }
    } catch (Exception e) {
      throw Reflections.convertReflectionExceptionToUnchecked(e);
    }
    return list;
  }

  public static <T> String convertElementPropertyToString(Collection<T> collection,
      String propertyName, String separator) {
    List<Object> list = convertElementPropertyToList(collection, propertyName);
    return StringUtils.join(list, separator);
  }

  public static Object convertStringToObject(String value, Class<?> toType) {
    try {
      return org.apache.commons.beanutils.ConvertUtils.convert(value, toType);
    } catch (Exception e) {
      throw Reflections.convertReflectionExceptionToUnchecked(e);
    }
  }

  private static void registerDateConverter() {
    DateConverter dc = new DateConverter();
    dc.setUseLocaleFormat(true);
    dc.setPatterns(new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"});
    org.apache.commons.beanutils.ConvertUtils.register(dc, Date.class);
  }
}
