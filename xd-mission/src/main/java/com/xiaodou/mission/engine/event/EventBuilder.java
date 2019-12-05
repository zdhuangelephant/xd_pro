package com.xiaodou.mission.engine.event;

import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.ScanPath;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mission.vo.request.EventRequest;

/**
 * @name @see com.xiaodou.mission.engine.event.EventBuilder.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年3月23日
 * @description 事件构建器
 * @version 1.0
 */
public final class EventBuilder {
  private EventBuilder() {}

  private static final Map<String, Class<?>> eventTypeMap = Maps.newHashMap();
  static {
    new ScanPath(EventBuilder.class.getPackage().getName()) {
      @Override
      protected void processClass(Class<?> clazz) {
        eventTypeMap.put(clazz.getSimpleName(), clazz);
      }
    };
  }

  @SuppressWarnings("unchecked")
  public static <T extends BaseEvent> T buildEvent(EventRequest request) {
    Class<?> clazz = eventTypeMap.get(request.getEventType());
    if (null == clazz) return null;
    return (T) FastJsonUtil.fromJson(request.getEventMessage(), clazz);
  }

  public static <T extends BaseEvent> EventRequest buildRequest(T event) {
    EventRequest req = new EventRequest();
    req.setEventTag(UUID.randomUUID().toString());
    req.setEventType(event.getClass().getSimpleName());
    req.setEventMessage(FastJsonUtil.toJson(event));
    return req;
  }

  public static BeFollowedEvent buildBeFollowedEvent() {
    return new BeFollowedEvent();
  }

  public static EliminateWrongQuesEvent buildEliminateWrongQuesEvent() {
    return new EliminateWrongQuesEvent();
  }

  public static FriendAddEvent buildFriendAddEvent() {
    return new FriendAddEvent();
  }

  public static TollgateEvent buildTollgateEvent() {
    return new TollgateEvent();
  }

  public static FollowEvent buildFollowEvent() {
    return new FollowEvent();
  }

  public static RandomPkEvent buildRandomPkEvent() {
    return new RandomPkEvent();
  }

  public static FriendPkEvent buildFriendPkEvent() {
    return new FriendPkEvent();
  }

  public static ImproveInfoEvent buildImproveInfoEvent() {
    return new ImproveInfoEvent();
  }

  public static CreditTotalEvent buildCreditTotalEvent() {
    return new CreditTotalEvent();
  }

  public static CollectionEvent buildCollectionEvent() {
    return new CollectionEvent();
  }

  public static WrongQuesCountEvent buildWrongQuesCountEvent() {
    return new WrongQuesCountEvent();
  }

  public static BuyCourseEvent buildBuyCourseEvent() {
    return new BuyCourseEvent();
  }

  public static AddNoteEvent buildAddNoteEvent() {
    return new AddNoteEvent();
  }

  public static DailyPracticeEvent buildDailyPracticeEvent() {
    return new DailyPracticeEvent();
  }

  public static ReceiveTaskAwardEvent buildReceiveTaskAwardEvent() {
    return new ReceiveTaskAwardEvent();
  }
  
  public static LeakFillingEvent buildLeakFillingEvent() {
    return new LeakFillingEvent();
  }
}
