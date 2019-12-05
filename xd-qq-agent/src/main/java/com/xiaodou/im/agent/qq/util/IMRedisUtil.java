package com.xiaodou.im.agent.qq.util;

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
  private static final String P_SESSION_ID = IM_QQ_PREFIX + "psessionId";

  private static final String MSG_ID = IM_QQ_PREFIX + "msgId";
  private static final String CLIENT_ID = IM_QQ_PREFIX + "clientId";
  private static final String FACE = IM_QQ_PREFIX + "face";

  private static final String IS_POLL = IM_QQ_PREFIX + "isPoll";
  private static final String IS_INIT = IM_QQ_PREFIX + "isInit";
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

  public static Integer getMsgId() {
    String msgId = JedisUtil.getStringFromJedis(MSG_ID);
    if (msgId != null) {
      return Integer.parseInt(msgId);
    }
    return null;
  }

  public static void setMsgId(int msgId) {
    JedisUtil.addStringToJedis(MSG_ID, String.valueOf(msgId), SECOND_PER_YEAR);
  }

  public static String getClientId() {
    return JedisUtil.getStringFromJedis(CLIENT_ID);
  }

  public static void setClientId(String clientId) {
    JedisUtil.addStringToJedis(CLIENT_ID, clientId, SECOND_PER_YEAR);
  }

  public static String getFace() {
    return JedisUtil.getStringFromJedis(FACE);
  }

  public static void setFace(String face) {
    JedisUtil.addStringToJedis(FACE, face, SECOND_PER_YEAR);
  }

  public static String getIsPoll() {
    return JedisUtil.getStringFromJedis(IS_POLL);
  }

  public static void setIsPoll(String flag) {
    JedisUtil.addStringToJedis(IS_POLL, flag, SECOND_PER_YEAR);
  }

  public static String getIsInit() {
    return JedisUtil.getStringFromJedis(IS_INIT);
  }

  public static void setIsInit(String flag) {
    JedisUtil.addStringToJedis(IS_INIT, flag, SECOND_PER_YEAR);
  }

  public static void removeAll(){
    JedisUtil.delKeyFromJedis(COOKIE);
    JedisUtil.delKeyFromJedis(UIN);
    JedisUtil.delKeyFromJedis(PT_WEB_QQ);
    JedisUtil.delKeyFromJedis(VF_WEB_QQ);
    JedisUtil.delKeyFromJedis(P_SESSION_ID);

    JedisUtil.delKeyFromJedis(MSG_ID);
    JedisUtil.delKeyFromJedis(CLIENT_ID);
    JedisUtil.delKeyFromJedis(FACE);

    JedisUtil.delKeyFromJedis(IS_POLL);
    JedisUtil.delKeyFromJedis(IS_INIT);
  }

}
