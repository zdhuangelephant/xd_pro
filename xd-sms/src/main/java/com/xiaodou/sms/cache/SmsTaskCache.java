package com.xiaodou.sms.cache;

import java.util.Map;

import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.sms.utils.CacheTimeProp;

/**
 * 短信查重缓存
 * 
 * @author wuyunkuo
 * 
 */
public class SmsTaskCache {

  // 缓存失效时间，sms.task.list.cache.time在properties文件中配置
  private static int smsTaskListCacheTime = Integer.parseInt(CacheTimeProp
      .getParams("sms.task.list.cache.time"));

  /**
   * 将模板生成的短信转化成MD5串存在缓存中
   * 
   * @param map
   */
  public static void cacheSmsTaskList(Map<String, Object> map) {

    if (null == map || map.size() == 0) return;
    try {
      JedisUtil.addStringToJedis(CommUtil.HEXAndMd5((String) map.get("")), null,
          smsTaskListCacheTime * 60 * 60);
    } catch (Exception e) {
      LoggerUtil.error(e.getMessage(), e);
    }
  }

  /**
   * 删除存中的数据
   * 
   * @param map
   */
  public static boolean deleteSmsTaskList1(Map<String, Object> map) {
    String key = null;
    try {
      key = CommUtil.HEXAndMd5((String) map.get(""));
    } catch (Exception e) {
      LoggerUtil.error(e.getMessage(), e);
    }
    return 1 == JedisUtil.delKeyFromJedis(key);
  }

  /**
   * 判断缓存中是否存在Key，用于查重
   * 
   * @param key
   */
  public static void existKey(Map<String, Object> map) {
    boolean exist = false;
    try {
      exist = JedisUtil.existKey(CommUtil.HEXAndMd5((String) map.get("")));
    } catch (Exception e) {
      LoggerUtil.error(e.getMessage(), e);
    }
    if (exist)
      // 有重复，直接返回放弃数据
      return;
    else
      // 无重复，存入缓存，并把数据存入任务表
      cacheSmsTaskList(map);
  }

}
