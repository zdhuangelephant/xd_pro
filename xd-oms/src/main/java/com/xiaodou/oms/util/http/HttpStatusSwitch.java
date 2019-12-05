package com.xiaodou.oms.util.http;

import java.util.Map;

import com.google.common.collect.Maps;

public class HttpStatusSwitch {

  private static final Map<Integer, String> status = Maps.newHashMap();


  static {
    status.put(0, "初始状态");
    status.put(200, "正常");
    status.put(601, "HTTP连接异常");
    status.put(604, "IO读超时");
    status.put(605, "其它错误");
  }

  public static String getStatus(Integer code) {
    return null == status.get(code) ? "未定义状态码" : status.get(code);
  }
}
