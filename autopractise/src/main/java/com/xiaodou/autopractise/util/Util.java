package com.xiaodou.autopractise.util;

import com.xiaodou.common.util.StringUtils;

public class Util {

  public static String fetchValue(String[] values) {
    StringBuilder _val = new StringBuilder(200);
    if (null != values && values.length > 0) {
      for (String value : values) {
        if (StringUtils.isBlank(value)) {
          continue;
        }
        _val.append(value).append(",");
      }
    }
    return _val.length() == 0 ? StringUtils.EMPTY : _val.substring(0, _val.length() - 1);
  }
}
