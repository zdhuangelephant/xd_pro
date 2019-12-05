package com.xiaodou.dashboard.vo.alarm.request;

import com.xiaodou.dashboard.model.alarm.domain.AlarmEventDo;

import lombok.Data;

/**
 * @name @see com.xiaodou.dashboard.vo.alarm.request.AlarmEventRequest.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年6月12日
 * @description 报警事件请求
 * @version 1.0
 */
@Data
public class AlarmEventRequest {
  /** alarmEventId 报警事件ID */
  private String alarmEventId;
  /** module 所属模块 */
  private String module;
  /** name 事件名称 */
  private String name;
  /** rate 频率 */
  private Integer rate;
  /** threshold 阈值 */
  private Integer threshold;
  /** alarmPolicy 所属报警策略 */
  private String alarmPolicyId;

  public AlarmEventDo getEventDo() {
    AlarmEventDo event = new AlarmEventDo();
    event.setAlarmEventId(alarmEventId);
    event.setModule(module);
    event.setName(name);
    event.setRate(rate);
    event.setThreshold(threshold);
    event.setAlarmPolicyId(alarmPolicyId);
    return event;
  }

  public String getAlarmEventId() {
    return alarmEventId;
  }

  public void setAlarmEventId(String alarmEventId) {
    this.alarmEventId = alarmEventId;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getRate() {
    return rate;
  }

  public void setRate(Integer rate) {
    this.rate = rate;
  }

  public Integer getThreshold() {
    return threshold;
  }

  public void setThreshold(Integer threshold) {
    this.threshold = threshold;
  }

  public String getAlarmPolicyId() {
    return alarmPolicyId;
  }

  public void setAlarmPolicyId(String alarmPolicyId) {
    this.alarmPolicyId = alarmPolicyId;
  }

}
