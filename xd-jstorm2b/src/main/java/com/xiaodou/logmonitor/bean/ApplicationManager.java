package com.xiaodou.logmonitor.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @name @see com.xiaodou.bean.ApplicationManager.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月18日
 * @description 上下文管理器
 * @version 1.0
 */
public class ApplicationManager {

  /** ctx 上下文单例 */
  private static ApplicationContext ctx = new ClassPathXmlApplicationContext(
      "conf/core/xd-jstorm2b.xml");

  /**
   * 获取spring上下文
   */
  public static ApplicationContext getContext() {
    if (null == ctx) synchronized (ApplicationManager.class) {
      if (null == ctx) ctx = new ClassPathXmlApplicationContext("conf/core/xd-jstorm2b.xml");
    }
    return ctx;
  }
}
