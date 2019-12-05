package com.xiaodou.autotest.core.util;

import org.apache.log4j.Logger;

import com.xiaodou.autotest.core.model.Action;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name @see com.xiaodou.autotest.core.util.ActionLoggerUtil.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年10月30日
 * @description Action日志工具类
 * @version 1.0
 */
public class ActionLoggerUtil extends LoggerUtil {

  /**
   * ActionResultLogger
   */
  public static void actionResult(Action msg) {
    debug("actionResultLogger", FastJsonUtil.toJson(msg));
  }

  private static void debug(String logger, Object msg) {
    if (null != logger && Logger.getLogger(logger).isDebugEnabled()) {
      Logger.getLogger(logger).debug(msg);
    }
  }

}
