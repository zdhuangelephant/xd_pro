package com.xiaodou.dashboard.cache;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.dashboard.constant.Constant;
import com.xiaodou.dashboard.vo.alarm.SendInfoVo;
/**
 * 
 * 报警信息缓存方法类
 * 
 * @author weirong.li
 * @version 1.0
 * @since JDK1.7
 */
public class AlarmDescCache {
	  /**
	   * 
	   * 缓存报警信息
	   * 
	   * @param pojo
	   * @throws Exception
	   */
	  public static void addAlarmDescToRedis(SendInfoVo pojo) throws Exception {
	    String key =
	        Constant.ALARM_DESC + pojo.getPojo().getEventModule() + Constant.COMMON_DELIMITER
	        +pojo.getPojo().getEventName()+ Constant.COMMON_DELIMITER+ pojo.getEvent().getMessage();
	    JedisUtil.addStringToJedis(key, JSON.toJSONString(pojo), 5 * 60);
	  }

	  /**
	   * 
	   * 从缓存获取对象
	   * 
	   * @param pojo
	   * @return
	   */
	  public static SendInfoVo getAlarmDescFromCache(SendInfoVo pojo) {
		  String key =
			        Constant.ALARM_DESC + pojo.getPojo().getEventModule() + Constant.COMMON_DELIMITER
			        +pojo.getPojo().getEventName()+ Constant.COMMON_DELIMITER+ pojo.getEvent().getMessage();
	    String str = JedisUtil.getStringFromJedis(key);
	    SendInfoVo model = null;
	    if (null != str) {
	      model = JSON.parseObject(str, SendInfoVo.class);
	    }
	    return model;
	  }

	  /**
	   * 
	   * 删除code
	   * 
	   * @param pojo
	   * @return
	   */
	  public static boolean deleteAlarmDesc(SendInfoVo pojo) {
		  String key =
			        Constant.ALARM_DESC + pojo.getPojo().getEventModule() + Constant.COMMON_DELIMITER
			        +pojo.getPojo().getEventName()+ Constant.COMMON_DELIMITER+ pojo.getEvent().getMessage();
		  return 1 == JedisUtil.delKeyFromJedis(key);
	  }
}
