package com.xiaodou.resources.cache.quesbk;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.resources.cache.quesbk.impl.QuesTypeCache;
import com.xiaodou.resources.model.quesbk.QuesbkQuesType;

/**
 * @name @see com.xiaodou.service.cache.QuesbkCacheImpl.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月8日
 * @description 题库缓存service实现
 * @version 1.0
 */
@Service("quesbkCache")
public class QuesbkCacheImpl implements QuesbkCache {

  @Resource
  QuesTypeCache quesTypeCache;

  @Override
  public List<QuesbkQuesType> getQuesType() {
    return quesTypeCache.getQuesType();
  }

}
