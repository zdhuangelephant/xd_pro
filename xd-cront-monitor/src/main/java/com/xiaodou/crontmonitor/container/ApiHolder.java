package com.xiaodou.crontmonitor.container;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.autotest.core.model.Api;
import com.xiaodou.common.util.StringUtils;

/**
 * @name @see com.xiaodou.crontmonitor.container.ApiHolder.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年11月30日
 * @description Api容器
 * @version 1.0
 */
public class ApiHolder {

  private final static Map<String, Api> API_CACHE = Maps.newHashMap();

  /**
   * 插入/更新 配置
   * 
   * @param configId 配置ID
   * @param entity 配置实体
   */
  public final static void pushApi(Api entity) {
    if (null == entity || StringUtils.isBlank(entity.getUniqueId())) {
      return;
    }
    API_CACHE.put(entity.getUniqueId(), entity);
  }

  /**
   * 获取配置实体
   * 
   * @param uniqueId 配置ID
   * @return 配置实体
   */
  public final static Api getApi(String uniqueId) {
    return API_CACHE.get(uniqueId);
  }

  public final static Set<String> getAllApiUniqueId() {
    return Sets.newHashSet(API_CACHE.keySet());
  }

}
