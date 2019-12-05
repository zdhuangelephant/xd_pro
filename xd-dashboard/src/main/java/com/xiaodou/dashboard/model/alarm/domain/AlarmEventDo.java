package com.xiaodou.dashboard.model.alarm.domain;


/**
 * @name @see com.xiaodou.dashboard.engine.EventPojo.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年11月25日
 * @description 报警事件实体类
 * @version 1.0
 */
public class AlarmEventDo {

  /** alarmEventId 报警事件ID */
  private String alarmEventId;
  /** module 所属模块 */
  private String module;
  /** name 事件名称 */
  private String name;
  /** type 报警类型 1 异常报警 2 监控报警 */
  private Integer type;
  /** count 报警次数 */
  private Number count;
  /** systime 计时时间 */
  private Long systime;
  /** rate 频率 */
  private Integer rate;
  /** threshold 阈值 */
  private Integer threshold;
  /** alarmPolicy 所属报警策略 */
  private String alarmPolicyId;
  /** alarmPolicyName 所属报警策略名 */
  private String alarmPolicyName;

  public AlarmEventDo() {}

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

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Number getCount() {
    return count;
  }

  public void setCount(Double count) {
    this.count = count;
  }

  public Long getSystime() {
    return systime;
  }

  public void setSystime(Long systime) {
    this.systime = systime;
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

  public String getAlarmPolicyName() {
    return alarmPolicyName;
  }

  public void setAlarmPolicyName(String alarmPolicyName) {
    this.alarmPolicyName = alarmPolicyName;
  }

}
