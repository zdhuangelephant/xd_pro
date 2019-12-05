package com.xiaodou.sms.utils;

import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.xiaodou.common.util.log.LoggerUtil;

/**
 * @name @see com.xiaodou.sms.utils.VelocityUtil.java
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月31日
 * @description VM模板解析工具类
 * @version 1.0
 */
public class VelocityUtil {

  private static final VelocityEngine ve = new VelocityEngine();
  static {
    try {
      ve.init();
    } catch (Exception e) {
      LoggerUtil.error("初始化VM解析引擎失败.", e);
    }
  }

  /**
   * 解析填充模板内容方法
   * 
   * @param instring 待填充信息
   * @param context VM模板
   * @return
   * @throws Exception
   */
  public static String transform(String instring, VelocityContext context) throws Exception {
    if (null == context) return instring;
    StringWriter out = new StringWriter();
    ve.evaluate(context, out, null, instring);
    return out.toString();
  }

}
