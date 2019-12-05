package com.xiaodou.facerecognitiondemo.cache;

import java.util.concurrent.atomic.AtomicBoolean;

import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.userCenter.request.UpTokenPojo;

public class UpTokenCache {

  /**
   * 上传凭证
   */
  public static final String UP_TOKEN = "uptoken:";

  private static AtomicBoolean lock = new AtomicBoolean(false);

  private static Long _deadLine = System.currentTimeMillis() / 1000;

  /**
   * 
   * 缓存验证码
   * 
   * @param model
   * @throws Exception
   */
  public static void setUpToken(UpTokenPojo pojo, String uptoken) throws Exception {
    if (lock.compareAndSet(false, true)) {
      if (null != pojo.getDeadline() && pojo.getDeadline() > _deadLine) {
        String key = UP_TOKEN + pojo.getScope();
        JedisUtil.addStringToJedis(key, uptoken, 50 * 60);
        _deadLine = pojo.getDeadline();
      }
      lock.compareAndSet(true, false);
    }
  }

  public static void releaseUpToken(UpTokenPojo pojo, String uptoken) throws Exception {
    if (lock.compareAndSet(false, true)) {
      String key = UP_TOKEN + pojo.getScope();
      JedisUtil.addStringToJedis(key, uptoken, 50 * 60);
      _deadLine = pojo.getDeadline();
      lock.compareAndSet(true, false);
    }
  }

  /**
   * 
   * 从缓存获取验证码
   * 
   * @param pojo
   * @return
   */
  public static String getUpToken(UpTokenPojo pojo) {
    String key = UP_TOKEN + pojo.getScope();
    return JedisUtil.getStringFromJedis(key);
  }

}
