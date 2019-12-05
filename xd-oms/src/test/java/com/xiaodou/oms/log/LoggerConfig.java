package com.xiaodou.oms.log;

import org.apache.log4j.xml.DOMConfigurator;

public class LoggerConfig {
  private static boolean isInited = false;

  public synchronized static void init() {
    if (!isInited) {


      DOMConfigurator.configure("src/test/resources/conf/core/log4j.xml");
    }
  }
}
