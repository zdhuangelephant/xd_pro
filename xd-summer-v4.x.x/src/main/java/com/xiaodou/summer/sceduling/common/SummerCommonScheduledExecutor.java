package com.xiaodou.summer.sceduling.common;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.xiaodou.summer.sceduling.concurrent.SummerScheduledThreadPoolExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerTaskExecutor.SummerThreadFactoryBuilder;

/**
 * @name @see com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor.java 
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月6日
 * @description 默认公共Executor
 * @version 1.0
 */
public class SummerCommonScheduledExecutor {

  private static final Integer CORE_SIZE = Runtime.getRuntime().availableProcessors() * 2;

  private static SummerThreadFactoryBuilder _summerThreadFactoryBuilder =
      new SummerThreadFactoryBuilder();

  static {
    _summerThreadFactoryBuilder.setNameFormat("xd-common");
    _summerThreadFactoryBuilder.setDaemon(false);
  }

  private final static ScheduledThreadPoolExecutor _scheduExec =
      new SummerScheduledThreadPoolExecutor(CORE_SIZE, _summerThreadFactoryBuilder.build());

  public static ScheduledThreadPoolExecutor getExecutor() {
    return _scheduExec;
  }
}
