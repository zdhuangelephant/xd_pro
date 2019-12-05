package com.xiaodou.dashboard.engine;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.dashboard.model.alarm.local.EventPojo;

/**
 * @name @see com.xiaodou.dashboard.engine.EventContainer.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年11月25日
 * @description 事件容器
 * @version 1.0
 */
public class EventContainer {

  private Map<String, EventPojo> pojoMap = Maps.newConcurrentMap();

  /** _container 单例 */
  private static EventContainer _container = null;

  /**
   * 私有构造方法
   */
  private EventContainer() {}

  public static synchronized EventContainer getContainer() {
    if (null == _container) synchronized (EventContainer.class) {
      if (null == _container) _container = new EventContainer();
    }
    return _container;
  }

  private static void add0(EventPojo entity) {
    getContainer().pojoMap.put(entity.uniqueId(), entity);
  }

  private static EventPojo get0(EventPojo key) {
    String uniqueId = key.uniqueId();
    System.out.println(uniqueId);
    System.out.println(getContainer().pojoMap.size());
    System.out.println(getContainer().pojoMap.containsKey(uniqueId));
    return getContainer().pojoMap.get(uniqueId);
  }

  private static List<EventPojo> list0() {
    return Lists.newArrayList(getContainer().pojoMap.values());
  }

  private static synchronized void destroy0() {
    _container = null;
  }

  public void add(EventPojo entity) {
    EventContainer.add0(entity);
  }

  public EventPojo get(EventPojo key) {
    return EventContainer.get0(key);
  }

  public List<EventPojo> list() {
    return EventContainer.list0();
  }

  public void destroy() {
    EventContainer.destroy0();
  }

}
