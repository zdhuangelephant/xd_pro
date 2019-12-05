package com.xiaodou.userCenter.util;

import com.xiaodou.common.util.StringUtils;

public class SaltUtil {

  private static final String SALT_TEMP = "**********%s";

  public static String saltInfo(String info) {
    if (StringUtils.isBlank(info) || info.length() < 5)
      return String.format(SALT_TEMP, StringUtils.EMPTY);
    return String.format(SALT_TEMP, info.substring(info.length() - 4));
  }
}
