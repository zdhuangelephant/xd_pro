package com.xiaodou.jmsg.server.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.xiaodou.common.util.log.LoggerUtil;

public class HostHelper {

  public static String getHostName() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      LoggerUtil.error("getHostName error: ", e);
    }
    return "";
  }

  public static String getHostAddress() {
    try {
      return InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      LoggerUtil.error("getHostAddress error: ", e);
    }
    return "";
  }

}
