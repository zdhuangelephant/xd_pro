package com.xiaodou.mission.service.facade;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.debug.PrintCostUtil;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.dao.CascadeMissionRecordModelDao;
import com.xiaodou.mission.dao.MissionModelDao;
import com.xiaodou.mission.dao.ProductRelationDao;
import com.xiaodou.mission.dao.UserCollectDataDetailModelDao;
import com.xiaodou.mission.dao.UserCollectDataModelDao;
import com.xiaodou.mission.dao.UserMissionRecordModelDao;
import com.xiaodou.mission.dao.UserTaskHitCompleteDao;
import com.xiaodou.mission.domain.CascadeMissionRecordModel;
import com.xiaodou.mission.domain.MissionModel;
import com.xiaodou.mission.domain.ProductRelation;
import com.xiaodou.mission.domain.UserCollectDataDetailModel;
import com.xiaodou.mission.domain.UserCollectDataModel;
import com.xiaodou.mission.domain.UserMissionRecordModel;
import com.xiaodou.mission.domain.UserTaskHitCompleteModel;
import com.xiaodou.mission.engine.MissionEnums.MissionType;
import com.xiaodou.mission.engine.MissionEnums.TaskType;
import com.xiaodou.mission.engine.event.BaseEvent;
import com.xiaodou.mission.service.QueueService;
import com.xiaodou.mission.service.cache.UserMissionListCache;
import com.xiaodou.mission.service.cache.impl.UserMissionListCacheImpl.UserTaskStatistic;
import com.xiaodou.mission.vo.model.CourseMissionHolder;
import com.xiaodou.mission.vo.model.CourseMissionHolder.MissionHolder;
import com.xiaodou.mission.vo.model.UserMissionList;
import com.xiaodou.mission.vo.model.UserMissionList.UserCourseMission;
import com.xiaodou.mission.vo.request.TaskListRequest;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IJoinQueryParam;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.JoinQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

import freemarker.template.SimpleDate;

/**
 * @name @see com.xiaodou.mission.service.facade.MissionOperationFacadeImpl.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 任务操作数据门面实现类
 * @version 1.0
 */
@Repository("missionOperationFacade")
public class MissionOperationFacadeImpl implements MissionOperationFacade {

  @Resource
  QueueService queueService;

  @Resource
  UserMissionListCache userMissionListCache;

  @Resource
  ProductRelationDao productRelationDao;

  @Resource
  MissionModelDao missionModelDao;

  @Resource
  UserCollectDataModelDao userCollectDataModelDao;

  @Resource
  UserCollectDataDetailModelDao userCollectDataDetailModelDao;

  @Resource
  UserMissionRecordModelDao userMissionRecordModelDao;

  @Resource
  CascadeMissionRecordModelDao cascadeMissionRecordModelDao;

  @Resource
  UserTaskHitCompleteDao userTaskHitCompleteDao;

  @Override
  public Page<ProductRelation> queryProductRelationPage(IQueryParam param) {
    return productRelationDao.findEntityListByCond(param, null);
  }

  @Override
  public MissionModel insertMissionModel(MissionModel model) {
    return missionModelDao.addEntity(model);
  }

  private UserCollectDataModel insertUserData(UserCollectDataModel model) {
    return userCollectDataModelDao.addEntity(model);
  }

  private UserCollectDataDetailModel insertUserDataDetail(UserCollectDataDetailModel model) {
    return userCollectDataDetailModelDao.addEntity(model);
  }

  @Override
  public <T extends BaseEvent> UserCollectDataModel queryUserData(Class<T> event,
      UserCollectDataModel model) {
    List<UserCollectDataModel> userCollectDataModelList =
        userCollectDataModelDao.select4Update(model);
    if (null != userCollectDataModelList && !userCollectDataModelList.isEmpty()) {
      UserCollectDataModel userCollectDataModel =
          userCollectDataModelList.get(MissionConstant.INTEGER_ZERO);
      userCollectDataModel.setTargetUserId(model.getTargetUserId());
      userCollectDataModel.setTollgateCourseId(model.getTollgateCourseId());
      if (StringUtils.isNotBlank(userCollectDataModel.getTargetUserId())) {
        UserCollectDataDetailModel pkInfo =
            queryUserDataDetail(userCollectDataModel.getPkInfoWithBuild());
        if (null == pkInfo) {
          pkInfo = insertUserDataDetail(userCollectDataModel.getPkInfoWithBuild());
        }
        userCollectDataModel.setPkInfo(pkInfo);
      }
      if (StringUtils.isNotBlank(userCollectDataModel.getTollgateCourseId())) {
        UserCollectDataDetailModel tollgateInfo =
            queryUserDataDetail(userCollectDataModel.getTollgateInfoWithBuild());
        if (null == tollgateInfo) {
          tollgateInfo = insertUserDataDetail(userCollectDataModel.getTollgateInfoWithBuild());
        }
        userCollectDataModel.setTollgateInfo(tollgateInfo);
      }
      Date lastUpdateDate = new SimpleDate(userCollectDataModel.getLastUpdateTime()).getAsDate();
      if (DateUtil.SDF_YMD.format(lastUpdateDate).compareTo(DateUtil.SDF_YMD.format(new Date())) < MissionConstant.INTEGER_ZERO) {
        if (DateUtil.SDF_YMD.format(lastUpdateDate)
            .compareTo(
                DateUtil.SDF_YMD.format(new Date(System.currentTimeMillis()
                    - TimeUnit.DAYS.toMillis(1)))) < 0) {
          userCollectDataModel.reset(false, event);
        } else {
          userCollectDataModel.reset(true, event);
        }
        updateUserData(userCollectDataModel);
      }
      return userCollectDataModel;
    }
    UserCollectDataModel insertUserData = insertUserData(model);
    if (null != model.getPkInfo()) {
      model.setPkInfo(insertUserDataDetail(insertUserData.getPkInfoWithBuild()));
    }
    if (null != model.getTollgateInfo()) {
      model.setTollgateInfo(insertUserDataDetail(insertUserData.getTollgateInfoWithBuild()));
    }
    return insertUserData;
  }

  private UserCollectDataDetailModel queryUserDataDetail(UserCollectDataDetailModel model) {
    IQueryParam param = new QueryParam();
    param.addInputs(CommUtil.getParams(model));
    Page<UserCollectDataDetailModel> page =
        userCollectDataDetailModelDao.findEntityListByCond(param, null);
    if (null != page && null != page.getResult() && !page.getResult().isEmpty()) {
      return page.getResult().get(0);
    }
    return null;
  }

  @Override
  public Boolean updateUserData(UserCollectDataModel model) {
    if (null != model.getPkInfo() && StringUtils.isNotBlank(model.getPkInfo().getId())) {
      updateUserDataDetail(model.getPkInfo());
    }
    if (null != model.getTollgateInfo() && StringUtils.isNotBlank(model.getTollgateInfo().getId())) {
      updateUserDataDetail(model.getTollgateInfo());
    }
    return userCollectDataModelDao.updateEntityById(model);
  }

  private Boolean updateUserDataDetail(UserCollectDataDetailModel model) {
    return userCollectDataDetailModelDao.updateEntityById(model);
  }

  @Override
  public List<UserMissionRecordModel> queryUserMissionRecordList(UserMissionRecordModel model) {
    Map<String, Object> input = Maps.newHashMap();
    CommUtil.transferFromVO2Map(input, model);
    return queryUserMissionRecordList(input);
  }

  @Override
  public List<UserMissionRecordModel> queryUserMissionRecordList(Map<String, Object> model) {
    IQueryParam param = new QueryParam();
    param.addInputs(model);
    Page<UserMissionRecordModel> page = userMissionRecordModelDao.findEntityListByCond(param, null);
    if (null != page) {
      return page.getResult();
    }
    return Lists.newArrayList();
  }

  @Override
  public UserMissionRecordModel insertUserMissionRecord(UserMissionRecordModel record) {
    return userMissionRecordModelDao.addEntity(record);
  }

  @Override
  public Boolean updateUserMissionRecord(UserMissionRecordModel record) {
    return userMissionRecordModelDao.updateEntityById(record);
  }

  @Override
  public List<MissionModel> queryMissionModelList(MissionModel model) {
    IQueryParam param = new QueryParam();
    param.addInputs(CommUtil.getParams(model));
    Page<MissionModel> page = missionModelDao.findEntityListByCond(param, null);
    if (null != page) {
      return page.getResult();
    }
    return Lists.newArrayList();
  }

  @Override
  public List<MissionModel> cascadeQueryMissionModelList(MissionModel model) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", model);
    Page<MissionModel> page = missionModelDao.cascadeQueryMissionModelList(cond, null);
    if (null != page) {
      return page.getResult();
    }
    return Lists.newArrayList();
  }

  @Override
  public List<CascadeMissionRecordModel> queryCascadeMissionRecordModel(
      CascadeMissionRecordModel model) {
    IQueryParam param = new QueryParam();
    param.addInputs(CommUtil.getParams(model));
    Page<CascadeMissionRecordModel> page =
        cascadeMissionRecordModelDao.findEntityListByCond(param, null);
    if (null != page) {
      return page.getResult();
    }
    return Lists.newArrayList();
  }

  @Override
  public List<CascadeMissionRecordModel> queryCascadeMissionRecordModel(IJoinQueryParam param) {
    Page<CascadeMissionRecordModel> page =
        cascadeMissionRecordModelDao.findEntityListByCond(param, null);
    if (null != page) {
      return page.getResult();
    }
    return Lists.newArrayList();
  }

  @Override
  public Integer countUserMissionRecordList(Map<String, Object> model) {
    IQueryParam param = new QueryParam();
    param.addInputs(model);
    Page<UserMissionRecordModel> page = new Page<>(1);
    page = userMissionRecordModelDao.findEntityListByCond(param, page);
    if (null != page) {
      return (int) page.getTotalCount();
    }
    return MissionConstant.INTEGER_ZERO;
  }

  @Override
  public UserTaskStatistic refreshUnReachTaskNum(String userId, String module,
      Set<String> majorIdSet, Set<String> courseIdSet) {
    UserTaskStatistic userTaskStatistic = new UserTaskStatistic();
    Map<String, UserTaskStatistic> taskStatisticMap =
        queryUserTaskStatistic(userId, module, null, majorIdSet, courseIdSet);
    if (null == taskStatisticMap || taskStatisticMap.isEmpty()) {
      return userTaskStatistic;
    }
    for (Entry<String, UserTaskStatistic> entity : taskStatisticMap.entrySet()) {
      userMissionListCache.setTasknum(userId, module, entity.getKey(), entity.getValue());
      userTaskStatistic.setTotalCount(userTaskStatistic.getTotalCount()
          + entity.getValue().getTotalCount());
      userTaskStatistic.setCompleteCount(userTaskStatistic.getCompleteCount()
          + entity.getValue().getCompleteCount());
    }
    return userTaskStatistic;
  }

  @Override
  public UserTaskStatistic refresh2DayUnReachTaskNum(String userId, String module,
      Set<String> majorIdSet, Set<String> courseIdSet) {
    UserTaskStatistic userTaskStatistic = new UserTaskStatistic();
    Map<String, UserTaskStatistic> taskStatisticMap =
        queryUserTaskStatistic(userId, module, new Timestamp(System.currentTimeMillis()),
            majorIdSet, courseIdSet);
    if (null == taskStatisticMap || taskStatisticMap.isEmpty()) {
      return userTaskStatistic;
    }
    for (Entry<String, UserTaskStatistic> entity : taskStatisticMap.entrySet()) {
      userMissionListCache.set2DayTasknum(userId, module, entity.getKey(), entity.getValue());
      userTaskStatistic.setTotalCount(userTaskStatistic.getTotalCount()
          + entity.getValue().getTotalCount());
      userTaskStatistic.setCompleteCount(userTaskStatistic.getCompleteCount()
          + entity.getValue().getCompleteCount());
    }
    return userTaskStatistic;
  }

  @Override
  public UserTaskStatistic getUnReachTaskNum(String userId, String module, Set<String> majorIdSet,
      Set<String> courseIdSet) {
    UserTaskStatistic taskStatistic = userMissionListCache.getTaskNum(userId, module, courseIdSet);
    if (null == taskStatistic) {
      taskStatistic = refreshUnReachTaskNum(userId, module, majorIdSet, courseIdSet);
    }
    return taskStatistic;
  }

  @Override
  public UserTaskStatistic get2DayUnReachTaskNum(String userId, String module,
      Set<String> majorIdSet, Set<String> courseIdSet) {
    UserTaskStatistic taskStatistic = userMissionListCache.getTaskNum(userId, module, courseIdSet);
    if (null == taskStatistic) {
      taskStatistic = refresh2DayUnReachTaskNum(userId, module, majorIdSet, courseIdSet);
    }
    return taskStatistic;
  }

  @Override
  public UserMissionList getUserMissionList(TaskListRequest request) {
    Map<String, String> mCourseMap = Maps.newHashMap(request.getMCourseMap());
    mCourseMap.put(MissionConstant.COMMON_COURSE_ID, StringUtils.EMPTY);
    IJoinQueryParam param = new JoinQueryParam();
    List<Integer> recordStatusList = Lists.newArrayList();
    recordStatusList.add(MissionConstant.RECORD_STATUS_AUTO);
    recordStatusList.add(MissionConstant.RECORD_STATUS_NORMAL);
    param.addJoin("recordStatusList", recordStatusList);
    param.addJoin("userId", request.getUid());
    List<String> moduleList = Lists.newArrayList();
    moduleList.add(MissionConstant.COMMON_MODULE);
    moduleList.add(request.getModule());
    param.addInput("moduleList", moduleList);
    List<String> majorIdList = Lists.newArrayList();
    majorIdList.add(request.getMajorId());
    majorIdList.add(MissionConstant.COMMON_MAJOR_ID);
    param.addInput("majorIdList", majorIdList);
    param.addInput("missionType", MissionType.task.toString());
    param.addInput("missionState", MissionConstant.MISSON_STATE_INUSE);
    List<CascadeMissionRecordModel> missionList = queryCascadeMissionRecordModel(param);
    PrintCostUtil.printCost("queryCascadeMissionRecordModel");
    if (null == missionList || missionList.isEmpty()) {
      return null;
    }
    List<CascadeMissionRecordModel> casecadeMissionList = Lists.newArrayList();
    for (CascadeMissionRecordModel mission : missionList) {
      if (null == mission) {
        continue;
      }
      if (!TaskType.dynamic.name().equals(mission.getTaskType())
          && !(MissionConstant.TASK_STATUS_UNREACH == mission.getIsReached())
          && null != mission.getFinishTime()
          && mission.getFinishTime().before(new Timestamp(DateUtil.getTimesmorning(0)))) {
        continue;
      }
      if (!TaskType.standard.name().equals(mission.getTaskType())) {
        mission.setNeedBuildRecord(Boolean.FALSE);
      }
      if (MissionConstant.COMMON_MAJOR_ID.equals(mission.getMajorId())
          || mCourseMap.containsKey(mission.getCourseId())) {
        casecadeMissionList.add(mission);
      }
    }
    UserMissionList userMissionList = processMissionList(request, casecadeMissionList);
    PrintCostUtil.printCost("processMissionList");
    return userMissionList;
  }

  private UserMissionList processMissionList(TaskListRequest request,
      List<CascadeMissionRecordModel> missionList) {
    UserMissionList userMissionList = new UserMissionList();
    CourseMissionHolder courseMissionHolder = new CourseMissionHolder();
    for (CascadeMissionRecordModel missionModel : missionList) {
      missionModel.init(request);
      courseMissionHolder.pushMission(missionModel);
    }
    for (String key : courseMissionHolder.getCourseIdSet()) {
      MissionHolder courseMission = courseMissionHolder.getCourseMission(key);
      UserCourseMission userCourseMission = new UserCourseMission();
      userCourseMission.setCourseId(key);
      if (MissionConstant.COMMON_COURSE_ID.equals(key)) {
        userCourseMission.getMissionList().addAll(
            getStaticCourseMissionList(courseMission.getStandardMission()));
      } else {
        userCourseMission.getMissionList().addAll(
            getStandardCourseMissionList(key, request, courseMission.getStandardMission(),
                request.getSurplusDays()));
      }
      userCourseMission.getMissionList().addAll(
          getDynamicCourseMissionList(courseMission.getDynamicMission()));
      userMissionList.getCourseMissionList().add(userCourseMission);
    }
    queueService.addMissionRecord(userMissionList);
    return userMissionList;
  }

  private List<CascadeMissionRecordModel> getStandardCourseMissionList(String courseId,
      TaskListRequest request, List<CascadeMissionRecordModel> courseMissionList,
      Integer surplusDays) {
    Integer baseCount =
        userMissionListCache.getUserBaseCount(request.getUid(), request.getModule(), courseId);
    Integer missionCount = getMissionCount(baseCount, courseMissionList.size(), surplusDays);
    userMissionListCache.refreshBaseCount(request.getUid(), request.getModule(), courseId,
        missionCount, surplusDays);
    List<CascadeMissionRecordModel> result = Lists.newArrayList();
    Collections.sort(courseMissionList, new Comparator<CascadeMissionRecordModel>() {
      @Override
      public int compare(CascadeMissionRecordModel o1, CascadeMissionRecordModel o2) {
        if (o1.getMissionOrder() == -1) {
          return 1;
        } else if (o2.getMissionOrder() == -1) {
          return -1;
        } else {
          return (int) (o1.getMissionOrder() - o2.getMissionOrder());
        }
      }
    });
    for (CascadeMissionRecordModel model : courseMissionList) {
      if (result.size() >= missionCount) {
        break;
      }
      if (!model.currUnValid()) {
        result.add(model);
      }
    }
    return result;
  }

  private List<CascadeMissionRecordModel> getStaticCourseMissionList(
      List<CascadeMissionRecordModel> courseMissionList) {
    List<CascadeMissionRecordModel> result = Lists.newArrayList();
    for (CascadeMissionRecordModel model : courseMissionList) {
      if (!model.currUnValid()) {
        result.add(model);
      }
    }
    return result;
  }

  private List<CascadeMissionRecordModel> getDynamicCourseMissionList(
      List<CascadeMissionRecordModel> courseMissionList) {
    List<CascadeMissionRecordModel> result = Lists.newArrayList();
    for (CascadeMissionRecordModel model : courseMissionList) {
      model.setNeedBuildRecord(false);
      result.add(model);
    }
    return result;
  }

  private static Integer getMissionCount(int baseCount, int size, Integer surplusDays) {
    if (surplusDays <= 0) {
      surplusDays = 1;
    }
    if (baseCount == 0) {
      baseCount = 1;
    }
    if (surplusDays == 0) {
      return size;
    }
    if (size <= baseCount * surplusDays) {
      return baseCount;
    }
    return getMissionCount(2 * baseCount, size, surplusDays);
  }

  private Map<String, UserTaskStatistic> queryUserTaskStatistic(String userId, String module,
      Timestamp targetDate, Set<String> majorIdSet, Set<String> courseIdSet) {
    if (StringUtils.isOrBlank(userId, module)) {
      throw new IllegalArgumentException("用户名与模块号不能为空");
    }
    if (null == courseIdSet || courseIdSet.isEmpty()) {
      throw new IllegalArgumentException("课程ID不能为空");
    }
    IJoinQueryParam param = new JoinQueryParam();
    List<Integer> recordStatusList = Lists.newArrayList();
    recordStatusList.add(MissionConstant.RECORD_STATUS_NORMAL);
    recordStatusList.add(MissionConstant.RECORD_STATUS_BACKUP);
    param.addJoin("recordStatusList", recordStatusList);
    param.addJoin("userId", userId);
    List<String> moduleList = Lists.newArrayList();
    moduleList.add(module);
    moduleList.add(MissionConstant.COMMON_MODULE);
    param.addInput("moduleList", moduleList);
    if (null != majorIdSet && !majorIdSet.isEmpty()) {
      List<String> majorIdList = Lists.newArrayList(majorIdSet);
      param.addInput("majorIdList", majorIdList);
    }
    List<String> courseIdList = Lists.newArrayList(courseIdSet);
    courseIdList.add(MissionConstant.COMMON_COURSE_ID);
    param.addInput("courseIdList", courseIdList);
    param.addInput("missionType", MissionType.task.toString());
    param.addInput("taskType", TaskType.standard.toString());
    param.addInput("missionState", MissionConstant.MISSON_STATE_INUSE);
    // if (null != deadLine) {
    // param.addInput("deadLineLower", deadLine);
    // }
    List<CascadeMissionRecordModel> missionRecordList = queryCascadeMissionRecordModel(param);
    if (null != missionRecordList && !missionRecordList.isEmpty()) {
      Map<String, UserTaskStatistic> courseTaskStatisticMap = Maps.newHashMap();
      DateFormat SDF_YMD = new SimpleDateFormat("yyyy-MM-dd");
      for (CascadeMissionRecordModel casecadeMissionRecord : missionRecordList) {
        UserTaskStatistic courseTaskStatistic =
            courseTaskStatisticMap.get(casecadeMissionRecord.getCourseId());
        if (null == courseTaskStatistic) {
          courseTaskStatistic = new UserTaskStatistic();
          courseTaskStatisticMap.put(casecadeMissionRecord.getCourseId(), courseTaskStatistic);
        }
        if (targetDate == null) {
          courseTaskStatistic.setTotalCount(courseTaskStatistic.getTotalCount() + 1);
        } else if (MissionConstant.TASK_STATUS_UNREACH == casecadeMissionRecord.getIsReached()
            || (null != casecadeMissionRecord.getFinishTime() && SDF_YMD.format(
                casecadeMissionRecord.getFinishTime()).equals(SDF_YMD.format(targetDate)))) {
          courseTaskStatistic.setTotalCount(courseTaskStatistic.getTotalCount() + 1);
        }
        if (casecadeMissionRecord.isComplete()) {
          if (targetDate == null
              || (null != casecadeMissionRecord.getFinishTime() && SDF_YMD.format(
                  casecadeMissionRecord.getFinishTime()).equals(SDF_YMD.format(targetDate)))) {
            courseTaskStatistic.setCompleteCount(courseTaskStatistic.getCompleteCount() + 1);
          }
        }
      }
      return courseTaskStatisticMap;
    }
    return null;
  }

  @Override
  public UserTaskHitCompleteModel insertUserTaskHitComplete(UserTaskHitCompleteModel hitInfo) {
    return userTaskHitCompleteDao.addEntity(hitInfo);
  }

  @Override
  public Page<UserTaskHitCompleteModel> listUserTaskHitComplete(IQueryParam param,
      Page<UserTaskHitCompleteModel> page) {
    return userTaskHitCompleteDao.findEntityListByCond(param, page);
  }

}
