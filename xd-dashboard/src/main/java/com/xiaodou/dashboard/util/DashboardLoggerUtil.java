package com.xiaodou.dashboard.util;

import org.apache.log4j.Logger;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.dashboard.util.log.SyncJmsgEntity;

/**
 * @name @see com.xiaodou.dashboard.util.DashboardLoggerUtil.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年2月23日
 * @description Dashboard自有LoggerUtil
 * @version 1.0
 */
public class DashboardLoggerUtil extends LoggerUtil {

  /**
   * 记录外部系统调用本系统接口的Logger
   */
  public static void syncJmsg(SyncJmsgEntity msg) {
    if (Logger.getLogger("syncJmsgLogger").isInfoEnabled()) {
      Logger.getLogger("syncJmsgLogger").info(msg);
    }
  }

}
