package com.xiaodou.oms.util;

import com.xiaodou.common.cache.redis.JedisUtil;


/**
 * Date: 2014/12/23
 * Time: 14:53
 *
 * @author Tian.Dong
 */
public class IMRedisUtil {
  public static final String IM_QQ_PREFIX = "im:qq:";
  private static final String COOKIE = IM_QQ_PREFIX + "cookie";
  private static final String UIN = IM_QQ_PREFIX + "uin";
  private static final String PT_WEB_QQ = IM_QQ_PREFIX + "ptwebqq";
  private static final String VF_WEB_QQ = IM_QQ_PREFIX + "vfwebqq";
  private static final String P_SESSION_ID = IM_QQ_PREFIX + "psessionid";
  /**
   * 缓存时间
   */
  public static final int SECOND_PER_YEAR = 60 * 60 * 24 * 365;

  public static String getCookie() {
    return JedisUtil.getStringFromJedis(COOKIE);
  }

  public static void setCookie(String cookie) {
    JedisUtil.addStringToJedis(COOKIE, cookie, SECOND_PER_YEAR);
  }

  public static String getUin() {
    return JedisUtil.getStringFromJedis(UIN);
  }

  public static void setUin(String uin) {
    JedisUtil.addStringToJedis(UIN, uin, SECOND_PER_YEAR);
  }

  public static String getPtwebqq() {
    return JedisUtil.getStringFromJedis(PT_WEB_QQ);
  }

  public static void setPtwebqq(String pt) {
    JedisUtil.addStringToJedis(PT_WEB_QQ, pt, SECOND_PER_YEAR);
  }

  public static String getVfwebqq() {
    return JedisUtil.getStringFromJedis(VF_WEB_QQ);
  }

  public static void setVfwebqq(String vf) {
    JedisUtil.addStringToJedis(VF_WEB_QQ, vf, SECOND_PER_YEAR);
  }

  public static String getPsessionid() {
    return JedisUtil.getStringFromJedis(P_SESSION_ID);
  }

  public static void setPsessionid(String sessionId) {
    JedisUtil.addStringToJedis(P_SESSION_ID, sessionId, SECOND_PER_YEAR);
  }
}
