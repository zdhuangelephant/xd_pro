package com.xiaodou.mission.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.domain.CascadeMissionRecordModel;
import com.xiaodou.mission.domain.UserMissionRecordModel;
import com.xiaodou.mission.domain.UserTaskHitCompleteModel;
import com.xiaodou.mission.engine.MissionEnums.MissionConditionType;
import com.xiaodou.mission.engine.MissionEnums.MissionType;
import com.xiaodou.mission.engine.MissionEnums.TaskType;
import com.xiaodou.mission.engine.model.MissionInstance;
import com.xiaodou.mission.engine.model.mission.AbstractMissionRecord;
import com.xiaodou.mission.engine.model.mission.MissionRecordFactory;
import com.xiaodou.mission.service.cache.impl.UserMissionListCacheImpl.UserTaskStatistic;
import com.xiaodou.mission.service.facade.MissionOperationFacade;
import com.xiaodou.mission.vo.model.DateTaskHolder;
import com.xiaodou.mission.vo.model.UserMissionList;
import com.xiaodou.mission.vo.request.MedalListRequest;
import com.xiaodou.mission.vo.request.TaskCompleteStatusRequest;
import com.xiaodou.mission.vo.request.TaskListRequest;
import com.xiaodou.mission.vo.request.TaskStatusRequest;
import com.xiaodou.mission.vo.request.UnReachedTaskCountRequest;
import com.xiaodou.mission.vo.response.MedalListResponse;
import com.xiaodou.mission.vo.response.MedalListResponse.Medal;
import com.xiaodou.mission.vo.response.TaskCompleteStatusResponse;
import com.xiaodou.mission.vo.response.TaskListResponse;
import com.xiaodou.mission.vo.response.TaskListResponse.Task;
import com.xiaodou.mission.vo.response.TaskStatusResponse;
import com.xiaodou.mission.vo.response.UnReachedTaskCountResponse;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IJoinQueryParam;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.JoinQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.mission.service.MissionService.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 任务逻辑Service
 * @version 1.0
 */
@Service("missionService")
public class MissionService {

  @Resource
  QueueService queueService;

  @Resource
  MissionOperationFacade missionOperationFacade;

  /**
   * 查询构建勋章列表
   * 
   * @param request 勋章列表查询条件
   * @return 勋章列表
   */
  public MedalListResponse medalList(MedalListRequest request) {
    MedalListResponse res = new MedalListResponse(ResultType.SUCCESS);
    IJoinQueryParam param = new JoinQueryParam();
    param.addJoin("userId", request.getUid());
    param.addInput("userId", request.getUid());
    param.addInput("module", request.getModule());
    param.addInput("missionType", MissionType.archieve.toString());
    List<CascadeMissionRecordModel> missionList =
        missionOperationFacade.queryCascadeMissionRecordModel(param);
    if (null == missionList || missionList.isEmpty()) {
      return res;
    }
    for (CascadeMissionRecordModel model : missionList) {
      res.getMedalList().add(new Medal(MissionRecordFactory.buildMissionRecord(model)));
    }
    return res;
  }

  public TaskListResponse taskList(TaskListRequest request) {
    TaskListResponse res = new TaskListResponse(ResultType.SUCCESS);
    UserTaskStatistic todayTaskStatistic = new UserTaskStatistic();
    if (!request.getMCourseMap().isEmpty()) {
      Set<String> majorIdSet = Sets.newHashSet(request.getMajorId());
      UserTaskStatistic taskStatistic =
          missionOperationFacade.getUnReachTaskNum(request.getUid(), request.getModule(),
              majorIdSet, request.getMCourseMap().keySet());
      res.setTotalMissionCount(taskStatistic.getTotalCount().toString());
      res.setReachedMissionCount(taskStatistic.getCompleteCount().toString());
    } else {
      res.setTotalMissionCount(MissionConstant.INTEGER_ZERO.toString());
      res.setReachedMissionCount(MissionConstant.INTEGER_ZERO.toString());
    }
    if (null == request.getLastDate()) {
      res.addAllTask(taskListNow(request, todayTaskStatistic));
    } else {
      res.addAllTask(taskListHis(request));
    }
    if (null != todayTaskStatistic.getTotalCount() && todayTaskStatistic.getTotalCount() > 0) {
      Double finishCount =
          null != todayTaskStatistic.getCompleteCount() ? new Double(
              todayTaskStatistic.getCompleteCount()) : 0d;
      Double finishPercent = finishCount / todayTaskStatistic.getTotalCount();
      if (finishPercent >= 1) {
        res.setTodayFinishMissionStatus(MissionConstant.TRUE.toString());
      }
    }
    res.setTodayTotalMissionCount(todayTaskStatistic.getTotalCount().toString());
    res.setTodayReachedMissionCount(todayTaskStatistic.getCompleteCount().toString());
    return res;
  }

  private DateTaskHolder taskListNow(TaskListRequest request, UserTaskStatistic todayTaskStatistic) {
    UserMissionList missionList = missionOperationFacade.getUserMissionList(request);
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", request.getUid());
    List<String> moduleList = Lists.newArrayList();
    moduleList.add(MissionConstant.COMMON_MODULE);
    moduleList.add(request.getModule());
    cond.put("moduleList", moduleList);
    List<Integer> recordStatusList = Lists.newArrayList();
    recordStatusList.add(MissionConstant.RECORD_STATUS_NORMAL);
    recordStatusList.add(MissionConstant.RECORD_STATUS_BACKUP);
    cond.put("recordStatusList", recordStatusList);
    List<String> courseIdList = Lists.newArrayList(request.getMCourseMap().keySet());
    courseIdList.add(MissionConstant.COMMON_COURSE_ID);
    cond.put("courseIdList", courseIdList);
    cond.put("deadLineLower", new Timestamp(System.currentTimeMillis()));
    List<UserMissionRecordModel> userMissionList =
        missionOperationFacade.queryUserMissionRecordList(cond);
    Map<String, List<UserMissionRecordModel>> recordMap = fetchRecord(userMissionList);
    DateTaskHolder taskHolder = new DateTaskHolder();
    taskHolder.initTodayTask();
    if (null == missionList || null == missionList.getTaskList()
        || missionList.getTaskList().isEmpty()) {
      return taskHolder;
    }
    List<CascadeMissionRecordModel> taskList = missionList.getTaskList();
    Timestamp date = new Timestamp(DateUtil.getTimesmorning(0));
    for (CascadeMissionRecordModel model : taskList) {
      if (!TaskType.statics.name().equals(model.getTaskType())
          && !request.getMCourseMap().containsKey(model.getCourseId())) {
        continue;
      }
      List<UserMissionRecordModel> recordList = recordMap.get(model.getId());
      if (null == recordList || recordList.isEmpty()) {
        if (TaskType.dynamic.name().equals(model.getTaskType())
            && !MissionConditionType.dailyPractice.name().equals(model.getCondType())) {
          continue;
        }
        // 第一次查询今日任务时,任务可能正在异步插入,这时需要兼容这种情形
        taskHolder.pushTask(date, new Task(request.getMCourseMap().get(model.getCourseId()),
            MissionRecordFactory.buildMissionRecord(model)));
        if (TaskType.standard.name().equals(model.getTaskType())) {
          todayTaskStatistic.setTotalCount(todayTaskStatistic.getTotalCount() + 1);
        }
      } else {
        for (UserMissionRecordModel record : recordList) {
          taskHolder.pushTask(date, new Task(request.getMCourseMap().get(model.getCourseId()),
              MissionRecordFactory.buildMissionRecord(record, new MissionInstance(model))));
          if (TaskType.standard.name().equals(model.getTaskType())) {
            todayTaskStatistic.setTotalCount(todayTaskStatistic.getTotalCount() + 1);
            if (MissionConstant.TASK_STATUS_UNREACH != record.getIsReached()) {
              todayTaskStatistic.setCompleteCount(todayTaskStatistic.getCompleteCount() + 1);
            }
          }
        }
      }
    }
    return taskHolder;
  }

  /**
   * 填充用户任务记录
   * 
   * @param userMissionList 用户任务记录列表
   * @return 用户任务记录列表Map
   */
  private Map<String, List<UserMissionRecordModel>> fetchRecord(
      List<UserMissionRecordModel> userMissionList) {
    Map<String, List<UserMissionRecordModel>> recordMap = Maps.newHashMap();
    if (null == userMissionList || userMissionList.isEmpty()) {
      return recordMap;
    }
    for (UserMissionRecordModel record : userMissionList) {
      if (record.currUnValid()) {
        continue;
      }
      List<UserMissionRecordModel> recordList = recordMap.get(record.getMissionId());
      if (null == recordList) {
        recordList = Lists.newLinkedList();
        recordMap.put(record.getMissionId(), recordList);
      }
      recordList.add(record);
    }
    return recordMap;
  }

  /**
   * 查询历史任务列表
   * 
   * @param request 任务列表查询参数类
   * @return 历史任务列表
   */
  private DateTaskHolder taskListHis(TaskListRequest request) {
    Timestamp upper = new Timestamp(request.getLastDate());
    IJoinQueryParam param = new JoinQueryParam();
    List<Integer> recordStatusList = Lists.newArrayList();
    recordStatusList.add(MissionConstant.RECORD_STATUS_NORMAL);
    recordStatusList.add(MissionConstant.RECORD_STATUS_BACKUP);
    param.addJoin("recordStatusList", recordStatusList);
    param.addJoin("userId", request.getUid());
    param.addInput("recordStatusList", recordStatusList);
    param.addInput("userId", request.getUid());
    List<String> moduleList = Lists.newArrayList();
    moduleList.add(MissionConstant.COMMON_MODULE);
    moduleList.add(request.getModule());
    param.addInput("moduleList", moduleList);
    List<String> courseIdList = Lists.newArrayList();
    if (null != request.getMCourseMap() && !request.getMCourseMap().isEmpty()) {
      courseIdList.addAll(request.getMCourseMap().keySet());
    }
    courseIdList.add(MissionConstant.COMMON_COURSE_ID);
    param.addInput("courseIdList", courseIdList);
    param.addInput("missionType", MissionType.task.toString());
    param.addInput("finishTimeUpper", upper);
    param.addSort("finishTime", Sort.DESC);
    List<CascadeMissionRecordModel> missionList =
        missionOperationFacade.queryCascadeMissionRecordModel(param);
    if (null == missionList || missionList.isEmpty()) {
      return null;
    }
    DateTaskHolder taskHolder = new DateTaskHolder();
    Set<String> dateSet = Sets.newHashSet();
    for (CascadeMissionRecordModel model : missionList) {
      if (MissionConstant.TASK_STATUS_UNDISPLAY == model.getIsReached()) {
        continue;
      }
      if (MissionConstant.TASK_STATUS_UNREACH == model.getIsReached()
          || null == model.getFinishTime()) {
        continue;
      }
      dateSet.add(DateUtil.SDF_YMD.format(model.getFinishTime()));
      if (dateSet.size() > 2) {
        break;
      }
      AbstractMissionRecord record = MissionRecordFactory.buildMissionRecord(model);
      taskHolder.pushTask(model.getFinishTime(),
          new Task(request.getMCourseMap().get(model.getCourseId()), record));
    }
    return taskHolder;
  }

  /**
   * 查询任务状态
   * 
   * @param request 任务状态请求参数类
   * @return
   */
  public TaskStatusResponse taskStatus(TaskStatusRequest request) {
    TaskStatusResponse response = new TaskStatusResponse(ResultType.SUCCESS);
    IJoinQueryParam param = new JoinQueryParam();
    List<Integer> recordStatusList = Lists.newArrayList();
    recordStatusList.add(MissionConstant.RECORD_STATUS_NORMAL);
    recordStatusList.add(MissionConstant.RECORD_STATUS_BACKUP);
    param.addJoin("recordStatusList", recordStatusList);
    param.addJoin("userId", request.getUid());
    param.addInput("recordStatusList", recordStatusList);
    param.addInput("missionId", request.getTaskId());
    param.addInput("recordId", request.getRecordId());
    param.addInput("userId", request.getUid());
    param.addInput("module", request.getModule());
    param.addInput("courseId", request.getCourseId());
    param.addInput("chapterId", request.getChapterId());
    param.addInput("missionType", MissionType.task.toString());
    List<CascadeMissionRecordModel> missionList =
        missionOperationFacade.queryCascadeMissionRecordModel(param);
    if (null == missionList || missionList.isEmpty()) {
      return response;
    }
    CascadeMissionRecordModel model = null;
    if (StringUtils.isNotBlank(request.getRecordId())) {
      model = missionList.get(0);
    } else {
      // 异步插入的记录没有返回前端recordId，这时候根据任务状态筛选第一个未领取的任务记录返回前端
      for (CascadeMissionRecordModel mission : missionList) {
        if (!(MissionConstant.TASK_STATUS_RECEIVED == mission.getIsReached())) {
          model = mission;
          break;
        }
      }
    }
    if (null == model) {
      return response;
    }
    AbstractMissionRecord record = MissionRecordFactory.buildMissionRecord(model);
    response.setTask(new Task(null, record));
    return response;
  }


  /**
   * 查询某用户的必做任务完成状况
   * 
   * @param request 任务状态请求参数类
   * @return
   */
  public TaskCompleteStatusResponse taskCompleteStatus(TaskCompleteStatusRequest request) {
    TaskCompleteStatusResponse response = new TaskCompleteStatusResponse(ResultType.SUCCESS);
    Map<String, String> courseIdMaps = request.getMCourseMap();
    List<String> completeDateList = Lists.newArrayList();
    String beginTime = DateUtil.SDF_YMD.format(new Timestamp(request.getBeginTime()));
    String endTime = DateUtil.SDF_YMD.format(new Timestamp(request.getEndTime()));
    response.setBeginExamTime(beginTime);
    response.setEndExamTime(endTime);
    IQueryParam param = new QueryParam();
    param.addInput("courseIdList", courseIdMaps.keySet());
    param.addInput("module", request.getModule());
    param.addInput("uid", request.getUid());
    param.addInput("createTimeBetween", beginTime);
    param.addInput("createTimeAnd", endTime);
    param.addOutputs(CommUtil.getAllField(UserTaskHitCompleteModel.class));
 
    Page<UserTaskHitCompleteModel> completeListPage = missionOperationFacade.listUserTaskHitComplete(param, null);
    if (null == completeListPage || completeListPage.getResult().isEmpty()) {
      return response;
    }
    Map<String, Integer> mapInfos = Maps.newHashMap();
    for (UserTaskHitCompleteModel ite : completeListPage.getResult()) {
      String doDate = DateUtil.SDF_YMD.format(ite.getCreateTime());
      if (mapInfos.containsKey(doDate)) {
        mapInfos.put(doDate, mapInfos.get(doDate) + 1);
      } else {
        mapInfos.put(doDate, 1);
      }
    }
    if (mapInfos.size() > 0) {
      for (Map.Entry<String, Integer> date : mapInfos.entrySet()) {
        if (date.getValue() >= courseIdMaps.size()) {
          completeDateList.add(date.getKey());
        }
      }
    }
    response.setTaskCompleteList(completeDateList);

    return response;
  }



  /**
   * 获取未完成任务数量
   * 
   * @param request 未完成任务数量请求
   * @return 未完成任务数量响应
   */
  public UnReachedTaskCountResponse unReachedTaskCount(UnReachedTaskCountRequest request) {
    UnReachedTaskCountResponse response = new UnReachedTaskCountResponse(ResultType.SUCCESS);
    // 1. 查询今日做任务情况,是否今天完成过任务,是否存在未完成任务
    Set<String> majorIdSet = Sets.newHashSet(request.getMajorId());
    UserTaskStatistic todayTaskStatistic =
        missionOperationFacade.get2DayUnReachTaskNum(request.getUid(), request.getModule(),
            majorIdSet, request.getMCourseMap().keySet());
    if (null == todayTaskStatistic
        || todayTaskStatistic.getTotalCount() == todayTaskStatistic.getCompleteCount()) {
      response.setUnReachedMissionCount(Integer.toString(0));
    } else if (todayTaskStatistic.getCompleteCount() == 0) {
      response.setUnReachedMissionCount(Integer.toString(request.getMCourseMap().size()));
    } else {
      Integer unreachCount =
          todayTaskStatistic.getTotalCount() - todayTaskStatistic.getCompleteCount();
      response.setUnReachedMissionCount(Integer.toString(unreachCount));
    }
    return response;
  }
}
