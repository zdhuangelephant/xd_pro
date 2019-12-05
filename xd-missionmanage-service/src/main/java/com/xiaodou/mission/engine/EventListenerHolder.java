package com.xiaodou.mission.engine;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.mission.engine.event.BaseEvent;
import com.xiaodou.mission.engine.listener.IEventListener;

/**
 * @name @see com.xiaodou.mission.engine.EventListenerHolder.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月18日
 * @description 事件监听器容器
 * @version 1.0
 */
@SuppressWarnings({"rawtypes"})
public class EventListenerHolder {

  private static Map<Class, Set<IEventListener>> listenerMapInUse = Maps.newConcurrentMap();
  private static Map<Class, Set<IEventListener>> listenerMapSnapshot = Maps.newConcurrentMap();
  private static Map<Class, Set<IEventListener>> listenerMapRegister = Maps.newConcurrentMap();

  public synchronized static void registListener(Class<? extends BaseEvent> clazz,
      IEventListener abstractEventListener) {
    Set<IEventListener> listenerSet = listenerMapRegister.get(clazz);
    if (null == listenerSet) {
      listenerSet = Sets.newConcurrentHashSet();
      listenerMapRegister.put(clazz, listenerSet);
    }
    listenerSet.add(abstractEventListener);
  }

  public static void switchSnapshot() {
    listenerMapSnapshot = Maps.newHashMap();
    listenerMapSnapshot.putAll(listenerMapRegister);
    listenerMapInUse = listenerMapSnapshot;
    listenerMapRegister.clear();
    listenerMapRegister = Maps.newHashMap();
  }

  public static void switchNewest() {
    listenerMapInUse = listenerMapRegister;
    listenerMapSnapshot.clear();
    listenerMapSnapshot = null;
  }

  public static <T extends BaseEvent> Set<IEventListener> getListenerSet(T event) {
    if (null == listenerMapInUse || listenerMapInUse.isEmpty()) {
      return null;
    }
    return listenerMapInUse.get(event.getClass());
  }

  public static Map<Class, Set<IEventListener>> getInUse() {
    return listenerMapInUse;
  }

}
