package com.xiaodou.st.dashboard.service.task;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.st.dashboard.service.quartz.QuartzJob;
@Deprecated
public class QuartzListener implements ServletContextListener {

  @Resource
  QuartzJob quartzJob;

  @Override
  public void contextInitialized(ServletContextEvent event) {
    try {
      quartzJob.resetJob();
    } catch (Exception e) {
      LoggerUtil.error("服务启动，更改定时器时间失败", e);
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {}

}
