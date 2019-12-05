package com.xiaodou.dashboard.util.log;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;

/**
 * @name @see com.xiaodou.dashboard.util.log.SyncJmsgEntity.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年2月23日
 * @description 同步JMSG日志模型
 * @version 1.0
 */
public class SyncJmsgEntity {
  enum SyncJmsgDesc {
    Init, AllSuccess, Alarm
  }

  /** desc 描述 */
  private String desc = SyncJmsgDesc.Init.name();
  private List<String> customTagList;
  private List<String> successTagList;
  private Set<String> alarmTagSet;

  public boolean isEmpty() {
    return isEmpty(customTagList) && isEmpty(successTagList) && isEmpty(alarmTagSet);
  }

  private boolean isEmpty(Collection<?> tagList) {
    return null == tagList || tagList.isEmpty();
  }

  public String getDesc() {
    return desc;
  }

  public List<String> getCustomTagList() {
    return customTagList;
  }

  public void setCustomTagList(List<String> customTagList) {
    this.customTagList = customTagList;
  }

  public List<String> getSuccessTagList() {
    return successTagList;
  }

  public void setSuccessTagList(List<String> successTagList) {
    this.successTagList = successTagList;
  }

  public Set<String> getAlarmTagSet() {
    return alarmTagSet;
  }

  public void setAlarmTagSet(Set<String> alarmTagSet) {
    this.alarmTagSet = alarmTagSet;
  }

  public void setDesc() {
    if (null != this.customTagList && !this.customTagList.isEmpty() && null != this.successTagList
        && !this.successTagList.isEmpty()
        && this.customTagList.size() == this.successTagList.size()) {
      this.desc = SyncJmsgDesc.AllSuccess.name();
    } else if (null != this.alarmTagSet && !this.alarmTagSet.isEmpty()) {
      this.desc = SyncJmsgDesc.Alarm.name();
    }
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
