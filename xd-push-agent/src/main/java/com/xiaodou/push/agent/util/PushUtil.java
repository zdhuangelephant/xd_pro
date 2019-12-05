package com.xiaodou.push.agent.util;

import java.util.Map;

/**
 * @name @see com.xiaodou.push.agent.util.PushUtil.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年10月26日
 * @description 推送工具类
 * @version 1.0
 */
public class PushUtil {

  /**
   * 设置retcode
   * 
   * @param map
   * @param code
   */
  public static void setRetCode(Map<String, String> map, String code) {
    map.put("retcode", code);
  }

  /**
   * 设置retdesc
   * 
   * @param map
   * @param desc
   */
  public static void setRetDesc(Map<String, String> map, String desc) {
    map.put("retdesc", desc);
  }

}
