package com.xiaodou.autotest.core;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.autotest.core.annotions.ApiExProcesser;
import com.xiaodou.autotest.core.interfaces.ProcessApiException;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.ScanPath;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * @name @see com.xiaodou.autotest.core.ActionProcesserCenter.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年2月9日
 * @description Action异常处理器注册中心
 * @version 1.0
 */
public class ActionProcesserCenter {

  private static String MODE_KEY = "env.mode";

  private static String MODE_TYPE_DEBUG = "DEBUG";

  private static ActionProcesserHolder holder = new ActionProcesserHolder();

  private Set<String> processerNameSet = Sets.newHashSet();
  private Set<ProcessApiException> processerSet = Sets.newHashSet();

  void pushProcessApiException(ProcessApiException processer) {
    String name = processer.getClass().getName();
    if (processerNameSet.contains(name)) {
      return;
    }
    processerNameSet.add(name);
    processerSet.add(processer);
  }

  ActionProcesserCenter merge(ActionProcesserCenter center) {
    if (null == center) {
      return this;
    }
    processerSet.addAll(center.processerSet);
    return this;
  }

  public static Set<ProcessApiException> getProcesserSet(String actionName) {
    ActionProcesserCenter center = holder.getCenter(actionName);
    if (null == center) {
      return null;
    }
    return center.processerSet;
  }

  private static class ActionProcesserHolder extends ScanPath {
    private static String processer_path = "com.xiaodou.autotest";
    private static String base_center = "base_center";

    public ActionProcesserHolder() {
      super(processer_path);
    }

    private Map<String, ActionProcesserCenter> centerMap;

    public synchronized void pushProcessApiException(String actionName,
        ProcessApiException processer) {
      if (StringUtils.isBlank(actionName)) {
        actionName = base_center;
      }
      if (null == centerMap) {
        centerMap = Maps.newHashMap();
      }
      if (!centerMap.containsKey(actionName)) {
        centerMap.put(actionName, new ActionProcesserCenter());
      }
      ActionProcesserCenter center = centerMap.get(actionName);
      center.pushProcessApiException(processer);
    }

    public ActionProcesserCenter getCenter(String actionName) {
      ActionProcesserCenter merge =
          new ActionProcesserCenter().merge(centerMap.get(actionName)).merge(
              centerMap.get(base_center));
      return merge;
    }

    @Override
    protected void processClass(Class<?> clazz) {
      if (null == clazz) {
        return;
      }
      if (!ProcessApiException.class.isAssignableFrom(clazz)) {
        return;
      }
      ApiExProcesser processer = clazz.getAnnotation(ApiExProcesser.class);
      if (null == processer) {
        return;
      }
      if (!processer.isDebug() || ConfigProp.getParams(MODE_KEY).equals(MODE_TYPE_DEBUG)) {
        try {
          pushProcessApiException(processer.actionName(), (ProcessApiException) clazz.newInstance());
        } catch (Exception e) {
          e.printStackTrace();
          LoggerUtil.error("初始化processer失败", e);
        }
      }
    }
  }

}
