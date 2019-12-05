package com.xiaodou.mission.service.cache.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.service.cache.UserMissionListCache;
import com.xiaodou.mission.service.facade.MissionOperationFacade;

/**
 * @name @see com.xiaodou.mission.service.cache.impl.UserMissionListCacheImpl.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 用户任务缓存列表实现类
 * @version 1.0
 */
@Service("userMissionListCache")
public class UserMissionListCacheImpl implements UserMissionListCache {

  /** STATISTIC_TASK_KEY 用户任务分析缓存KEY */
  private static final String STATISTIC_TASK_KEY = "xd:mission:taskcount:%s:%s";

  /** STATISTIC_2DAY_TASK_KEY 当日用户任务分析缓存KEY */
  private static final String STATISTIC_2DAY_TASK_KEY = "xd:mission:taskcount:today:%s:%s";

  /** TASK_BASE_COUNT 基础任务数量 */
  private static final String TASK_BASE_COUNT = "xd:mission:basecount:%s:%s:%s";

  @Resource
  MissionOperationFacade missionOperationFacade;

  @Override
  public UserTaskStatistic setTasknum(String uid, String module, String courseId,
      UserTaskStatistic taskStatistic) {
    if (StringUtils.isOrBlank(uid, module, courseId)) {
      return taskStatistic;
    }
    if (taskStatistic == null) {
      return taskStatistic;
    }
    JedisUtil.addHashMapToJedis(String.format(STATISTIC_TASK_KEY, module, uid), courseId,
        FastJsonUtil.toJson(taskStatistic), 86400);
    return taskStatistic;
  }

  @Override
  public UserTaskStatistic getTaskNum(String uid, String module, Set<String> courseIdSet) {
    return getTaskNum0(STATISTIC_TASK_KEY, uid, module, courseIdSet);
  }

  @Override
  public UserTaskStatistic set2DayTasknum(String uid, String module, String courseId,
      UserTaskStatistic taskStatistic) {
    if (StringUtils.isOrBlank(uid, module, courseId)) {
      return taskStatistic;
    }
    if (taskStatistic == null) {
      return taskStatistic;
    }
    int cacheTime = (int) (DateUtil.getTimesmorning(1) - System.currentTimeMillis());
    JedisUtil.addHashMapToJedis(String.format(STATISTIC_2DAY_TASK_KEY, module, uid), courseId,
        FastJsonUtil.toJson(taskStatistic), cacheTime);
    return taskStatistic;
  }

  @Override
  public UserTaskStatistic get2DayTaskNum(String uid, String module, Set<String> courseIdSet) {
    return getTaskNum0(STATISTIC_2DAY_TASK_KEY, uid, module, courseIdSet);
  }

  private UserTaskStatistic getTaskNum0(String cacheKey, String uid, String module,
      Set<String> courseIdSet) {
    if (StringUtils.isOrBlank(uid, module)) {
      throw new IllegalArgumentException("用户名与模块号不能为空");
    }
    if (null == courseIdSet || courseIdSet.isEmpty()) {
      throw new IllegalArgumentException("课程ID不能为空");
    }
    Map<String, String> allTaskCountMap =
        JedisUtil.getAllMapValueFromJedis(String.format(cacheKey, module, uid));
    if (null == allTaskCountMap || allTaskCountMap.isEmpty()) {
      return null;
    }
    UserTaskStatistic allUserTaskStatistic = new UserTaskStatistic();
    if (null != courseIdSet && !courseIdSet.isEmpty()) {
      for (String courseId : courseIdSet) {
        String sTaskStatistic = allTaskCountMap.get(courseId);
        if (StringUtils.isJsonBlank(sTaskStatistic)) {
          return null;
        }
        fetchTaskStatistic(allUserTaskStatistic,
            FastJsonUtil.fromJson(sTaskStatistic, UserTaskStatistic.class));
      }
    } else {
      for (String sTaskStatistic : allTaskCountMap.values()) {
        if (StringUtils.isJsonBlank(sTaskStatistic)) {
          return null;
        }
        fetchTaskStatistic(allUserTaskStatistic,
            FastJsonUtil.fromJson(sTaskStatistic, UserTaskStatistic.class));
      }
    }
    return allUserTaskStatistic;
  }

  private void fetchTaskStatistic(UserTaskStatistic allUserTaskStatistic,
      UserTaskStatistic userTaskStatistic) {
    if (null == allUserTaskStatistic) {
      return;
    }
    if (null == userTaskStatistic) {
      return;
    }
    if (null != userTaskStatistic.totalCount) {
      allUserTaskStatistic.totalCount += userTaskStatistic.totalCount;
    }
    if (null != userTaskStatistic.completeCount) {
      allUserTaskStatistic.completeCount += userTaskStatistic.completeCount;
    }
  }

  /**
   * @name @see com.xiaodou.mission.service.cache.impl.UserMissionListCacheImpl.java
   * @CopyRright (c) 2017 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年12月27日
   * @description 用户任务统计分析模型
   * @version 1.0
   */
  public static class UserTaskStatistic {
    private Integer totalCount = MissionConstant.INTEGER_ZERO;
    private Integer completeCount = MissionConstant.INTEGER_ZERO;

    public Integer getTotalCount() {
      return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
      this.totalCount = totalCount;
    }

    public Integer getCompleteCount() {
      return completeCount;
    }

    public void setCompleteCount(Integer completeCount) {
      this.completeCount = completeCount;
    }
  }

  @Override
  public Integer getUserBaseCount(String uid, String module, String major) {
    String cacheKey = String.format(TASK_BASE_COUNT, module, uid, major);
    String baseCount = JedisUtil.getStringFromJedis(cacheKey);
    if (StringUtils.isBlank(baseCount)) {
      baseCount = Integer.toString(1);
    }
    return Integer.parseInt(baseCount);
  }

  @Override
  public void refreshBaseCount(String uid, String module, String major, Integer baseCount,
      Integer surplusDays) {
    if (null == baseCount) {
      return;
    }
    String cacheKey = String.format(TASK_BASE_COUNT, module, uid, major);
    if (null == surplusDays) {
      surplusDays = 1; // 默认缓存一天
    }
    int cacheSecs = (int) TimeUnit.SECONDS.convert(surplusDays, TimeUnit.DAYS);
    JedisUtil.addStringToJedis(cacheKey, baseCount.toString(), cacheSecs);
  }

}
