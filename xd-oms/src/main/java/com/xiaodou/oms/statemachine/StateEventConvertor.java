package com.xiaodou.oms.statemachine;

import java.lang.reflect.Field;

/**
 * @Title:StateEventConvertor.java
 * @Description:根据状态值和event名字得到相应的枚举值
 * @author zhaoyang
 * @date June 18, 2014 8:33:35 AM
 * @version V1.0
 */
public class StateEventConvertor {
  /**
   * 根据状态值得到相应的枚举值
   * 
   * @param orderStatus
   * @param stateInstance
   * @return
   * @throws Exception
   */
  public static Enum getOrderState(Integer orderStatus, String stateInstance) throws Exception {
    Class clazz = Class.forName(stateInstance);
    Field[] fields = clazz.getFields();
    for (int i = 0; i < fields.length; i++) {
      Enum e = Enum.valueOf(clazz, fields[i].getName());
      if (String.valueOf(orderStatus).equals(e.toString())) {
        return e;
      }
    }
    return null;
  }

  /**
   * 根据event名字得到相应的枚举值
   * 
   * @param eventString
   * @param eventInstance
   * @return
   * @throws Exception
   */
  public static Enum getOrderEvent(String eventString, String eventInstance) throws Exception {
    Class clazz = Class.forName(eventInstance);
    Field[] fields = clazz.getFields();
    for (int i = 0; i < fields.length; i++) {
      Enum e = Enum.valueOf(clazz, fields[i].getName());
      if (eventString.equals(fields[i].getName())) {
        return e;
      }
    }
    return null;
  }

}
