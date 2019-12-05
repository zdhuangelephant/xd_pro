package com.xiaodou.mission.service.cache;

import java.util.Set;

import com.xiaodou.mission.service.cache.impl.UserMissionListCacheImpl.UserTaskStatistic;

/**
 * @name @see com.xiaodou.mission.service.cache.UserMissionListCache.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 用户任务缓存列表抽象接口
 * @version 1.0
 */
public interface UserMissionListCache {

  /**
   * 设置任务数量
   * 
   * @param uid 用户ID
   * @param module 模块号
   * @param major 专业ID
   * @param courseId 课程ID
   * @param taskStatistic 任务分析模型
   * @return 用户任务分析模型
   */
  public UserTaskStatistic setTasknum(String uid, String module, String courseId,
      UserTaskStatistic taskStatistic);

  /**
   * 获取任务数量
   * 
   * @param uid 用户ID
   * @param module 模块号
   * @param major 专业ID
   * @param courseIdSet 课程ID集合
   * @return 用户任务分析模型
   */
  public UserTaskStatistic getTaskNum(String uid, String module, Set<String> courseIdSet);

  /**
   * 设置今天的任务数量
   * 
   * @param uid 用户ID
   * @param module 模块号
   * @param major 专业ID
   * @param courseId 课程ID
   * @param taskStatistic 任务分析模型
   * @return 用户任务分析模型
   */
  public UserTaskStatistic set2DayTasknum(String uid, String module, String courseId,
      UserTaskStatistic taskStatistic);

  /**
   * 获取今天的任务数量
   * 
   * @param uid 用户ID
   * @param module 模块号
   * @param major 专业ID
   * @param courseIdSet 课程ID集合
   * @return 用户任务分析模型
   */
  public UserTaskStatistic get2DayTaskNum(String uid, String module, Set<String> courseIdSet);

  /**
   * 获取用户基础任务数量
   * 
   * @param uid 用户ID
   * @param module 模块号
   * @param major 专业ID
   * @return 基础任务数量
   */
  public Integer getUserBaseCount(String uid, String module, String major);

  /**
   * 刷新基础任务数量
   * 
   * @param uid 用户ID
   * @param module 模块号
   * @param major 专业ID
   * @param baseCount 基础任务数量
   * @param surplusDays 缓存有效期天数
   */
  public void refreshBaseCount(String uid, String module, String major, Integer baseCount,
      Integer surplusDays);

}
