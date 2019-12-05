package com.xiaodou.resources.cache.quesbk;

import java.util.List;

import com.xiaodou.resources.model.quesbk.QuesbkQuesType;

/**
 * @name @see com.xiaodou.service.cache.QuesbkCache.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月8日
 * @description 题库缓存service接口
 * @version 1.0
 */
public interface QuesbkCache {

  /**
   * 获取问题类型缓存
   * 
   * @return
   */
  public List<QuesbkQuesType> getQuesType();

}
