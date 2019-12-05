package com.xiaodou.userCenter.cache;


import com.alibaba.fastjson.JSON;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.userCenter.common.Constant;
import com.xiaodou.userCenter.module.selfTaught.response.StUserInfoResponse;
import com.xiaodou.userCenter.prop.UcenterSwitch;

public class UserInfoByTokenCache {
  /**
   * 添加至缓存
   * 
   * @return void
   */
  public static void addUserInfoToCache(StUserInfoResponse model, String token) {
    try {
      String key = Constant.LOGIN_TOKEN + token;
      if (null == model) return;
      String cacheTime =
          UcenterSwitch
              .getParams("xd.ucenter.token.cachetime");
      if (StringUtils.isBlank(cacheTime)) cacheTime = "0";
      JedisUtil.addStringToJedis(key, JSON.toJSONString(model), Integer.valueOf(cacheTime));
    } catch (Exception e) {
      LoggerUtil.error("添加至缓存", e);
    }
  }

  /**
   * 删除缓存信息
   * 
   * @return void
   */
  public static boolean deleteUserInfoFromCache(String token) {
    try {
      String key = Constant.LOGIN_TOKEN + token;
      Long delCount = JedisUtil.delKeyFromJedis(key);
      return null != delCount && 1 == delCount;
    } catch (Exception e) {
      LoggerUtil.error("删除缓存信息", e);
      return false;
    }
  }

  /**
   * 从缓存中获取用户信息
   * 
   * @return UserModel
   */
  public static StUserInfoResponse getUserInfoFromCache(String token) {
    try {
      String key = Constant.LOGIN_TOKEN + token;
      StUserInfoResponse model = null;
      String str = JedisUtil.getStringFromJedis(key);
      if (null != str) {
        model = JSON.parseObject(str, StUserInfoResponse.class);
      }
      return model;
    } catch (Exception e) {
      LoggerUtil.error("从缓存中获取用户信息", e);
      return null;
    }
  }
}
