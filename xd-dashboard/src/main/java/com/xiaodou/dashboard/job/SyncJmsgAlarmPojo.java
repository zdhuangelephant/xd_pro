package com.xiaodou.dashboard.job;

import java.util.Set;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dashboard.request.AlarmRequestPojo;

/**
 * @name @see com.xiaodou.dashboard.job.SyncJmsgAlarmPojo.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年2月23日
 * @description 同步jmsg请求报警参数类
 * @version 1.0
 */
public class SyncJmsgAlarmPojo extends AlarmRequestPojo {

  private static final String SYNC_JMSG_MODULE = "xd-dashboard";
  private static final String SYNC_JMSG_EVENT = "sync-jmsg-alarm";
  private static final String SYNC_JMSG_MESSAGE = "异步消息消费存在异常,请检查.";
  private static final String SYNC_JMSG_MAIL = "异步消息消费存在异常,请检查.[TagList:%s]";

  public SyncJmsgAlarmPojo() {
    this(null);
  }

  public SyncJmsgAlarmPojo(Set<String> alarmTagSet) {
    setEventModule(SYNC_JMSG_MODULE);
    setEventName(SYNC_JMSG_EVENT);
    setMessageInfo(SYNC_JMSG_MESSAGE);
    setAlarmTagSet(alarmTagSet);
  }

  /**
   * 设置报警TagList
   * @param alarmTagSet 报警tagList
   */
  public void setAlarmTagSet(Set<String> alarmTagSet) {
    if (null != alarmTagSet && !alarmTagSet.isEmpty()) {
      setMailInfo(String.format(SYNC_JMSG_MAIL, FastJsonUtil.toJson(alarmTagSet)));
    }
  }

}
