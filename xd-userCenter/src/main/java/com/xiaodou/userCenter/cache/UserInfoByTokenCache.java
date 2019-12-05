package com.xiaodou.userCenter.cache;


import com.alibaba.fastjson.JSON;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.userCenter.common.Constant;
import com.xiaodou.userCenter.response.BaseUserModel;
import com.xiaodou.userCenter.response.UserModelResponse;
import com.xiaodou.userCenter.util.ModuleMappingWrapper;

public class UserInfoByTokenCache {
  /**
   * 添加至缓存
   * 
   * @return void
   */
  public static void addUserInfoToCache(UserModelResponse model, String token) {
    String key = Constant.LOGIN_TOKEN + token;
    if (null == model) {
      return;
    }
    JedisUtil.addStringToJedis(key, JSON.toJSONString(model), 0);
  }

  /**
   * 删除缓存信息
   * 
   * @return void
   */
  public static boolean deleteUserInfoFromCache(String token) {
    String key = Constant.LOGIN_TOKEN + token;
    return 1 == JedisUtil.delKeyFromJedis(key);
  }

  /**
   * 从缓存中获取用户信息
   * 
   * @return UserModel
   */
  public static BaseUserModel getUserInfoFromCache(String token) {
    String key = Constant.LOGIN_TOKEN + token;
    BaseUserModel model = null;
    String str = JedisUtil.getStringFromJedis(key);
    if (null != str) {
      model = JSON.parseObject(str, ModuleMappingWrapper.getWrapper().getModule().getResponse());
    }
    return model;
  }
}
