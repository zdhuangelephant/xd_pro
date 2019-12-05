package com.xiaodou.webfetch.unique;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @name @see com.xiaodou.webfetch.unique.TargetHolder.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月13日
 * @description 可达对象容器
 * @version 1.0
 * @param <T>
 */
public class TargetHolder<T extends IUnique> {

  public Map<String, T> holder = Maps.newHashMap();

  public synchronized void push(T target) {
    holder.put(target.unique(), target);
  }

  public T get(Target target) {
    return holder.get(target.unique());
  }

  public void clear() {
    holder.clear();
  }

  public void destroy() {
    holder.clear();
    holder = null;
  }

}
