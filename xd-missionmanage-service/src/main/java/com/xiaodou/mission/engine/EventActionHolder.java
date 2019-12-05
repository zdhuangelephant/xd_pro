package com.xiaodou.mission.engine;

import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.mission.engine.action.AddNoteEventAction;
import com.xiaodou.mission.engine.action.BeFollowedEventAction;
import com.xiaodou.mission.engine.action.BuyCourseEventAction;
import com.xiaodou.mission.engine.action.CollectionEventAction;
import com.xiaodou.mission.engine.action.CreditTotalEventAction;
import com.xiaodou.mission.engine.action.DailyPracticeEventAction;
import com.xiaodou.mission.engine.action.EliminateWrongQuesEventAction;
import com.xiaodou.mission.engine.action.FollowEventAction;
import com.xiaodou.mission.engine.action.FriendAddEventAction;
import com.xiaodou.mission.engine.action.FriendPkEventAction;
import com.xiaodou.mission.engine.action.IAction;
import com.xiaodou.mission.engine.action.ImproveInfoEventAction;
import com.xiaodou.mission.engine.action.LeakFillingEventAction;
import com.xiaodou.mission.engine.action.RandomPkEventAction;
import com.xiaodou.mission.engine.action.TollgateEventAction;
import com.xiaodou.mission.engine.action.WrongQuesCountEventAction;
import com.xiaodou.mission.engine.event.AddNoteEvent;
import com.xiaodou.mission.engine.event.BaseEvent;
import com.xiaodou.mission.engine.event.BeFollowedEvent;
import com.xiaodou.mission.engine.event.BuyCourseEvent;
import com.xiaodou.mission.engine.event.CollectionEvent;
import com.xiaodou.mission.engine.event.CreditTotalEvent;
import com.xiaodou.mission.engine.event.DailyPracticeEvent;
import com.xiaodou.mission.engine.event.EliminateWrongQuesEvent;
import com.xiaodou.mission.engine.event.FollowEvent;
import com.xiaodou.mission.engine.event.FriendAddEvent;
import com.xiaodou.mission.engine.event.FriendPkEvent;
import com.xiaodou.mission.engine.event.ImproveInfoEvent;
import com.xiaodou.mission.engine.event.LeakFillingEvent;
import com.xiaodou.mission.engine.event.RandomPkEvent;
import com.xiaodou.mission.engine.event.TollgateEvent;
import com.xiaodou.mission.engine.event.WrongQuesCountEvent;

/**
 * @name @see com.xiaodou.mission.engine.EventActionHolder.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 事件行为容器
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class EventActionHolder {

  private EventActionHolder() {}

  private final static EventActionHolder INSTANCE = new EventActionHolder();

  public static EventActionHolder getInstance() {
    return INSTANCE;
  }

  private final Map<Class<? extends BaseEvent>, Class<? extends IAction>> eventActionMap = Maps
      .newHashMap();

  {
    eventActionMap.put(BeFollowedEvent.class, BeFollowedEventAction.class);
    eventActionMap.put(CollectionEvent.class, CollectionEventAction.class);
    eventActionMap.put(CreditTotalEvent.class, CreditTotalEventAction.class);
    eventActionMap.put(EliminateWrongQuesEvent.class, EliminateWrongQuesEventAction.class);
    eventActionMap.put(WrongQuesCountEvent.class, WrongQuesCountEventAction.class);
    eventActionMap.put(DailyPracticeEvent.class, DailyPracticeEventAction.class);
    eventActionMap.put(LeakFillingEvent.class, LeakFillingEventAction.class);
    eventActionMap.put(FollowEvent.class, FollowEventAction.class);
    eventActionMap.put(FriendAddEvent.class, FriendAddEventAction.class);
    eventActionMap.put(ImproveInfoEvent.class, ImproveInfoEventAction.class);
    eventActionMap.put(BuyCourseEvent.class, BuyCourseEventAction.class);
    eventActionMap.put(RandomPkEvent.class, RandomPkEventAction.class);
    eventActionMap.put(FriendPkEvent.class, FriendPkEventAction.class);
    eventActionMap.put(TollgateEvent.class, TollgateEventAction.class);
    eventActionMap.put(AddNoteEvent.class, AddNoteEventAction.class);
  }

  @SuppressWarnings("unchecked")
  public <T extends BaseEvent> IAction<T> getAction(Class<T> eventType)
      throws InstantiationException, IllegalAccessException {
    return eventActionMap.get(eventType).newInstance();
  }

}
