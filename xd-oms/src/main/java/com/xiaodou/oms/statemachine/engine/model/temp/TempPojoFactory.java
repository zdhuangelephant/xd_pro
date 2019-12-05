package com.xiaodou.oms.statemachine.engine.model.temp;

import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.oms.statemachine.Context;

/**
 * <p>
 * TempPojo工厂
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月18日
 */
public class TempPojoFactory {

  private static Map<String, Class<? extends IBaseTempPojo>> tempMap = Maps.newHashMap();

  static {
    TempPojo[] temps = TempPojo.values();
    for (TempPojo temp : temps) {
      tempMap.put(temp.getName(), temp.getPojo());
    }
  }

  /**
   * 构建Pojo方法
   * @param name
   * @param context
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  public static IBaseTempPojo buildTempPojo(String name, Context context)
      throws InstantiationException, IllegalAccessException {
    Class<? extends IBaseTempPojo> pojoClass = tempMap.get(name);
    IBaseTempPojo pojo = pojoClass.newInstance();
    return pojo.initPojo(context);
  }

}
