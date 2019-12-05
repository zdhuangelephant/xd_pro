package com.xiaodou.common.util.debug;

import com.xiaodou.common.util.log.LoggerUtil;


/**
 * @name @see com.xiaodou.mission.util.DebugUtil.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月28日
 * @description Debug工具类-打印耗时工具
 * @version 1.0
 */
public class PrintCostUtil {

  /**
   * 打印方法耗时
   * 
   * @param method 方法
   */
  public static void printCost(String method) {
    AnalyseWrapper.getWrapper().printCost(method);
  }

  /**
   * 标记方法组开始
   */
  public static void start() {
    AnalyseWrapper.refreshWrapper();
  }

  /**
   * @name @see com.xiaodou.mission.util.DebugUtil.java
   * @CopyRright (c) 2017 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年12月28日
   * @description 分析工具
   * @version 1.0
   */
  private static class AnalyseWrapper {

    private long previos = System.currentTimeMillis();

    public void printCost(String method) {
      long current = System.currentTimeMillis();
      long cost = current - previos;
      LoggerUtil.debug(String.format("%s cost [%s]ms", method, cost));
      previos = current;
    }

    private AnalyseWrapper() {}

    private static final ThreadLocal<AnalyseWrapper> localContext =
        new ThreadLocal<AnalyseWrapper>();

    static {
      initWrapper();
    }

    /**
     * 构造方法
     * 
     */
    public static void initWrapper() {
      AnalyseWrapper ctx = getWrapper();
      setWrapper(ctx);
    }

    /**
     * 刷新包装器
     */
    public static void refreshWrapper() {
      AnalyseWrapper ctx = new AnalyseWrapper();
      localContext.set(ctx);
    }

    /**
     * 获取包装器
     * 
     * @return ErrorsWrapper
     */
    public static AnalyseWrapper getWrapper() {
      AnalyseWrapper ctx = localContext.get();
      if (ctx == null) {
        ctx = new AnalyseWrapper();
        localContext.set(ctx);
      }
      return ctx;
    }

    /**
     * 设置包装器
     * 
     * @param wrapper
     */
    public static void setWrapper(AnalyseWrapper wrapper) {
      localContext.set(wrapper);
    }
  }

}
