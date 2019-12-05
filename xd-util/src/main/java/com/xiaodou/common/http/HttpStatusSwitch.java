package com.xiaodou.common.http;

import java.util.Map;

import com.google.common.collect.Maps;

public class HttpStatusSwitch {

  private static final Map<Integer, HttpResultStatus> statusMap = Maps.newHashMap();


  static {
    for(HttpResultStatus status : HttpResultStatus.values())
      statusMap.put(status.getCode(), status);
  }

  public static HttpResultStatus getStatus(Integer code) {
    return (null == statusMap.get(code)) ? (HttpResultStatus) HttpResultStatus.UNDEFINE : statusMap.get(code);
  }
}
