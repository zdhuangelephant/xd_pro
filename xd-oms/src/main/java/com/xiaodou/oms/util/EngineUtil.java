package com.xiaodou.oms.util;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.oms.exception.ExceptionMessageProp;

/**
 * <p>解析引擎用Util方法类</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月30日
 */
public class EngineUtil {
  /**
   * 验证属性值不空
   */
  public static String validateNotBlank(String value, String errCode, String name) {
    if (StringUtils.isBlank(value))
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(errCode, name));
    return value;
  }
}
