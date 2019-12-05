package com.xiaodou.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.xiaodou.common.annotation.BaseField;
import com.xiaodou.common.annotation.GeneralField;
import com.xiaodou.common.annotation.ShowField;
import com.xiaodou.common.enums.ClassType;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.reflect.ReflectionField;

/**
 * 公共方法类
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-1-14
 */
public class CommUtil {

  /**
   * 日期验证正则表达式
   */
  private static String dateValidationExpression =
      "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
  /**
   * 时间验证正则表达式
   */
  private static String timeValidationExpression =
      "(\\s(((0?[0-9])|(1?[0-9])|(2?[0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";

  /**
   * 验证日期格式是否正确
   * 
   * @param sdate 待验证日期
   * @return 验证结果
   */
  public static boolean validateTimeExp(String sdate) {
    if (StringUtils.isBlank(sdate)) return false;
    Pattern p = Pattern.compile(dateValidationExpression);
    if (sdate.length() > 10) {
      p = Pattern.compile(dateValidationExpression + timeValidationExpression);
    }
    Matcher m = p.matcher(sdate);
    return m.matches();
  }

  /**
   * Md5加密算法
   * 
   * @param plainText
   */
  public static String HEXAndMd5(String plainText) throws Exception {
    return HEXAndMd5(plainText.getBytes("UTF8"));
  }

  /**
   * Md5加密算法
   * 
   * @param plainText
   */
  public static String HEXAndMd5(byte[] plainText) throws Exception {
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(plainText);
    byte b[] = md.digest();
    int i;
    StringBuffer buf = new StringBuffer(200);
    for (int offset = 0; offset < b.length; offset++) {
      i = b[offset] & 0xff;
      if (i < 16) buf.append("0");
      buf.append(Integer.toHexString(i));
    }
    return buf.toString();
  }

  /**
   * 获取并删除Map中的键
   * 
   * @param map map
   * @param key 键
   * @return 值
   */
  public static Object getValAndDelKey(Map<String, Object> map, String key) {
    if (!map.containsKey(key)) return null;
    Object value = map.get(key);
    map.remove(key);
    return value;
  }

  /**
   * 转化Map->VO
   * 
   * @param class1 VO类型
   * @param params 属性值Map
   * @param vo VO
   */
  public static void transferFromMap2VO(Class<?> class1, Map<String, Object> params, Object vo) {
    if (class1.getName().equals(Object.class.getName())) return;
    transferFromMap2VO(class1.getSuperclass(), params, vo);
    Map<String, Method> methodMap = initMethodList(class1.getDeclaredMethods(), "set");
    for (Field argument : class1.getDeclaredFields()) {
      Object value = ReflectionField.getField(argument, vo);
      if (value != null) {
        Method method = methodMap.get(argument.getName().toLowerCase());
        if (null != method) {
          try {
            method.invoke(vo, value);
          } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            LoggerUtil.error("[CommUtil][转换Map到VO失败]", e);
            continue;
          }
        }
      }
    }
  }

  /**
   * 初始化带前缀方法映射
   * 
   * @param methods 方法数组
   * @param prefix 方法前缀
   * @return 方法映射Map
   */
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

  /**
   * 转化Map->VO
   * 
   * @param class1 VO类型
   * @param params 属性值Map
   * @param vo VO
   */
  public static void transferFromMap2VO(Map<String, Object> params, Object vo) {
    transferFromMap2VO(vo.getClass(), params, vo);
  }

  /**
   * 转化VO->Map
   * 
   * @param class1 VO类型
   * @param params 属性值Map
   * @param vo VO
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   */
  public static void transferFromVO2Map(Class<?> class1, Map<String, Object> params, Object vo,
      Function<Field, Object> func) {
    if (class1.getName().equals(Object.class.getName())) return;
    for (Field field : class1.getDeclaredFields()) {
      func.apply(field);
    }
    transferFromVO2Map(class1.getSuperclass(), params, vo, func);
  }

  /**
   * 检验对象是否为空,若为空则返回null
   * 
   * @param obj 原对象
   * @return 校验结果
   */
  public static Object checkNull(Object obj) {
    return checkNull(obj, null);
  }

  /**
   * 检验对象是否为空,若为空则返回预置的替换值
   * 
   * @param srcObj 原对象
   * @param outObj 替换对象
   * @return 校验结果
   */
  public static Object checkNull(Object srcObj, Object outObj) {
    return null == srcObj ? outObj : srcObj;
  }

  /**
   * 转化VO->Map
   * 
   * @param params 属性值Map
   * @param vo VO
   */
  public static void transferFromVO2Map(final Map<String, Object> params, final Object vo) {
    transferFromVO2Map(vo.getClass(), params, vo, new Function<Field, Object>() {
      @Override
      public Object apply(Field field) {
        field.setAccessible(true);
        try {
          Object value = checkNull(ReflectionField.getField(field, vo));
          if (null != value) params.put(field.getName(), value);
        } catch (Exception e) {
          LoggerUtil.error("[CommUtil][转换VO到Map失败]", e);
        }
        field.setAccessible(false);
        return null;
      }
    });
  }

  /**
   * 获取服务器IP
   * 
   * @return 服务器IP
   */
  public static String getServerIp() {
    try {
      InetAddress addr = InetAddress.getLocalHost();
      return addr.getHostAddress().toString();// 获得本机IP
    } catch (UnknownHostException e) {
      LoggerUtil.error("Err:Unknown Host.", e);
      return null;
    }
  }

  /**
   * 获取服务器名
   * 
   * @return 服务器名
   */
  public static String getServerName() {
    try {
      InetAddress addr = InetAddress.getLocalHost();
      return addr.getHostName().toString();// 获得机器名
    } catch (UnknownHostException e) {
      LoggerUtil.error("Err:Unknown Host.", e);
      return "";
    }
  }

  /**
   * 封装属性到Map的方法
   * 
   * @param paramnames 待封装属性名
   * @return 封装后属性Map
   */
  public static TreeMap<String, Object> getParams(Object target, Object... paramnames) {
    TreeMap<String, Object> params = Maps.newTreeMap();
    for (Object name : paramnames)
      try {
        Field field = target.getClass().getDeclaredField(name.toString());
        params.put(name.toString(), ReflectionField.getField(field, target));
      } catch (Exception e) {
        LoggerUtil.error("Err:获取参数Map失败:" + name, e);
      }
    return params;
  }

  /**
   * 封装属性到Map的方法
   * 
   * @return 封装后属性Map
   */
  public static TreeMap<String, Object> getParams(Object target) {
    TreeMap<String, Object> params = Maps.newTreeMap();
    for (Field argument : target.getClass().getDeclaredFields()) {
      try {
        if (null != ReflectionField.getField(argument, target))
          params.put(argument.getName(), ReflectionField.getField(argument, target));
      } catch (Exception e) {
        LoggerUtil.error("Err:获取参数Map失败:" + argument.getName(), e);
      }
    }
    return params;
  }

  public static void getSelectField(final Map<String, Object> params, final Class<?> clazz,
      final Class<? extends Annotation> anno) {
    transferFromVO2Map(clazz, params, null, new Function<Field, Object>() {
      @Override
      public Object apply(Field field) {
        field.setAccessible(true);
        try {
          if (anno == null || field.getAnnotation(anno) != null) {
            params.put(field.getName(), "1");
          }
        } catch (Exception e) {
          LoggerUtil.error("[CommUtil][转换VO到Map失败]", e);
        }
        field.setAccessible(false);
        return null;
      }
    });
  }

  public static Map<String, Object> getShowField(final Object obj) {
    if (null == obj) return null;
    final Map<String, Object> params = Maps.newHashMap();
    transferFromVO2Map(obj.getClass(), params, obj, new Function<Field, Object>() {
      @Override
      public Object apply(Field field) {
        field.setAccessible(true);
        try {
          if (field.getAnnotation(ShowField.class) != null) {
            params.put(field.getName(), field.get(obj));
          }
        } catch (Exception e) {
          LoggerUtil.error("[CommUtil][转换VO到Map失败]", e);
        }
        field.setAccessible(false);
        return null;
      }
    });
    return params;
  }

  public static void getBasicField(final Map<String, Object> params, final Class<?> clazz) {
    getSelectField(params, clazz, BaseField.class);
  }

  public static void getGeneralField(final Map<String, Object> params, final Class<?> clazz) {
    getSelectField(params, clazz, GeneralField.class);
  }

  public static void getAllField(final Map<String, Object> params, final Class<?> clazz) {
    getSelectField(params, clazz, null);
  }

  public static Map<String, Object> getBasicField(final Class<?> clazz) {
    Map<String, Object> params = Maps.newHashMap();
    getSelectField(params, clazz, BaseField.class);
    return params;
  }

  public static Map<String, Object> getGeneralField(final Class<?> clazz) {
    Map<String, Object> params = Maps.newHashMap();
    getSelectField(params, clazz, GeneralField.class);
    return params;
  }

  public static Map<String, Object> getAllField(final Class<?> clazz) {
    Map<String, Object> params = Maps.newHashMap();
    getSelectField(params, clazz, null);
    return params;
  }

  public static Object changeFromString(String paramValue, Class<?> paramType) {
    if (null == paramValue) {
      return null;
    }
    return ClassType.getValueFromString(paramValue, paramType);
  }

  public static String change2String(Object resultValue, Class<?> resultType) {
    if (null == resultValue) {
      return StringUtils.EMPTY;
    }
    return ClassType.getStringValue(resultValue, resultType);
  }

  /**
   * Returns the MAC address of the computer.
   * 
   * @return the MAC address
   */
  public static String getMACAddress() {
    String address = "";
    if (OSInfo.isWindows()) {
      try {
        String command = "cmd.exe /c ipconfig /all";
        Process p = Runtime.getRuntime().exec(command);
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
          if (line.indexOf("Physical Address") > 0) {
            int index = line.indexOf(":");
            index += 2;
            address = line.substring(index);
            break;
          }
        }
        br.close();
        return address.trim();
      } catch (IOException e) {}
    } else if (OSInfo.isLinux()) {
      String command = "/bin/sh -c ifconfig -a";
      Process p;
      try {
        p = Runtime.getRuntime().exec(command);
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
          if (line.indexOf("HWaddr") > 0) {
            int index = line.indexOf("HWaddr") + "HWaddr".length();
            address = line.substring(index);
            break;
          }
        }
        br.close();
      } catch (IOException e) {}
    }
    address = address.trim();
    return address;
  }

  /**
   * 判断给定类的父子关系
   * 
   * @param sub 指定子类
   * @param sup 指定父类
   * @return 是否父子
   */
  public static boolean isSub(Class<?> sub, Class<?> sup) {
    if (null == sub || null == sup) return false;
    if (Object.class.equals(sup)) return true;
    Class<?> parent = sub.getSuperclass();
    if (Object.class.equals(parent)) return false;
    if (sup.equals(parent)) return true;
    return isSub(parent, sup);
  }

  /**
   * 获取本机ip地址，并自动区分Windows还是linux操作系统
   * 
   * @return String
   */
  public static String getLocalIP() {
    String sIP = "";
    InetAddress ip = null;
    try {
      // 如果是Windows操作系统
      if (OSInfo.isWindows()) {
        ip = InetAddress.getLocalHost();
      }
      // 如果是Linux操作系统
      else if (OSInfo.isLinux()) {
        boolean bFindIP = false;
        Enumeration<NetworkInterface> netInterfaces =
            (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
        while (netInterfaces.hasMoreElements()) {
          if (bFindIP) {
            break;
          }
          NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
          // ----------特定情况，可以考虑用ni.getName判断
          // 遍历所有ip
          Enumeration<InetAddress> ips = ni.getInetAddresses();
          while (ips.hasMoreElements()) {
            ip = (InetAddress) ips.nextElement();
            if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
                && ip.getHostAddress().indexOf(":") == -1) {
              bFindIP = true;
              break;
            }
          }

        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (null != ip) {
      sIP = ip.getHostAddress();
    }
    return sIP;
  }
}
