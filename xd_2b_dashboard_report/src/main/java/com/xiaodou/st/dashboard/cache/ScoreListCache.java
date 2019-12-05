package com.xiaodou.st.dashboard.cache;


import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.st.dashboard.constants.CacheConstants;
import com.xiaodou.st.dashboard.domain.score.ScoreDO;

public class ScoreListCache {
  /**
   * 添加至缓存
   * 
   * @return void
   */
  public static void addScoreListToCache(List<ScoreDO> list) {
    try {
      String key = CacheConstants.SCORE_LIST;
      Integer cacheTime = 48 * 60 * 60;
      JedisUtil.addStringToJedis(key, JSON.toJSONString(list), cacheTime);
    } catch (Exception e) {
      LoggerUtil.error("添加至缓存", e);
    }
  }

  /**
   * 删除缓存信息
   * 
   * @return void
   */
  public static boolean deleteScoreListFromCache() {
    try {
      String key = CacheConstants.SCORE_LIST;
      Long delCount = JedisUtil.delKeyFromJedis(key);
      return null != delCount && 1 == delCount;
    } catch (Exception e) {
      LoggerUtil.error("删除缓存信息", e);
      return false;
    }
  }

  /**
   * 从缓存中获取信息
   * 
   * @return UserModel
   */
  public static List<ScoreDO> getScoreListFromCache() {
    try {
      String key = CacheConstants.SCORE_LIST;
      List<ScoreDO> list = null;
      String str = JedisUtil.getStringFromJedis(key);
      if (null != str) {
        list = FastJsonUtil.fromJsons(str, new TypeReference<List<ScoreDO>>() {});
      }
      return list;
    } catch (Exception e) {
      LoggerUtil.error("从缓存中获取分数信息", e);
      return null;
    }
  }
}
