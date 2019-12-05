package com.xiaodou.resources.cache.quesbk.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.cache.local.DynamicTimingLocalCache;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.dao.quesbk.QuesbkQuesTypeMapper;
import com.xiaodou.resources.model.quesbk.QuesbkQuesType;
import com.xiaodou.resources.util.CacheInfoProp;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;

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
  QuesbkQuesTypeMapper quesbkQuesTypeMapper;

  /** initialDelay 初始调度任务时机 */
  private Integer initialDelay = 0;

  /** quesTypeDelay 问题类型刷新延迟 */
  private Integer quesTypeDelay = CacheInfoProp.getBaseCache().getPropertiesInt(QuesBaseConstant.QUES_TYPE_TASK_DELAY);

  /** 刷新问题类型任务 */
  {
    SummerCommonScheduledExecutor.getExecutor().scheduleWithFixedDelay(new SummerScheduledTask() {
      @Override
      public void doMain() {
        if(null != quesbkQuesTypeMapper) {
          List<QuesbkQuesType> quesTypeLst = quesbkQuesTypeMapper.selectQuesType();
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
