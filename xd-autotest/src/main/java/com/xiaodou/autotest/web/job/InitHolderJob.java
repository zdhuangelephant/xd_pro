package com.xiaodou.autotest.web.job;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.xiaodou.autotest.web.service.facade.RequestServiceFacade;
import com.xiaodou.autotest.web.util.DocHolder;
import com.xiaodou.autotest.web.util.DocReqHolder;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;

public class InitHolderJob {

  @Resource
  private RequestServiceFacade requestServiceFacade;

  public void init() {
    initDocHolder();
    initDocReqHolder();
  }

  private void initDocHolder() {
    /** initialDelay 初始调度任务时机 */
    Integer initialDelay = 0;

    /** docRefreshDelay 文档缓存刷新延迟 */
    Integer docRefreshDelay = 60;
    {
      SummerCommonScheduledExecutor.getExecutor().scheduleWithFixedDelay(new SummerScheduledTask() {
        @Override
        public void doMain() {
          DocHolder.getInstance().init(requestServiceFacade);
        }

        @Override
        public void onException(Throwable t) {
          LoggerUtil.error("刷新DocMapper异常.", t);
        }
      }, initialDelay, docRefreshDelay, TimeUnit.SECONDS);
    }
  }

  private void initDocReqHolder() {
    /** initialDelay 初始调度任务时机 */
    Integer initialDelay = 0;

    /** docReqRefreshDelay 文档请求缓存刷新延迟 */
    Integer docReqRefreshDelay = 60;
    SummerCommonScheduledExecutor.getExecutor().scheduleWithFixedDelay(new SummerScheduledTask() {
      @Override
      public void doMain() {
        DocReqHolder.getInstance().init(requestServiceFacade);
      }

      @Override
      public void onException(Throwable t) {
        LoggerUtil.error("刷新DocReqMapper异常.", t);
      }
    }, initialDelay, docReqRefreshDelay, TimeUnit.SECONDS);
  }

}
