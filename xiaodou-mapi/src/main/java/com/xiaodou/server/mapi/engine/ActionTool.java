package com.xiaodou.server.mapi.engine;

import com.xiaodou.common.util.StringUtils;

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
    if (StringUtils.isBlank(name)) return null;
    return (T) Enum.valueOf(clazz, name);
  }

  /**
   * 验证属性值不空
   */
  public static String validateNotBlank(String value, String errCode, String name) {
    if (StringUtils.isBlank(value))
      throw new RuntimeException(ActionMessage.getErrMessage(errCode, name));
    return value;
  }

}
