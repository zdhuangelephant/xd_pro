package com.xiaodou.im.agent.qq.util;

import java.util.Map;

/**
 * Date: 2014/12/11
 * Time: 14:20
 *
 * @author Tian.Dong
 */
public class Map2ParamsUtil {
  public String toParams(Map<String, String> map) {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, String> entry : map.entrySet()) {
      sb.append(entry.getKey());
      sb.append("=");
      sb.append(entry.getValue());
      sb.append("&");
    }
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }
}
