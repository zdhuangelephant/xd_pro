package com.xiaodou.crontab.runable;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.xiaodou.crontab.engine.excutor.CrontExcutor;

/**
 * @name @see com.xiaodou.crontab.runable.TaskExcution.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月20日
 * @description Quartz任务调度单元
 * @version 1.0
 */
public class TaskExcution extends QuartzJobBean {

  @Override
  protected void executeInternal(JobExecutionContext jobexecutioncontext)
      throws JobExecutionException {
    Trigger trigger = jobexecutioncontext.getTrigger();
    String configId = trigger.getKey().getName();
    CrontExcutor.voteJob(configId);
  }

}
