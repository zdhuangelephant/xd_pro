package com.xiaodou.manager.cache.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.cache.local.DynamicTimingLocalCache;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.dao.product.QuesbkQuesTypeDao;
import com.xiaodou.domain.product.QuesbkQuesType;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;
import com.xiaodou.util.CacheInfoProp;

/**
 * @name @see com.xiaodou.service.cache.impl.QuesTypeCache.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月8日
 * @description 题目类型缓存
 * @version 1.0
 */
@SuppressWarnings("unchecked")
@Service("quesTypeCache")
public class QuesTypeCache {

  @Resource
  QuesbkQuesTypeDao quesbkQuesTypeDao;

  /** initialDelay 初始调度任务时机 */
  private Integer initialDelay = 0;

  /** quesTypeDelay 问题类型刷新延迟 */
  private Integer quesTypeDelay = CacheInfoProp.getBaseCache().getPropertiesInt(
      QuesBaseConstant.QUES_TYPE_TASK_DELAY);

  /** 刷新问题类型任务 */
  {
    SummerCommonScheduledExecutor.getExecutor().scheduleWithFixedDelay(new SummerScheduledTask() {
      @Override
      public void doMain() {
        if (null != quesbkQuesTypeDao) {
          List<QuesbkQuesType> quesTypeLst = quesbkQuesTypeDao.selectQuesType();
          setQuesType(quesTypeLst);
        }
      }

      @Override
      public void onException(Throwable t) {
        LoggerUtil.error("设置问题类型缓存异常.", t);
      }
    }, initialDelay, quesTypeDelay, TimeUnit.SECONDS);
  }

  /**
   * 获取问题类型缓存
   * 
   * @param courseId 课程ID
   * @return 问题类型列表
   */
  public List<QuesbkQuesType> getQuesType() {
    Object quesTypeList = DynamicTimingLocalCache.getCache(QuesBaseConstant.QUES_TYPE_PREFIX);
    if (null != quesTypeList) {
      return (List<QuesbkQuesType>) quesTypeList;
    }
    return null;
  }

  /**
   * 设置问题类型缓存
   * 
   * @param quesTypeLst 问题类型列表
   */
  private void setQuesType(List<QuesbkQuesType> quesTypeLst) {
    DynamicTimingLocalCache
        .cache(QuesBaseConstant.QUES_TYPE_PREFIX, quesTypeLst, quesTypeDelay * 5);
  }

}
