package com.xiaodou.ucenter.cache;


import com.alibaba.fastjson.JSON;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ucenter.constant.Constant;
import com.xiaodou.ucenter.model.UserModel;
import com.xiaodou.ucenter.porp.UcenterSwitch;

public class UserInfoByTokenCache {
  /**
   * 添加至缓存
   * 
   * @return void
   */
  public static void addUserInfoToCache(UserModel model, String token,String module) {
    String key = Constant.LOGIN_TOKEN + token +module;
    if (null == model) return;
    String cacheTime =
        UcenterSwitch.getParams(String.format("xd.ucenter.token.%s.cachetime", model.getModule()));
    if (StringUtils.isBlank(cacheTime)) cacheTime = "0";
    JedisUtil.addStringToJedis(key, JSON.toJSONString(model), Integer.valueOf(cacheTime));
  }

  /**
   * 删除缓存信息
   * 
   * @return void
   */
  public static boolean deleteUserInfoFromCache(String token,String module) {
    String key = Constant.LOGIN_TOKEN + token+module;
    Long delCount = JedisUtil.delKeyFromJedis(key);
    return null != delCount && 1 == delCount;
  }

  /**
   * 从缓存中获取用户信息
   * 
   * @return UserModel
   */
  public static UserModel getUserInfoFromCache(String token, String module) {
    String key = Constant.LOGIN_TOKEN + token + module ;
    UserModel model = null;
    String str = JedisUtil.getStringFromJedis(key);
    if (null != str) {
      model = JSON.parseObject(str, UserModel.class);
    }
    return model;
  }
}
