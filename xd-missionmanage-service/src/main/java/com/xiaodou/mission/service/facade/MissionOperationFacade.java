package com.xiaodou.mission.service.facade;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xiaodou.mission.domain.CascadeMissionRecordModel;
import com.xiaodou.mission.domain.MissionModel;
import com.xiaodou.mission.domain.ProductRelation;
import com.xiaodou.mission.domain.UserCollectDataModel;
import com.xiaodou.mission.domain.UserMissionRecordModel;
import com.xiaodou.mission.domain.UserTaskHitCompleteModel;
import com.xiaodou.mission.engine.event.BaseEvent;
import com.xiaodou.mission.service.cache.impl.UserMissionListCacheImpl.UserTaskStatistic;
import com.xiaodou.mission.vo.model.UserMissionList;
import com.xiaodou.mission.vo.request.TaskListRequest;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IJoinQueryParam;
import com.xiaodou.summer.dao.param.IQueryParam;

/**
 * @name @see com.xiaodou.mission.service.facade.MissionOperationFacade.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 任务操作数据门面抽象接口
 * @version 1.0
 */
public interface MissionOperationFacade {

  /**
   * 查询产品关系列表页
   * 
   * @param param 查询参数
   * @return 产品关系列表页
   */
  public Page<ProductRelation> queryProductRelationPage(IQueryParam param);

  /**
   * 新增任务模型
   * 
   * @param model 任务模型
   * @return 任务模型
   */
  public MissionModel insertMissionModel(MissionModel model);

  /**
   * 查询任务模型列表
   * 
   * @param model 任务模型条件
   * @return 任务模型列表
   */
  public List<MissionModel> queryMissionModelList(MissionModel model);

  /**
   * 联合专业信息查询任务模型列表
   * 
   * @param model 任务模型条件
   * @return 任务模型列表
   */
  public List<MissionModel> cascadeQueryMissionModelList(MissionModel model);

  /**
   * 查询联合任务模型列表
   * 
   * @param model 级联任务查询条件
   * @return 级联查询任务模型列表
   */
  public List<CascadeMissionRecordModel> queryCascadeMissionRecordModel(
      CascadeMissionRecordModel model);

  /**
   * 查询联合任务模型列表
   * 
   * @param joinQueryParam 联合查询条件
   * @return 级联查询任务模型列表
   */
  public List<CascadeMissionRecordModel> queryCascadeMissionRecordModel(
      IJoinQueryParam joinQueryParam);

  /**
   * 查询用户数据
   * 
   * @param event 事件
   * @param model 用户数据模型
   * @return 用户数据模型
   */
  public <T extends BaseEvent> UserCollectDataModel queryUserData(Class<T> event,
      UserCollectDataModel model);

  /**
   * 更新用户数据
   * 
   * @param model 用户数据模型
   * @return 更新结果
   */
  public Boolean updateUserData(UserCollectDataModel model);

  /**
   * 查询用户任务实例
   * 
   * @param model 用户任务数据条件
   * @return 用户任务实例列表
   */
  public List<UserMissionRecordModel> queryUserMissionRecordList(UserMissionRecordModel model);

  /**
   * 查询用户任务实例
   * 
   * @param model 查询条件Map
   * @return 用户任务实例列表
   */
  public List<UserMissionRecordModel> queryUserMissionRecordList(Map<String, Object> model);

  /**
   * 查询符合条件的任务实例数量
   * 
   * @param model 查询符合条件的任务实例数量
   * @return 任务实例数量
   */
  public Integer countUserMissionRecordList(Map<String, Object> model);

  /**
   * 插入用户任务实例
   * 
   * @param model 用户任务实例
   * @return 用户任务实例
   */
  public UserMissionRecordModel insertUserMissionRecord(UserMissionRecordModel model);

  /**
   * 更新用户任务实例
   * 
   * @param model 用户任务实例
   * @return 更新结果
   */
  public Boolean updateUserMissionRecord(UserMissionRecordModel model);

  /**
   * 刷新课程未完成任务数量
   * 
   * @param userId 用户ID
   * @param module 模块号
   * @param majorId 专业ID
   * @param courseIdSet 课程ID集合
   * @return 用户任务分析实例
   */
  public UserTaskStatistic refreshUnReachTaskNum(String userId, String module,
      Set<String> majorIdSet, Set<String> courseIdSet);

  /**
   * 刷新今日课程未完成任务数量
   * 
   * @param userId 用户ID
   * @param module 模块号
   * @param majorId 专业ID
   * @param courseIdSet 课程ID集合
   * @return 用户任务分析实例
   */
  public UserTaskStatistic refresh2DayUnReachTaskNum(String userId, String module,
      Set<String> majorIdSet, Set<String> courseIdSet);

  /**
   * 获取指定课程列表未完成任务数量
   * 
   * @param userId 用户ID
   * @param module 模块号
   * @param majorId 专业ID
   * @param courseIdSet 课程ID集合
   * @return 用户任务分析实例
   */
  public UserTaskStatistic getUnReachTaskNum(String userId, String module, Set<String> majorIdSet,
      Set<String> courseIdSet);

  /**
   * 获取指定课程列表今日未完成任务数量
   * 
   * @param userId 用户ID
   * @param module 模块号
   * @param majorId 专业ID
   * @param courseIdSet 课程ID集合
   * @return 用户任务分析实例
   */
  public UserTaskStatistic get2DayUnReachTaskNum(String userId, String module,
      Set<String> majorIdSet, Set<String> courseIdSet);

  /**
   * 获取用户的任务列表
   * 
   * @param request 任务列表请求
   * @return 用户任务列表
   */
  public UserMissionList getUserMissionList(TaskListRequest request);

  public UserTaskHitCompleteModel insertUserTaskHitComplete(UserTaskHitCompleteModel hitInfo);

  public Page<UserTaskHitCompleteModel> listUserTaskHitComplete(IQueryParam param, Page<UserTaskHitCompleteModel> page);

}
