package com.xiaodou.st.dashboard.service.quartz;

import java.text.ParseException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.CronTriggerBean;

import com.xiaodou.st.dashboard.service.http.HttpService;
import com.xiaodou.st.dashboard.util.StaticInfoProp;

public class QuartzJob {

  @Resource
  HttpService httpService;

  private Scheduler scheduler;

  public Scheduler getScheduler() {
    return scheduler;
  }

  public void setScheduler(Scheduler scheduler) {
    this.scheduler = scheduler;
  }

  // 定时任务执行方法
  public void work() {
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // Date date = new Date();
    // System.out.println(sdf.format(date) + "  执行Quartz定时器");
    httpService.quartzStudent();
    httpService.quartzApply();
  }

  // 修改定时任务时间
  @PostConstruct
  public void resetJob() throws SchedulerException, ParseException {
    String cronExpression = null;
    if ("ON".equals(StaticInfoProp.quartzCronExpressionStatus())) {
      cronExpression = StaticInfoProp.quartzCronExpression();
    } else
      return;
    CronTriggerBean trigger =
        (CronTriggerBean) scheduler.getTrigger("doTime", Scheduler.DEFAULT_GROUP);
    String originConExpression = trigger.getCronExpression();
    if (!originConExpression.equalsIgnoreCase(cronExpression)) {
      // cronExpression = "0 26 13 * * ?";
      trigger.setCronExpression(cronExpression);
      scheduler.rescheduleJob("doTime", Scheduler.DEFAULT_GROUP, trigger);
    }
    scheduler.resumeTrigger("doTime", Scheduler.DEFAULT_GROUP);
  }

}
