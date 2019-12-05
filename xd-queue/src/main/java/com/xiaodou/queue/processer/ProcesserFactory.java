package com.xiaodou.queue.processer;

import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.queue.util.CloneUtil;

public class ProcesserFactory {
  /**
   * 创建一个新的processer，采用clone的方式创建
   * 
   * @param processer
   */
  public static IProcesser createProcesser(IProcesser processer) {
    if (null == processer) {
      return null;
    }
    try {
      // 克隆方式进行创建
      return CloneUtil.deepClone(processer);
    } catch (Exception e) {
      LoggerUtil.error("processer深度拷贝异常", e);
      return null;
    }
  }

  /**
   * 创建一组新的processer，采用clone的方式创建
   * 
   * @param processer
   */
  public static Map<String, IProcesser> createProcesser(Map<String, IProcesser> processerMap) {
    if (null == processerMap) {
      return null;
    }
    try {
      // 克隆方式进行创建
      Map<String, IProcesser> _workMap = Maps.newHashMap();
      for (Map.Entry<String, IProcesser> processer : processerMap.entrySet())
        _workMap.put(processer.getKey(), CloneUtil.deepClone(processer.getValue()));
      return _workMap;
    } catch (Exception e) {
      LoggerUtil.error("processer深度拷贝异常", e);
      return null;
    }
  }

}
