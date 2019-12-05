package com.xiaodou.common.service.checkcode.cache;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.service.checkcode.constant.Constant;
import com.xiaodou.common.service.checkcode.model.CheckCodeModel;

/**
 * 
 * 手机验证码缓存方法类
 * 
 * @author weirong.li
 * @version 1.0
 * @since JDK1.7
 */
public class CheckCodeCache {

  /**
   * 
   * 缓存验证码
   * 
   * @param model
   * @throws Exception
   */
  public static void addCodeToRedis(String checkCodeType, String phoneNum, String deviceId,
      String checkCode) throws Exception {
    String key = Constant.CHECK_CODE + checkCodeType + Constant.COMMON_DELIMITER + phoneNum;
    CheckCodeModel model = new CheckCodeModel();
    model.setPhoneNum(phoneNum);
    model.setDeviceId(deviceId);
    model.setCheckCodeType(checkCodeType);
    model.setCheckCode(checkCode);
    model.setCreateDate(new Date());
    JedisUtil.addStringToJedis(key, JSON.toJSONString(model), 5 * 60);
  }

  /**
   * 
   * 从缓存获取验证码
   * 
   * @param pojo
   * @return
   */
  public static CheckCodeModel getCheckCodeFromCache(String checkCodeType, String phoneNum) {
    String key = Constant.CHECK_CODE + checkCodeType + Constant.COMMON_DELIMITER + phoneNum;
    String str = JedisUtil.getStringFromJedis(key);
    CheckCodeModel model = null;
    if (null != str) {
      model = JSON.parseObject(str, CheckCodeModel.class);
    }
    return model;
  }

  /**
   * 
   * 删除code
   * 
   * @param checkCodeType
   * @param phoneNum
   * @return
   */
  public static boolean deleteCheckCode(String checkCodeType, String phoneNum) {
    String key = Constant.CHECK_CODE + checkCodeType + Constant.COMMON_DELIMITER + phoneNum;
    return 1 == JedisUtil.delKeyFromJedis(key);
  }


  /**
   * 
   * 缓存15条短信
   * 
   * @param phoneNum
   * @param list
   * @throws Exception
   */
  public static void cacheSmsAmount(String phoneNum, String code) throws Exception {
    String key = Constant.SMS_CODE + phoneNum;
    Calendar cal = new GregorianCalendar();
    cal.setTime(new Date());
    Long cacheTime = 86400l;
    cacheTime -= TimeUnit.SECONDS.convert(cal.get(Calendar.HOUR_OF_DAY), TimeUnit.HOURS);
    cacheTime -= TimeUnit.SECONDS.convert(cal.get(Calendar.MINUTE), TimeUnit.MINUTES);
    cacheTime -= cal.get(Calendar.SECOND);
    JedisUtil.addHashMapToJedis(key, code, code, cacheTime.intValue());
  }

  /**
   * 
   * 获取15条短信缓存List
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public static Map<String, String> getSmsAmountCache(String phoneNum) throws Exception {
    String key = Constant.SMS_CODE + phoneNum;
    Map<String, String> map = JedisUtil.getAllMapValueFromJedis(key);
    return map;
  }

}
