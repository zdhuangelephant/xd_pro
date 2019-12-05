package com.xiaodou.course.util;

import java.util.TreeMap;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.StringUtils;

/**
 * @name SignUtil CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月17日
 * @description 打卡util类
 * @version 1.0
 */
public class SignUtil {
  private static TreeMap<Integer, Integer> stepMap = Maps.newTreeMap();
  static {
    INIT: {
      String stepStrand = ConfigProp.getParams("signcard.step");
      if (StringUtils.isBlank(stepStrand)) break INIT;
      for (String step : stepStrand.split(",")) {
        if (StringUtils.isBlank(step)) continue;
        Integer istep = Integer.parseInt(step);
        stepMap.put(istep, istep);
      }
    }
  }

  /**
   * 根据连续打卡天数获取打卡页描述
   * 
   * @param continDay 连续打卡天数
   * @return 打卡页描述
   */
  public static String getDesc(Integer continDay) {
    if (null == stepMap || stepMap.size() == 0) return null;
    Integer step = stepMap.higherKey(continDay);
    if (null == step) step = stepMap.lastKey();
    return ConfigProp.getParams(String.format("signcard.desc.%s", step));
  }

  /**
   * 根据连续打卡天数获取增加积分数量
   * 
   * @param continDay 连续打卡天数
   * @return 增加积分数量
   */
  public static Integer getAddCredit(Integer continDay) {
    if (null == stepMap || stepMap.size() == 0) return null;
    Integer step = stepMap.higherKey(continDay);
    if (null == step) step = stepMap.lastKey();
    return Integer.parseInt(ConfigProp.getParams(String.format("signcard.credit.%s", step)));
  }
}
