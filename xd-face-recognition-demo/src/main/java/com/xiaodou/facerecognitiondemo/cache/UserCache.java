package com.xiaodou.facerecognitiondemo.cache;

import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.facerecognitiondemo.model.User;

public class UserCache {

  public static void addUser(User user) {
    if (StringUtils.isBlank(user.getUid(), user.getFaceId())) return;
    JedisUtil.addStringToJedis(user.getUid(), FastJsonUtil.toJson(user), 0);
  }

  public static User getUser(String uid) {
    if (StringUtils.isBlank(uid)) return null;
    String userInfo = JedisUtil.getStringFromJedis(uid);
    if (StringUtils.isJsonBlank(userInfo)) return null;
    return FastJsonUtil.fromJson(userInfo, User.class);
  }

}
