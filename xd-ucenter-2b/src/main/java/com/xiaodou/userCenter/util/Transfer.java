package com.xiaodou.userCenter.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.WordUtils;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.userCenter.module.selfTaught.model.StUserInfo;

/**
 * @name @see com.xiaodou.exercise.util.Transfer.java
 * @CopyRright (c) 2018 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
 * @date 2018年4月16日
 * @description map中存储的数据转换为相对应的Bean或VO
 * @version 1.0
 */
public class Transfer {

  public static void main(String[] args) throws Exception {
    Map<String, Object> moudelInfoMap = CommUtil.getAllField(StUserInfo.class);
    
    System.out.println(moudelInfoMap.toString());
    StUserInfo userInfo = new StUserInfo();
    mapBind1(userInfo.getClass(), moudelInfoMap, userInfo);
    System.out.println(userInfo.toString());
  }

  public static void mapBind1(Class<?> clazz, Map<?, ?> map, Object pmsVo) throws Exception {
//    if (clazz.getName().equals(Object.class.getName())) {
//      return;
//    }
//    mapBind1(clazz.getSuperclass(), map, pmsVo);
//    System.out.println(clazz.getName());
    
    Class<?> superclass = clazz;
    Object supervo = pmsVo;
    while (clazz != null) {
      if (superclass.getName().equals(Object.class.getName())) {
        break;
      }
      mapBind(superclass, map, supervo);
      System.out.println("111"+supervo.toString());
      superclass = superclass.getSuperclass();
      supervo = superclass.newInstance();
    }
  }

  
  public static void transferFromMap2VO111(Class<?> class1, Map<String, Object> params, Object vo) {
    if (class1.getName().equals(Object.class.getName())) return;
    transferFromMap2VO111(class1.getSuperclass(), params, vo);
    Map<String, Method> methodMap = initMethodList(class1.getDeclaredMethods(), "set");
    for (Field argument : class1.getDeclaredFields()) {
      Object value = params.get(WordUtils.uncapitalize(argument.getName()));
      System.err.println(argument.getName().toLowerCase()+":"+value);
      if (value != null) {
        Method method = methodMap.get(argument.getName().toLowerCase());
        if (null != method) {
          try {
            method.invoke(vo, value);
          } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            LoggerUtil.error("[CommUtil][转换Map到VO失败]", e);
            System.out.println(e.getMessage());
            continue;
          }
        }
      }
    }
  }

  
  private static Map<String, Method> initMethodList(Method[] methods, String prefix) {
    Map<String, Method> methodMap = Maps.newHashMap();
    for (Method argument : methods) {
      String methodName = argument.getName().trim();
      if (methodName.startsWith(prefix)) {
        String key = methodName.substring(prefix.length()).toLowerCase();
        methodMap.put(key, argument);
      }
    }
    return methodMap;
  }

  
//public static void main(String[] args) throws Exception {
//  Map<String, Object> moudelInfoMap = CommUtil.getAllField(StUserInfo.class);
//  System.out.println(moudelInfoMap.toString());
//  
//  BaseUserInfo userInfo = new BaseUserInfo();
//  transferFromMap2VO111(userInfo.getClass(), moudelInfoMap, userInfo);
//  System.out.println(userInfo.toString());
//  
//  transferFromMap2VO111(userInfo.getClass().getSuperclass(), moudelInfoMap, userInfo);
//  System.out.println(userInfo.toString());
//  
//}

  
  public static List<Class<?>> getsuperClass(Class<?> calzz) {
    List<Class<?>> listSuperClass = new ArrayList<Class<?>>();
    Class<?> superclass = calzz.getSuperclass();
    while (superclass != null) {
      if (superclass.getName().equals("java.lang.Object")) {
        break;
      }
      listSuperClass.add(superclass);
      superclass = superclass.getSuperclass();
    }
    return listSuperClass;
  }

  // 该方法主要传入的参数有两个，第一个是Map接口，第二个就是要绑定的VO。
  @SuppressWarnings("rawtypes")
  public static void mapBind(Class<?> newClass,Map<?, ?> map, Object pmsVo) throws Exception {
    // 获得传入vo的Class方法
//    Class<? extends Object> newClass = pmsVo.getClass();

//    if (newClass.getName().equals(Object.class.getName())) {
//      return;
//    }
//    mapBind(newClass.getSuperclass(), map, pmsVo);

    // 得到vo中所有的成员变量
    Field[] fs = newClass.getDeclaredFields();
    // 方法变量
    String methodName = null;
    // map的value值
    Object mapValue = null;
    // 参数类型
    String parameterType = null;
    // 查找方法时需要传入的参数
    Class[] parameterTypes = new Class[1];
    // 执行invoke方法时需要传入的参数
    Object[] args = new Object[1];
    // 取得Map的迭代器
    Iterator<?> it = map.keySet().iterator();
    while (it.hasNext()) {
      // 取出map的key值
      String key = (String) it.next();
      if (key != null) {
        for (int i = 0; i < fs.length; i++) {
          if (key.equals(fs[i].getName())) {
            // 拼set方法名
            methodName =
                "set" + key.replaceFirst(key.substring(0, 1), key.substring(0, 1).toUpperCase());
            try {
              // 得到vo中成员变量的类型
              parameterTypes[0] = fs[i].getType();
              parameterType = parameterTypes[0].toString();
              // 找到vo中的方法
              Method method = newClass.getDeclaredMethod(methodName, parameterTypes);
              mapValue = map.get(key);
              // 下面代码都是参数类型是什么，如果有需求可以自行增加
              // 当set方法中的参数为int或者Integer
              if (parameterTypes[0] == Integer.class || parameterTypes[0] == int.class) {
                if (mapValue instanceof Integer) {
                  args[0] = mapValue;
                } else {
                  args[0] = Integer.parseInt((String) mapValue);
                }
                // 当set方法中的参数为Date
              } else if (parameterTypes[0] == Date.class) {
                if (mapValue instanceof Date) {
                  args[0] = mapValue;
                } else {
                  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                  args[0] = sdf.parse((String) mapValue);
                }
                // 当set方法中的参数为Float
              } else if (parameterTypes[0] == double.class || parameterTypes[0] == Double.class) {
                if (mapValue instanceof Double) {
                  args[0] = mapValue;
                } else {
                  args[0] = Double.parseDouble((String) mapValue);
                }
                // 当set方法中的参数为其他
              } else if (parameterTypes[0] == String.class) {

                if (mapValue instanceof String[]) {

                  String[] tempArray = (String[]) mapValue;
                  String result = "";
                  for (int m = 0; m < tempArray.length; m++) {
                    result = result + tempArray[m] + ",";
                  }
                  result = result.substring(0, result.length() - 1);
                  args[0] = result;

                } else {
                  args[0] = (String) mapValue;
                }
              } else {
                args[0] = mapValue;
              }
              // 执行set方法存储数据
              method.invoke(pmsVo, args);

            } catch (SecurityException e) {
              throw new SecurityException("[mapBind]安全异常：" + e);
            } catch (NoSuchMethodException e) {
              throw new NoSuchMethodException("[mapBind]Vo中无此方法异常" + e);
            } catch (IllegalArgumentException e) {
              throw new Exception(
                  "VO中" + key + "属性类型" + parameterType + "与Map中值为" + mapValue + "的类型不匹配");
            } catch (IllegalAccessException e) {
              throw new IllegalAccessException("[mapBind]IllegalAccessException异常");
            } catch (ParseException e) {
              throw new ParseException("[mapBind]ParseException异常", 0);
            }
          }
        }
      }
    }
  }
}
