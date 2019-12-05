package com.xiaodou.userCenter.util;

public class UcenterUtil {

  public static String getPasswd(String srcPasswd) {
    return MD5Util.GetMD5Code(srcPasswd);
  }

}
