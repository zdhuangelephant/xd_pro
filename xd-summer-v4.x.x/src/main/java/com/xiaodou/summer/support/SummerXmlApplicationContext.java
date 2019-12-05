package com.xiaodou.summer.support;

import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * @name @see com.xiaodou.summer.support.SummerXmlApplicationContext.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月14日
 * @description Summer补充框架ApplicationContext拓展类
 * @version 1.0
 */
public class SummerXmlApplicationContext extends XmlWebApplicationContext {

  {
    this.registerShutdownHook();
  }

  @Override
  protected void onRefresh() {
    SummerBeanExpandManager.getInstance().refreshAll();
    super.onRefresh();
  }

  @Override
  protected void onClose() {
    SummerBeanExpandManager.getInstance().shutDownAll();
    super.onClose();
  }

}
