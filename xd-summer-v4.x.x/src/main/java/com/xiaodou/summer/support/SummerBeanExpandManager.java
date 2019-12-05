package com.xiaodou.summer.support;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.support.expand.IRefresh;
import com.xiaodou.summer.support.expand.IShutDown;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.summer.support.SummerBeanExpandManager.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月12日
 * @description Summer补充框架bean扩展管理器
 * @version 1.0
 */
public class SummerBeanExpandManager {

  private List<String> refreshList = Lists.newArrayList();
  private List<String> shutDownList = Lists.newArrayList();
  private static SummerBeanExpandManager manager = null;

  private synchronized static void init() {
    if (null == manager) manager = new SummerBeanExpandManager();
  }

  public static SummerBeanExpandManager getInstance() {
    if (null == manager) init();
    return manager;
  }

  public void registRefresh(String refresh) {
    refreshList.add(refresh);
  }

  public void registShutDown(String shutDown) {
    shutDownList.add(shutDown);
  }

  public void refreshAll() {
    if (refreshList.isEmpty()) return;
    for (String refresh : refreshList) {
      IRefresh bean = SpringWebContextHolder.getBean(refresh);
      if (null != bean) bean.refresh();
    }
  }

  public void shutDownAll() {
    if (shutDownList.isEmpty()) return;
    for (String shutdown : shutDownList) {
      IShutDown bean = SpringWebContextHolder.getBean(shutdown);
      if (null != bean) bean.shutDown();
    }
  }
}
