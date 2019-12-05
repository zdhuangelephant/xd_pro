package com.xiaodou.st.dashboard.constants.enums;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

public enum AlarmTypeEnum {

   AlarmType_1("1","同一账号更换3台设备登陆",AlarmLevelEnum.AlarmLevel_1,PretreatmentEnum.AccountNoPretreat),
   AlarmType_2("2","同一设备登陆2个不同账号",AlarmLevelEnum.AlarmLevel_1,PretreatmentEnum.DeviceNoPretreat),
   AlarmType_3("3","同一账号7日内更换3台及以上设备登陆",AlarmLevelEnum.AlarmLevel_2,PretreatmentEnum.AccountNoPretreat),
   AlarmType_4("4","同一账号7日内在三个不同地区登陆",AlarmLevelEnum.AlarmLevel_2,PretreatmentEnum.AccountNoPretreat),
   AlarmType_5("5","同一设备7日内切换账号登陆2次以上A-B-A-B",AlarmLevelEnum.AlarmLevel_2,PretreatmentEnum.AccountDeviceClosure),
   AlarmType_6("6","同一账号24小时内更换3台及以上设备登陆",AlarmLevelEnum.AlarmLevel_3,PretreatmentEnum.AccountClosure),
   AlarmType_7("7","同一账号24小时内在三个不同地区登陆",AlarmLevelEnum.AlarmLevel_3,PretreatmentEnum.AccountDeviceClosure),
   AlarmType_8("8","同一设备24小时内切换账号登陆2次以上A-B-A-B",AlarmLevelEnum.AlarmLevel_3,PretreatmentEnum.AccountDeviceClosure),
   AlarmType_9("9","同一设备登陆3个及以上不同账号",AlarmLevelEnum.AlarmLevel_3,PretreatmentEnum.AccountDeviceClosure),
   AlarmType_10("10","答题监控人像对比低于阀值",AlarmLevelEnum.AlarmLevel_3,PretreatmentEnum.AccountClosure),

   /*****************云测评更新预警类型******************/
  AlarmType_11("11", "近7日无学习记录", AlarmLevelEnum.AlarmLevel_1, PretreatmentEnum.ResponseNothing),
  AlarmType_12("12", "近14日无学习记录",AlarmLevelEnum.AlarmLevel_2,PretreatmentEnum.ResponseNothing),
  AlarmType_13("13", "近21日无学习记录",AlarmLevelEnum.AlarmLevel_3,PretreatmentEnum.ResponseNothing),
  /** AlarmType_14 同一账号近7日内登陆3个及以上设备*/
  AlarmType_14("14", "同一账号近7日内登陆3个及以上设备",AlarmLevelEnum.AlarmLevel_1,PretreatmentEnum.ResponseNothing),
  /** AlarmType_15 同一账号近1日内登陆3个及以上设备*/
  AlarmType_15("15", "同一账号近1日内登陆3个及以上设备",AlarmLevelEnum.AlarmLevel_3,PretreatmentEnum.ResponseNothing),
  /** AlarmType_16 同一设备近7日内登陆3个及以上账号*/
  AlarmType_16("16", "同一设备近7日内登陆3个及以上账号",AlarmLevelEnum.AlarmLevel_2,PretreatmentEnum.ResponseNothing),
  /** AlarmType_17 同一设备近1日内登陆3个及以上账号*/
  AlarmType_17("17", "同一设备近1日内登陆3个及以上账号",AlarmLevelEnum.AlarmLevel_3,PretreatmentEnum.ResponseNothing);
  
  private String code;
  private String desc;
  private AlarmLevelEnum alarmLevelEnum;
  private PretreatmentEnum pretreatmentEnum;

  private AlarmTypeEnum(String code, String desc, AlarmLevelEnum alarmLevelEnum,
      PretreatmentEnum pretreatmentEnum) {
    this.code = code;
    this.desc = desc;
    this.alarmLevelEnum = alarmLevelEnum;
    this.pretreatmentEnum = pretreatmentEnum;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public AlarmLevelEnum getAlarmLevelEnum() {
    return alarmLevelEnum;
  }

  public void setAlarmLevelEnum(AlarmLevelEnum alarmLevelEnum) {
    this.alarmLevelEnum = alarmLevelEnum;
  }

  public PretreatmentEnum getPretreatmentEnum() {
    return pretreatmentEnum;
  }

  public void setPretreatmentEnum(PretreatmentEnum pretreatmentEnum) {
    this.pretreatmentEnum = pretreatmentEnum;
  }
  
  public static List<AlarmTypeEnum> getAddAlermTypeEnum() {
    ArrayList<AlarmTypeEnum> addAlermTypes = Lists.newArrayList();
    for (AlarmTypeEnum alermType : AlarmTypeEnum.values()) {
      if(Integer.parseInt(alermType.getCode()) > 10) {
        addAlermTypes.add(alermType);
      }
    }
    
    return addAlermTypes;
  }

}
