package com.xiaodou.userCenter.cache;

import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.userCenter.common.Constant;
import com.xiaodou.userCenter.model.alarm.AlarmRecordModel;
import com.xiaodou.userCenter.prop.UcenterSwitch;

public class UserLoginInfoCache {

  /**
   * 添加缓存
   * 
   * @param key 1、userId 2、deviceId
   */
  public static void saveAlarmRecordCache(String key, AlarmRecordModel model) {
    try {
      key = Constant.LOGIN_INFO + key;
      String cacheTime = UcenterSwitch.getParams("xd.ucenter.login.info.cachetime");
      if (StringUtils.isBlank(cacheTime)) cacheTime = "86400";
      JedisUtil.addStringToJedis(key, FastJsonUtil.toJson(model), Integer.valueOf(cacheTime));
    } catch (Exception e) {
      LoggerUtil.error("登录日志添加至缓存失败", e);
    }
  }

  /**
   * 刪除缓存
   */

  public static boolean removeAlarmRecordCache(String key) {
    try {
      if (StringUtils.isBlank(key)) return true;
      key = Constant.LOGIN_INFO + key;
      Long delCount = JedisUtil.delKeyFromJedis(key);
      return null != delCount && 1 == delCount;
    } catch (Exception e) {
      LoggerUtil.error("登录日志添加至缓存失败", e);
      return false;
    }
  }

  /**
   * 从缓存中获取用户信息
   * 
   * @return UserModel
   */
  public static AlarmRecordModel getAlarmRecordCache(String key) {
    try {
      key = Constant.LOGIN_INFO + key;
      String cacheJson = JedisUtil.getStringFromJedis(key);
      AlarmRecordModel model = null;
      if (StringUtils.isJsonNotBlank(cacheJson))
        model = FastJsonUtil.fromJson(cacheJson, AlarmRecordModel.class);
      return model;
    } catch (Exception e) {
      LoggerUtil.error("从缓存中获取用户信息", e);
      return null;
    }
  }
}
