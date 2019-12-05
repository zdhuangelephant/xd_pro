package com.xiaodou.crontmonitor.schedule;

import java.text.ParseException;
import java.util.Date;

import org.quartz.CronExpression;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.crontmonitor.exception.CrontMonitorException;
import com.xiaodou.crontmonitor.exception.CrontMonitorException.ExceptionType;

/**
 * @name @see com.xiaodou.crontab.engine.schedule.SchedulerServiceImpl.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月20日
 * @description 任务调度服务Service实现
 * @version 1.0
 */
@Service("schedulerService")
public class SchedulerServiceImpl implements SchedulerService {

  private static final String NULLSTRING = null;
  private static final Date NULLDATE = null;

  @Autowired
  private Scheduler scheduler;
  @Autowired
  private JobDetail jobDetail;

  @Override
  public void schedule(String cronExpression) {
    schedule(NULLSTRING, cronExpression);
  }

  @Override
  public void schedule(String name, String cronExpression) {
    schedule(name, NULLSTRING, cronExpression);
  }

  @Override
  public void schedule(String name, String group, String cronExpression) {
    try {
      schedule(name, group, new CronExpression(cronExpression));
    } catch (ParseException e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public void schedule(CronExpression cronExpression) {
    schedule(NULLSTRING, cronExpression);
  }

  @Override
  public void schedule(String name, CronExpression cronExpression) {
    schedule(name, NULLSTRING, cronExpression);
  }

  @Override
  public void schedule(String name, String group, CronExpression cronExpression) {

    if (isValidExpression(cronExpression)) {
      if (name == null || StringUtils.EMPTY.equals(name.trim())) {
        throw new CrontMonitorException(ExceptionType.NameIsNull);
      }

      CronTriggerImpl trigger = new CronTriggerImpl();
      trigger.setCronExpression(cronExpression);

      TriggerKey triggerKey = new TriggerKey(name, group);

      trigger.setJobName(jobDetail.getKey().getName());
      trigger.setKey(triggerKey);

      try {
        scheduler.addJob(jobDetail, true);
        if (scheduler.checkExists(triggerKey)) {
          scheduler.rescheduleJob(triggerKey, trigger);
        } else {
          scheduler.scheduleJob(trigger);
        }
      } catch (SchedulerException e) {
        throw new IllegalArgumentException(e);
      }
    }
  }

  @Override
  public void schedule(Date startTime) {
    schedule(startTime, NULLDATE);
  }

  @Override
  public void schedule(Date startTime, String group) {
    schedule(startTime, NULLDATE, group);
  }

  @Override
  public void schedule(String name, Date startTime) {
    schedule(name, startTime, NULLDATE);
  }

  @Override
  public void schedule(String name, Date startTime, String group) {
    schedule(name, startTime, NULLDATE, group);
  }

  @Override
  public void schedule(Date startTime, Date endTime) {
    schedule(startTime, endTime, 0);
  }

  @Override
  public void schedule(Date startTime, Date endTime, String group) {
    schedule(startTime, endTime, 0, group);
  }

  @Override
  public void schedule(String name, Date startTime, Date endTime) {
    schedule(name, startTime, endTime, 0);
  }

  @Override
  public void schedule(String name, Date startTime, Date endTime, String group) {
    schedule(name, startTime, endTime, 0, group);
  }

  @Override
  public void schedule(Date startTime, int repeatCount) {
    schedule(null, startTime, NULLDATE, 0);
  }

  @Override
  public void schedule(Date startTime, Date endTime, int repeatCount) {
    schedule(null, startTime, endTime, 0);
  }

  @Override
  public void schedule(Date startTime, Date endTime, int repeatCount, String group) {
    schedule(null, startTime, endTime, 0, group);
  }

  @Override
  public void schedule(String name, Date startTime, Date endTime, int repeatCount) {
    schedule(name, startTime, endTime, 0, 0L);
  }

  @Override
  public void schedule(String name, Date startTime, Date endTime, int repeatCount, String group) {
    schedule(name, startTime, endTime, 0, 0L, group);
  }

  @Override
  public void schedule(Date startTime, int repeatCount, long repeatInterval) {
    schedule(null, startTime, NULLDATE, repeatCount, repeatInterval);
  }

  @Override
  public void schedule(Date startTime, Date endTime, int repeatCount, long repeatInterval) {
    schedule(null, startTime, endTime, repeatCount, repeatInterval);
  }

  @Override
  public void schedule(Date startTime, Date endTime, int repeatCount, long repeatInterval,
      String group) {
    schedule(null, startTime, endTime, repeatCount, repeatInterval, group);
  }

  @Override
  public void schedule(String name, Date startTime, Date endTime, int repeatCount,
      long repeatInterval) {
    schedule(name, startTime, endTime, repeatCount, repeatInterval, NULLSTRING);
  }

  @Override
  public void schedule(String name, Date startTime, Date endTime, int repeatCount,
      long repeatInterval, String group) {

    if (this.isValidExpression(startTime)) {

      if (name == null || StringUtils.EMPTY.equals(name.trim())) {
        throw new CrontMonitorException(ExceptionType.NameIsNull);
      }

      TriggerKey triggerKey = new TriggerKey(name, group);

      SimpleTriggerImpl trigger = new SimpleTriggerImpl();
      trigger.setKey(triggerKey);
      trigger.setJobName(jobDetail.getKey().getName());

      trigger.setStartTime(startTime);
      trigger.setEndTime(endTime);
      trigger.setRepeatCount(repeatCount);
      trigger.setRepeatInterval(repeatInterval);

      try {
        scheduler.addJob(jobDetail, true);
        if (scheduler.checkExists(triggerKey)) {
          scheduler.rescheduleJob(triggerKey, trigger);
        } else {
          scheduler.scheduleJob(trigger);
        }
      } catch (SchedulerException e) {
        throw new IllegalArgumentException(e);
      }
    }
  }

  @Override
  public void pauseTrigger(String triggerName) {
    pauseTrigger(triggerName, NULLSTRING);
  }

  @Override
  public void pauseTrigger(String triggerName, String group) {
    try {
      scheduler.pauseTrigger(new TriggerKey(triggerName, group));
    } catch (SchedulerException e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public void resumeTrigger(String triggerName) {
    resumeTrigger(triggerName, NULLSTRING);
  }

  @Override
  public void resumeTrigger(String triggerName, String group) {
    try {
      scheduler.resumeTrigger(new TriggerKey(triggerName, group));
    } catch (SchedulerException e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public boolean removeTrigdger(String triggerName) {
    return removeTrigdger(triggerName, NULLSTRING);
  }

  @Override
  public boolean removeTrigdger(String triggerName, String group) {
    TriggerKey triggerKey = new TriggerKey(triggerName, group);
    try {
      scheduler.pauseTrigger(triggerKey);
      return scheduler.unscheduleJob(triggerKey);
    } catch (SchedulerException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private boolean isValidExpression(final CronExpression cronExpression) {

    CronTriggerImpl trigger = new CronTriggerImpl();
    trigger.setCronExpression(cronExpression);

    Date date = trigger.computeFirstFireTime(null);

    return date != null && date.after(new Date());
  }

  private boolean isValidExpression(final Date startTime) {

    SimpleTriggerImpl trigger = new SimpleTriggerImpl();
    trigger.setStartTime(startTime);

    Date date = trigger.computeFirstFireTime(null);

    return date != null && date.after(new Date());
  }
}
