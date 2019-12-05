package com.xiaodou.common.test.http;

import com.xiaodou.common.util.log.DashBoardAlarm;
import com.xiaodou.common.util.log.model.AlarmEntityImpl;
import com.xiaodou.common.util.log.model.IAlarmEntity;

public class DashboardAlarmTest {

  public static void main(String[] args) {
    DashBoardAlarm.alarm(new AlarmEntityImpl() {
      
      @Override
      public String getMessageInfo() {
        return "出题接口异常";
      }
      
      @Override
      public String getMailInfo() {
        return "出题接口异常";
      }
      
      @Override
      public IAlarmEntity getLoggerEntity() {
        return null;
      }
      
      @Override
      public String getEventName() {
        return "pk-fail";
      }
      
      @Override
      public String getEventModule() {
        return "xd-quesbk";
      }
    });
  }
}
