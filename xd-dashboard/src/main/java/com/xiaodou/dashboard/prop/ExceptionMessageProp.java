package com.xiaodou.dashboard.prop;

import com.xiaodou.common.util.FileUtil;


/**
 * @name @see com.xiaodou.dashboard.prop.ExceptionMessageProp.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年11月24日
 * @description 异常描述信息
 * @version 1.0
 */
public class ExceptionMessageProp {
  /**
   * 配置文件
   */
  private static FileUtil errFile = FileUtil
      .getInstance("/conf/custom/notenv/error_info.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (errFile == null)
      synchronized (ExceptionMessageProp.class) {
        if (errFile == null)
          errFile = FileUtil.getInstance("/conf/custom/notenv/error_info.properties");
      }
    return errFile;
  }

  private static String PARAM = "#param#";

  /**
   * 获取参数值
   * 
   * @param code 参数key
   * @param params 待替换信息
   * @return 参数值
   */
  public static String getErrMessage(String code, String... params) {
    String message = getProperty().getProperties(code);
    if (null == params || params.length == 0) return message;
    if (params.length == 1) return message.replaceAll(PARAM, params[0]);
    for (String param : params) {
      message = message.replaceFirst(PARAM, param);
    }
    return message;
  }
}
