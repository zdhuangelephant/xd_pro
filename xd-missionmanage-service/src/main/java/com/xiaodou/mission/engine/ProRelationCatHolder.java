package com.xiaodou.mission.engine;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * @name @see com.xiaodou.mission.engine.ProRelationCatHolder.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月28日
 * @description 产品关联专业容器
 * @version 1.0
 */
public class ProRelationCatHolder {
  /** PRO_RELATION_CAT_MAP 产品专业关联Map */
  private static Map<String, Set<String>> PRO_RELATION_CAT_MAP = Maps.newConcurrentMap();

  /**
   * 注册产品专业关联关系
   * 
   * @param productId 产品ID
   * @param catId 专业ID
   */
  public synchronized static void registRelation(Integer productId, Integer catId) {
    if (null == productId || null == catId) {
      LoggerUtil.error("产品ID与专业ID不能为空", new IllegalArgumentException());
      return;
    }
    Set<String> relationSet = PRO_RELATION_CAT_MAP.get(productId.toString());
    if (null == relationSet) {
      relationSet = Sets.newHashSet();
      PRO_RELATION_CAT_MAP.put(productId.toString(), relationSet);
    }
    relationSet.add(catId.toString());
  }

  /**
   * 获取产品关联专业列表
   * 
   * @param productId 产品ID
   * @return 关联专业列表
   */
  public static Set<String> getRelationCatSet(String productId) {
    if (null == PRO_RELATION_CAT_MAP || PRO_RELATION_CAT_MAP.isEmpty()) {
      return null;
    }
    return PRO_RELATION_CAT_MAP.get(productId);
  }
}
