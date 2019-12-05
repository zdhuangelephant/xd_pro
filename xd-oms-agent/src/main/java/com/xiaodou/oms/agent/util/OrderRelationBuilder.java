package com.xiaodou.oms.agent.util;

import java.util.Map;

/**
 * Created by zyp on 14-6-24.
 */
public class OrderRelationBuilder {

  /**
   * 构建关联关系
   * 
   * @param relations
   * @return
   */
  public static String buildRealtion(Map<String, String> relations) {
    StringBuilder relationString = new StringBuilder();
    for (String key : relations.keySet()) {
      String gx = key + ":" + relations.get(key) + "||";
      relationString.append(gx);
    }
    return relationString.toString();
  }
}
