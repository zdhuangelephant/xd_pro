package com.xiaodou.course.util;

import com.xiaodou.common.util.RandomUtil;

public class IDHelper {

  public static Long getId() {
    return Long.parseLong((System.currentTimeMillis() + RandomUtil.randomNumber(6)).trim());
  }

}
