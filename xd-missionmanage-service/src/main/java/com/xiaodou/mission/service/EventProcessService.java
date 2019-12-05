package com.xiaodou.mission.service;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.jmsg.entity.MessageRemoteResult;
import com.xiaodou.jmsg.entity.MessageRemoteResult.MessageRemoteResultType;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.domain.CascadeMissionRecordModel;
import com.xiaodou.mission.domain.UserCollectDataModel;
import com.xiaodou.mission.engine.EventDispatcher;
import com.xiaodou.mission.engine.EventDispatcher.IListenerFilter;
import com.xiaodou.mission.engine.MissionEnums.MissionType;
import com.xiaodou.mission.engine.ProRelationCatHolder;
import com.xiaodou.mission.engine.event.BaseEvent;
import com.xiaodou.mission.engine.event.EventBuilder;
import com.xiaodou.mission.engine.listener.IEventListener;
import com.xiaodou.mission.engine.model.Context;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;
import com.xiaodou.mission.engine.model.mission.AbstractMissionRecord;
import com.xiaodou.mission.engine.model.mission.IMissionRecord;
import com.xiaodou.mission.engine.model.mission.MissionRecordFactory;
import com.xiaodou.mission.service.cache.impl.UserMissionListCacheImpl.UserTaskStatistic;
import com.xiaodou.mission.service.facade.MissionOperationFacade;
import com.xiaodou.mission.util.MissionLoggerUtil;
import com.xiaodou.mission.vo.message.FollowBonusMessage;
import com.xiaodou.mission.vo.message.FollowBonusMessage.FollowBonus;
import com.xiaodou.mission.vo.message.UpdateScoreMessage;
import com.xiaodou.mission.vo.model.UserMissionRecordHolder;
import com.xiaodou.mission.vo.request.EventRequest;
import com.xiaodou.mission.vo.request.ReceiveAward;
import com.xiaodou.summer.dao.param.IJoinQueryParam;
import com.xiaodou.summer.dao.param.JoinQueryParam;

/**
 * @name @see com.xiaodou.mission.service.EventProcessService.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 事件响应处理Service
 * @version 1.0
 */
@Service("eventProcessService")
public class EventProcessService {

  @Resource
  QueueService queueService;
  @Resource
  MissionOperationFacade missionOperationFacade;

  /**
   * answer the event 响应事件
   * 
   * @param request 事件请求
   * @return 处理结果
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  public MessageRemoteResult onEvent(EventRequest request) throws InstantiationException,
      IllegalAccessException {
    BaseEvent event = EventBuilder.buildEvent(request);
    MessageRemoteResult res = new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    try {
      Context context = new Context();
      UserCollectDataInstance userCollectDataInstance = processUserData(event, context);
      // 过滤用户已完成任务
      final UserMissionRecordHolder userMissionHolder =
          filterCompletedMission(userCollectDataInstance);
      Set<IMissionRecord> userRecordSet =
          EventDispatcher.notifyListener(event, context, new IListenerFilter() {
            @Override
            public AbstractMissionRecord doFilter(IEventListener listener) {
              if (null == userMissionHolder) {
                return null;
              }
              return userMissionHolder.getUserRecordByMissionId(listener.getMissionInstance()
                  .getId());
            }
          });
      if (null == userRecordSet || userRecordSet.isEmpty()) {
        return res;
      }
      for (IMissionRecord instance : userRecordSet) {
        instance.storeRecord(context);
      }

      // 发送更新分数成绩消息
      if (StringUtils.isAllNotBlank(event.getUserId(), event.getModule(), event.getCourseId())) {
        RabbitMQSender.getInstance().send(new UpdateScoreMessage(event, checkUnReachTask(event)));
      }
    } catch (Exception e) {
      MissionLoggerUtil.error("处理事件异常. req=" + FastJsonUtil.toJson(request), e);
    }
    return res;
  }

  /**
   * 查询是否有剩余任务
   * 
   * @param context 上下文
   */
  private UserTaskStatistic checkUnReachTask(BaseEvent event) {
    Set<String> majorIdSet = Sets.newHashSet();
    if (StringUtils.isNotBlank(event.getMajorId())) {
      majorIdSet.add(event.getMajorId());
    }
    UserTaskStatistic allTaskStatistic =
        missionOperationFacade.refreshUnReachTaskNum(event.getUserId(), event.getModule(),
            majorIdSet, Sets.newHashSet(event.getCourseId()));
    if (null != allTaskStatistic
        && allTaskStatistic.getTotalCount() == allTaskStatistic.getCompleteCount()) {
      Set<String> catIdList = ProRelationCatHolder.getRelationCatSet(event.getCourseId());
      if (null == catIdList || catIdList.isEmpty()) {
        return allTaskStatistic;
      }
    }
    return allTaskStatistic;
  }

  /**
   * 过滤用户已经完成的任务
   */
  private UserMissionRecordHolder filterCompletedMission(
      UserCollectDataInstance userCollectDataInstance) {
    IJoinQueryParam param = new JoinQueryParam();
    List<Integer> recordStatusList = Lists.newArrayList();
    // 记录状态 自动生成
    recordStatusList.add(MissionConstant.RECORD_STATUS_AUTO);
    // 记录状态 正常
    recordStatusList.add(MissionConstant.RECORD_STATUS_NORMAL);
    param.addJoin("recordStatusList", recordStatusList);
    param.addJoin("userId", userCollectDataInstance.getUserId());
    param.addInput("missionState", MissionConstant.MISSON_STATE_INUSE);
    param.addInput("userId", userCollectDataInstance.getUserId());
    param.addInput("module", userCollectDataInstance.getModule());
    List<CascadeMissionRecordModel> userMissionList =
        missionOperationFacade.queryCascadeMissionRecordModel(param);
    UserMissionRecordHolder userMissionRecordHolder = new UserMissionRecordHolder();
    if (null != userMissionList && !userMissionList.isEmpty()) {
      userMissionRecordHolder.initUserMissionList(userMissionList);
    }
    return userMissionRecordHolder;
  }

  /**
   * 处理用户数据
   */
  public UserCollectDataInstance processUserData(BaseEvent event, Context context) throws Exception {
    context.setCurrentThreshold(event.getThreshold());
    context.setMissionOperationFacade(missionOperationFacade);
    UserCollectDataModel userData =
        missionOperationFacade.queryUserData(event.getClass(), new UserCollectDataModel(
            new UserCollectDataInstance(event)));
    UserCollectDataInstance userCollectDataInstance = new UserCollectDataInstance(userData);
    context.setCoreParam(userCollectDataInstance);
    EventDispatcher.doDispatch(event, context);
    missionOperationFacade.updateUserData(new UserCollectDataModel(context.getCoreParam()));
    return userCollectDataInstance;
  }

  /**
   * 获取任务奖励
   * 
   * @param request 获取任务奖励请求
   */
  public MessageRemoteResult receiveTaskAward(ReceiveAward request) {
    MessageRemoteResult result = new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    IJoinQueryParam param = new JoinQueryParam();
    List<Integer> recordStatusList = Lists.newArrayList();
    recordStatusList.add(MissionConstant.RECORD_STATUS_NORMAL);
    param.addJoin("recordStatusList", recordStatusList);
    param.addJoin("userId", request.getUserId());
    param.addInput("recordId", request.getRecordId());
    param.addInput("recordStatus", MissionConstant.RECORD_STATUS_NORMAL);
    param.addInput("missionState", MissionConstant.MISSON_STATE_INUSE);
    param.addInput("userId", request.getUserId());
    param.addInput("isReached", MissionConstant.TASK_STATUS_REACHED);
    List<String> moduleList = Lists.newArrayList();
    moduleList.add(MissionConstant.COMMON_MODULE);
    moduleList.add(request.getModule());
    param.addInput("moduleList", moduleList);
    param.addInput("missionId", request.getMissionId());
    param.addInput("missionType", MissionType.task.toString());
    List<CascadeMissionRecordModel> userMissionList =
        missionOperationFacade.queryCascadeMissionRecordModel(param);
    if (null == userMissionList || userMissionList.isEmpty()) {
      return result;
    }
    Set<Long> recordSet = Sets.newHashSet();
    for (CascadeMissionRecordModel userMission : userMissionList) {
      if (null == userMission
          || !(MissionConstant.TASK_STATUS_REACHED == userMission.getIsReached())
          || null == userMission.getRecordId() || recordSet.contains(userMission.getRecordId())) {
        continue;
      }
      recordSet.add(userMission.getRecordId());
      IMissionRecord record = MissionRecordFactory.buildMissionRecord(userMission);
      Context context = new Context();
      context.setMissionOperationFacade(missionOperationFacade);
      record.touchAward(context);
    }
    if (StringUtils.isNotBlank(request.getBonusId())) {
      followBonus(request.getBonusId());
    }
    return result;
  }

  private void followBonus(String bonusId) {
    FollowBonus message = new FollowBonus();
    message.setBonusId(bonusId);
    RabbitMQSender.getInstance().send(new FollowBonusMessage(message));
  }

}
