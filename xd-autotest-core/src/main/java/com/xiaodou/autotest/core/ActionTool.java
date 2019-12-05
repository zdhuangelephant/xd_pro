package com.xiaodou.autotest.core;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;
import com.xiaodou.autotest.core.vo.SandBoxContext;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * @name @see com.xiaodou.server.mapi.engine.ActionTool.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description 注册Action用工具类
 * @version 1.0
 */
public class ActionTool {

  /**
   * 获取存储Key
   * 
   * @param mpackage
   * @param name
   * @return
   */
  public static String getKey(String mpackage, String name) {
    return String.format("{package:%s, name:%s}", mpackage, name);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <T extends Enum> T getEnumValue(Class<T> clazz, String name) {
    if (StringUtils.isBlank(name)) {
      return null;
    }
    return (T) Enum.valueOf(clazz, name);
  }

  /**
   * 传入一个如coreParams.gorderUpdateInfo.gpayAmount和context对象
   * 则会获取context.getCoreParams().getGorderUpdateInfo().getGpayAmount() 同时也支持获取Shares
   * Map里的值,如传入("shares.aaa",context)context.getShares().get("aaa")
   * 另外对大小写做了处理，传入coreparams.gorderupdateinfo也可以，但取shares里的东西必须大小写正确
   * 
   * @param paramName 参数路径名如coreParams.payParam.amount
   * @param context context对象
   * @return context的相应属性
   */
  public static Object getParams(String paramName, @SuppressWarnings("rawtypes") Map context) {
    if (StringUtils.isBlank(paramName)) {
      return null;
    }
    String[] params = paramName.split("\\.");
    Queue<String> queue = new LinkedList<>();
    queue.addAll(Arrays.asList(params));
    return getParam(queue, context);
  }

  /**
   * 递归获取对象的属性
   * 
   * @param queue paramName按照.分割后的queue
   * @param obj 任意对象
   * @return 对象的属性
   */
  private static Object getParam(Queue<String> queue, Object obj) {
    String param = queue.poll();
    Object next = getFieldParam(obj, param);
    if (queue.size() == 0 || next == null) {
      return next;
    }
    return getParam(queue, next);
  }

  /**
   * 获取对象的属性 如果对象是一个Map对象，则map.get(fieldName)
   * 如果对象是一个普通对象，则把对象所有field转成小写和传入fieldName转小写比较，返回field.get(obj)
   * 
   * @param obj 任意对象，支持map
   * @param fieldName 对象属性
   * @return
   */
  @SuppressWarnings("rawtypes")
  private static Object getFieldParam(Object obj, String fieldName) {
    if (obj == null || StringUtils.isBlank(fieldName)) {
      return null;
    }
    // 判断obj是否是一个Map对象
    if (Map.class.isAssignableFrom(obj.getClass())) {
      return ((Map) obj).get(fieldName);
    }

    for (Field field : obj.getClass().getDeclaredFields()) {
      try {
        if (fieldName.toLowerCase().equals(field.getName().toLowerCase())) {
          field.setAccessible(true);
          return field.get(obj);
        }
      } catch (Exception e) {
        LoggerUtil.error("LocalApiBase.getParam", e);
      }
    }
    return null;
  }

  /**
   * 判断是否包含引用属性${}
   */
  public static boolean checkRegex(String value) {
    if (StringUtils.isBlank(value)) {
      return false;
    }
    String[] values = value.split(ActionConstant.REGEX);
    if (values.length == 1) {
      return false;
    }
    for (int i = 0; i < values.length; i++) {
      String subValue = values[i];
      int cutPoint = subValue.indexOf("}");
      if (cutPoint > 0) {
        return true;
      }
    }
    return false;
  }


  /**
   * 读取引用属性值${}
   */
  public static Object getValue(SandBoxContext sandBox, String value) {
    if (StringUtils.isBlank(value)) {
      return value;
    }
    String[] values = value.split(ActionConstant.REGEX);
    if (values.length == 1) {
      return value;
    }
    for (int i = 0; i < values.length; i++) {
      String subValue = values[i];
      int cutPoint = subValue.indexOf("}");
      if (cutPoint > 0) {
        String regex = subValue.substring(0, cutPoint);
        values[i] =
            getProperty(sandBox, regex) + subValue.substring(cutPoint + 1, subValue.length());
      }
    }
    return StringUtils.join(values);
  }


  public static void main(String[] args) {
    String name = "zhaodan";
    String value1 = "{\"name\":\"${value}\",\"age\":\"12\"}";
    String value2 = "\"${value}\"";
    String value3 = "${value}";
    String value4 = "{\"name\":\"${value}\",\"age\":\"${value}\"}";
    System.out.println(getValue(name, value1));
    System.out.println(getValue(name, value2));
    System.out.println(getValue(name, value3));
    System.out.println(getValue(name, value4));
  }


  public static Object getValue(String target, String value) {
    if (StringUtils.isBlank(value)) {
      return value;
    }
    String regex = "\\$\\{";
    String[] values = value.split(regex);
    if (values.length == 1) {
      return value;
    }
    for (int i = 0; i < values.length; i++) {
      String subValue = values[i];
      int cutPoint = subValue.indexOf("}");
      if (cutPoint > 0) {
        String matcher = subValue.substring(0, cutPoint);
        values[i] = matcher + subValue.substring(cutPoint + 1, subValue.length());
      }
    }
    return StringUtils.join(values);
  }

  private static Object getProperty(SandBoxContext sandBox, String regex) {
    return sandBox.getParam(regex);
  }

  public static List<String> getAutoRegistKey(String key) {
    List<String> keyList = Lists.newArrayList();
    Matcher matcher = ActionConstant.AUTO_REGIST_PATTERN.matcher(key);
    while (matcher.find()) {
      keyList.add(matcher.group());
    }
    return keyList;
  }

  private static boolean isMatch(String regex, String orginal) {
    if (orginal == null || orginal.trim().equals("")) {
      return false;
    }
    Pattern pattern = Pattern.compile(regex);
    Matcher isNum = pattern.matcher(orginal);
    return isNum.matches();
  }

  public static boolean isPositiveInteger(String orginal) {
    return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
  }

  public static boolean isNegativeInteger(String orginal) {
    return isMatch("^-[1-9]\\d*", orginal);
  }

  public static boolean isWholeNumber(String orginal) {
    return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal)
        || isNegativeInteger(orginal);
  }

  public static boolean isPositiveDecimal(String orginal) {
    return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
  }

  public static boolean isNegativeDecimal(String orginal) {
    return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
  }

  public static boolean isDecimal(String orginal) {
    return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
  }

  public static boolean isRealNumber(String orginal) {
    return isWholeNumber(orginal) || isDecimal(orginal);
  }

}
