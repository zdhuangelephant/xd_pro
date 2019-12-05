package com.xiaodou.mission.engine.model.mission;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import org.springframework.util.Assert;

import com.google.common.collect.Sets;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.domain.UserMissionRecordModel;
import com.xiaodou.mission.domain.UserTaskHitCompleteModel;
import com.xiaodou.mission.engine.ProRelationCatHolder;
import com.xiaodou.mission.engine.model.Context;
import com.xiaodou.mission.service.QueueService;
import com.xiaodou.mission.service.cache.impl.UserMissionListCacheImpl.UserTaskStatistic;
import com.xiaodou.mission.service.facade.MissionOperationFacade;
import com.xiaodou.mission.vo.event.FinishAllMissionEvent;
import com.xiaodou.mission.vo.event.FinishAllMissionEvent.FinishAllMessionRequest;
import com.xiaodou.mission.vo.event.FinishMissionEvent;
import com.xiaodou.mission.vo.event.FinishMissionEvent.FinishMessionRequest;
import com.xiaodou.mission.vo.message.CreateRedBonusMessage;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.mission.engine.model.mission.StandardRecord.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 标准任务类型数据
 * @version 1.0
 */
public class StandardRecord extends AbstractTaskRecord {

  public StandardRecord() {
    setRecordStatus(MissionConstant.RECORD_STATUS_AUTO);
  }

  @Override
  protected void createRecord(Context context) {
    if (null == context.getMissionOperationFacade()) {
      return;
    }
    if (MissionConstant.TRUE != getIsReached()) {
      return;
    }
    if (null != getBuyCourseSet() && !getBuyCourseSet().isEmpty()
        && getBuyCourseSet().contains(getCourseId())) {
      setIsReached(MissionConstant.TASK_STATUS_UNDISPLAY);
    }
    UserMissionRecordModel domainModel = buildDomain();
    setFinishTime(new Timestamp(System.currentTimeMillis()));
    context.getMissionOperationFacade().insertUserMissionRecord(domainModel);
    Assert.isTrue(StringUtils.isNotBlank(domainModel.getModule()));
    RabbitMQSender.getInstance().send(new CreateRedBonusMessage(domainModel));
  }

  @Override
  protected void afterStore(Context context) {
    if (null == context.getMissionOperationFacade()) {
      return;
    }

    // 这里要兼容提前完成任务而不展示的情况
    if (MissionConstant.TRUE != getIsReached()
        && MissionConstant.TASK_STATUS_UNDISPLAY != getIsReached()) {
      return;
    }

    // 1. 查询是否有剩余任务
    checkUnReachTask(context);

    // 2. 查询今日做任务情况,是否今天完成过任务,是否存在未完成任务
    check2DayUnReachTask(context);
  }

  /**
   * 查询是否有剩余任务
   * 
   * @param context 上下文
   */
  private UserTaskStatistic checkUnReachTask(Context context) {
    Set<String> majorIdSet = Sets.newHashSet();
    if (StringUtils.isNotBlank(context.getEvent().getMajorId())) {
      majorIdSet.add(context.getEvent().getMajorId());
    }
    UserTaskStatistic allTaskStatistic = context.getMissionOperationFacade().refreshUnReachTaskNum(
        getUserId(), getModule(), majorIdSet, Sets.newHashSet(getCourseId()));
    if (null != allTaskStatistic
        && allTaskStatistic.getTotalCount() == allTaskStatistic.getCompleteCount()) {
      Set<String> catIdList = ProRelationCatHolder.getRelationCatSet(getCourseId());
      if (null == catIdList || catIdList.isEmpty()) {
        return allTaskStatistic;
      }
      for (String catId : catIdList) {
        sendFinishAllMes(getUserId(), getModule(), catId, getCourseId());
      }
    }
    return allTaskStatistic;
  }

  /**
   * 查询今日做任务情况,是否今天完成过任务,是否存在未完成任务
   * 
   * @param context 上下文
   */
  private UserTaskStatistic check2DayUnReachTask(Context context) {
    Set<String> majorIdSet = Sets.newHashSet();
    if (StringUtils.isNotBlank(context.getEvent().getMajorId())) {
      majorIdSet.add(context.getEvent().getMajorId());
    }
    UserTaskStatistic todayTaskStatistic =
        context.getMissionOperationFacade().refresh2DayUnReachTaskNum(getUserId(), getModule(),
            majorIdSet, Sets.newHashSet(getCourseId()));
    if (null != todayTaskStatistic
        && todayTaskStatistic.getTotalCount() == todayTaskStatistic.getCompleteCount()) {
      // 记录今天 谁 完成了 哪门课程 当日任务
      MissionOperationFacade facade = context.getMissionOperationFacade();
      IQueryParam param = new QueryParam();
      param.addInput("module", getModule());
      param.addInput("courseId", getCourseId());
      param.addInput("uid", getUserId());
      param.addInput("createTimeUpper",
          new Date(DateUtil.getStartTimeOfDay(new Timestamp(System.currentTimeMillis()))));
      param.addInput("createTimeLower", new Date(DateUtil.getTimesnight(0)));
      param.addOutputs(CommUtil.getAllField(UserTaskHitCompleteModel.class));
//      System.out.println(String.format("%s,%s,%s,%s,%s","***********", getModule(),context.getEvent().getMajorId(),
//        getCourseId(),getUserId()));
      Page<UserTaskHitCompleteModel> page = facade.listUserTaskHitComplete(param, null);
      if (null == page || page.getResult() == null || page.getResult().isEmpty()) {
        UserTaskHitCompleteModel hitInfo = new UserTaskHitCompleteModel();
        hitInfo.setCourseId(getCourseId());
        hitInfo.setUid(getUserId());
        hitInfo.setModule(getModule());
        hitInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
        hitInfo.setIsComplete(MissionConstant.TASK_STATUS_REACHED);
        facade.insertUserTaskHitComplete(hitInfo);
      }

      Set<String> catIdList = ProRelationCatHolder.getRelationCatSet(getCourseId());
      if (null == catIdList || catIdList.isEmpty()) {
        return todayTaskStatistic;
      }
      for (String catId : catIdList) {
        sendFinishMes(getUserId(), getModule(), catId, getCourseId());
      }
    }
    return todayTaskStatistic;
  }

  /**
   * 发送完成课程当天任务消息
   * 
   * @param userId 用户ID
   * @param module 模块ID
   * @param majorId 专业ID
   * @param courseId 课程ID
   */
  private void sendFinishMes(String userId, String module, String majorId, String courseId) {
    FinishMessionRequest request = new FinishMessionRequest();
    request.setUserId(userId);
    request.setMajorId(majorId);
    request.setCourseId(courseId);
    request.setModule(module);
    request.setCurrentTime(new Timestamp(System.currentTimeMillis()));
    new FinishMissionEvent(request).send();
  }

  /**
   * 发送完成课程全部任务消息
   * 
   * @param userId 用户ID
   * @param module 模块ID
   * @param majorId 专业ID
   * @param courseId 课程ID
   */
  private void sendFinishAllMes(String userId, String module, String majorId, String courseId) {
    FinishAllMessionRequest request = new FinishAllMessionRequest();
    request.setUserId(userId);
    request.setMajorId(majorId);
    request.setCourseId(courseId);
    request.setModule(module);
    request.setCurrentTime(new Timestamp(System.currentTimeMillis()));
    new FinishAllMissionEvent(request).send();
  }
}
