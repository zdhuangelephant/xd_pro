package com.xiaodou.mission.engine;

import java.util.Set;

import com.google.common.collect.Sets;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.action.IAction;
import com.xiaodou.mission.engine.event.BaseEvent;
import com.xiaodou.mission.engine.listener.IEventListener;
import com.xiaodou.mission.engine.model.Context;
import com.xiaodou.mission.engine.model.mission.AbstractMissionRecord;
import com.xiaodou.mission.engine.model.mission.IMissionRecord;
import com.xiaodou.mission.engine.model.mission.MissionRecordFactory;

/**
 * @name @see com.xiaodou.mission.engine.EventDispatcher.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月18日
 * @description 事件分发器
 * @version 1.0
 */
public class EventDispatcher {

  private static final IListenerFilter DEFAULT_FILTER = new IListenerFilter() {
    @Override
    public AbstractMissionRecord doFilter(IEventListener listener) {
      return null;
    }
  };

  @SuppressWarnings("unchecked")
  public static <T extends BaseEvent> void doDispatch(T event, Context context)
      throws InstantiationException, IllegalAccessException {
    Class<T> classType = (Class<T>) event.getClass();
    IAction<T> action = EventActionHolder.getInstance().getAction(classType);
    if (null != action) {
      action.doAction(event, context);
    }
  }

  public static <T extends BaseEvent> Set<IMissionRecord> notifyListener(T event, Context context,
      IListenerFilter filter) throws InstantiationException, IllegalAccessException {
    Set<IMissionRecord> result = Sets.newHashSet();
    IListenerFilter defaultFilter = DEFAULT_FILTER;
    Set<IEventListener> listenerSet = EventListenerHolder.getListenerSet(event);
    if (null == listenerSet || listenerSet.isEmpty()) {
      return null;
    }
    for (IEventListener listener : listenerSet) {
      if (!MissionConstant.COMMON_MODULE.equals(listener.getMissionInstance().getModule())
          && StringUtils.isNotBlank(event.getModule())
          && !event.getModule().equals(listener.getMissionInstance().getModule())) {
        continue;
      }
      if (!MissionConstant.COMMON_COURSE_ID.equals(listener.getMissionInstance().getCourseId())
          && StringUtils.isNotBlank(event.getCourseId())
          && !event.getCourseId().equals(listener.getMissionInstance().getCourseId())) {
        continue;
      }
      if (null != filter) {
        defaultFilter = filter;
      }
      AbstractMissionRecord userMissionModel = defaultFilter.doFilter(listener);
      if (null == userMissionModel
          && listener.getMissionInstance().getTaskType().checkPreCond(listener, context)) {
        userMissionModel =
            MissionRecordFactory.buildMissionRecord(context.getCoreParam(),
                listener.getMissionInstance());
        userMissionModel.setRecordStatus(listener.getMissionInstance().getTaskType()
            .getRecordStatus());
      }
      if (null != userMissionModel && userMissionModel.getIsReached() == MissionConstant.FALSE) {
        result.add(listener.onEvent(context, userMissionModel));
      }
    }
    return result;
  }

  /**
   * @name @see com.xiaodou.mission.engine.EventDispatcher.java
   * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2016年3月16日
   * @description 监听器过滤
   * @version 1.0
   */
  public interface IListenerFilter {
    /**
     * 过滤方法
     * 
     * @param listener 事件监听器
     * @return 过滤后用户任务数据
     */
    public AbstractMissionRecord doFilter(IEventListener listener);
  }

}
