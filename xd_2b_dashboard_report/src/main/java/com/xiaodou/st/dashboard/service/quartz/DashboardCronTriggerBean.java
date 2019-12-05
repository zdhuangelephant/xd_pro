package com.xiaodou.st.dashboard.service.quartz;

import org.springframework.scheduling.quartz.CronTriggerBean;

import com.xiaodou.st.dashboard.util.StaticInfoProp;
@Deprecated
public class DashboardCronTriggerBean extends CronTriggerBean {

  /**
   * 
   */
  private static final long serialVersionUID = 1852680051948984776L;
  @SuppressWarnings("unused")
  private static String cronExpression ;


  static {
    if ("ON".equals(StaticInfoProp.quartzCronExpressionStatus())){
    cronExpression = StaticInfoProp.quartzCronExpression();
    }
  }

}
