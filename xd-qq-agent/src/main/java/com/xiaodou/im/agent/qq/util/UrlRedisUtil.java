package com.xiaodou.im.agent.qq.util;

import com.xiaodou.common.cache.redis.JedisUtil;


/**
 * Date: 2014/12/30
 * Time: 14:06
 *
 * @author Tian.Dong
 */
public class UrlRedisUtil {
  public static final String IM_QQ_URL_PREFIX = "im:qq:url";

  public static String DISCUS_LIST_URL = IM_QQ_URL_PREFIX + "discusList";
  public static String GROUP_LIST_URL = IM_QQ_URL_PREFIX + "groupList";
  public static String SEND_GROUP_MSG_URL = IM_QQ_URL_PREFIX + "sendGroupMsg";
  public static String SEND_DISCUS_MSG_URL = IM_QQ_URL_PREFIX + "sendDiscusMsg";
  public static String SEND_BUDDY_MSG_URL = IM_QQ_URL_PREFIX + "sendBuddyMsg";
  public static String POLL_URL = IM_QQ_URL_PREFIX + "poll";
  public static String REFER_URL = IM_QQ_URL_PREFIX + "refer";

  /**
   * 缓存时间
   */
  public static final int SECOND_PER_YEAR = 60 * 60 * 24 * 365;


  public static String getDISCUS_LIST_URL() {
    return JedisUtil.getStringFromJedis(DISCUS_LIST_URL);
  }

  public static void setDISCUS_LIST_URL(String url) {
    JedisUtil.addStringToJedis(DISCUS_LIST_URL, url, SECOND_PER_YEAR);
  }

  public static String getGROUP_LIST_URL() {
    return JedisUtil.getStringFromJedis(GROUP_LIST_URL);
  }

  public static void setGROUP_LIST_URL(String url) {
    JedisUtil.addStringToJedis(GROUP_LIST_URL, url, SECOND_PER_YEAR);
  }

  public static String getSEND_GROUP_MSG_URL() {
    return JedisUtil.getStringFromJedis(SEND_GROUP_MSG_URL);
  }

  public static void setSEND_GROUP_MSG_URL(String url) {
    JedisUtil.addStringToJedis(SEND_GROUP_MSG_URL, url, SECOND_PER_YEAR);
  }

  public static String getSEND_DISCUS_MSG_URL() {
    return JedisUtil.getStringFromJedis(SEND_DISCUS_MSG_URL);
  }

  public static void setSEND_DISCUS_MSG_URL(String url) {
    JedisUtil.addStringToJedis(SEND_DISCUS_MSG_URL, url, SECOND_PER_YEAR);
  }

  public static String getSEND_BUDDY_MSG_URL() {
    return JedisUtil.getStringFromJedis(SEND_BUDDY_MSG_URL);
  }

  public static void setSEND_BUDDY_MSG_URL(String url) {
    JedisUtil.addStringToJedis(SEND_BUDDY_MSG_URL, url, SECOND_PER_YEAR);
  }

  public static String getPOLL_URL() {
    return JedisUtil.getStringFromJedis(POLL_URL);
  }

  public static void setPOLL_URL(String url) {
    JedisUtil.addStringToJedis(POLL_URL, url, SECOND_PER_YEAR);
  }

  public static String getREFER_URL() {
    return JedisUtil.getStringFromJedis(REFER_URL);
  }

  public static void setREFER_URL(String url) {
    JedisUtil.addStringToJedis(REFER_URL, url, SECOND_PER_YEAR);
  }
}
