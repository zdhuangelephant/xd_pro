package com.xiaodou.summer.dao;

import java.lang.reflect.Field;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;

/**
 * @Title: RelectHelper
 * @Description: 反射工具类 RelectHelper
 * @author Guanguo.Gao
 * @date 2014年11月1日 下午5:09:23
 * 
 */
public class ReflectHelper {
  /**
   * @Title: getFieldByFieldName
   * @Description: 根据名称获得field getFieldByFieldName
   * @param obj
   * @param fieldName
   * @return
   */
  public static Field getFieldByFieldName(Object obj, String fieldName) {
    for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass =
        superClass.getSuperclass()) {
      try {
        return superClass.getDeclaredField(fieldName);
      } catch (NoSuchFieldException e) {
        continue;
      }
    }
    return null;
  }

  /**
   * @Title: getValueByFieldName
   * @Description: getValueByFieldName
   * @param obj
   * @param fieldName
   * @return
   * @throws SecurityException
   * @throws NoSuchFieldException
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   */
  public static Object getValueByFieldName(Object obj, String fieldName)
      throws NoSuchFieldException, IllegalAccessException {
    Field field = getFieldByFieldName(obj, fieldName);
    Object value = null;
    if (field != null) {
      if (field.isAccessible()) {
        value = field.get(obj);
      } else {
        field.setAccessible(true);
        value = field.get(obj);
        field.setAccessible(false);
      }
    }
    return value;
  }

  /**
   * @Title: setValueByFieldName
   * @Description: 设置field的value
   * @param obj
   * @param fieldName
   * @param value
   * @throws SecurityException
   * @throws NoSuchFieldException
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   */
  public static void setValueByFieldName(Object obj, String fieldName, Object value)
      throws NoSuchFieldException, IllegalAccessException {
    Field field = obj.getClass().getDeclaredField(fieldName);
    if (field.isAccessible()) {
      field.set(obj, value);
    } else {
      field.setAccessible(true);
      field.set(obj, value);
      field.setAccessible(false);
    }
  }

  /**
   * @Title: getTargetObject
   * @Description: 获取目标对象
   * @param proxy
   * @param targetClass
   * @return
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public static <T> T getTargetObject(Object proxy, Class<T> targetClass) throws Exception {
    if (AopUtils.isJdkDynamicProxy(proxy)) {
      return (T) ((Advised) proxy).getTargetSource().getTarget();
    } else {
      return (T) proxy; // expected to be cglib proxy then, which is simply a specialized class
    }
  }
}
