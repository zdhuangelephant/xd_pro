package com.xiaodou.st.dashboard.cache;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.st.dashboard.constants.CacheConstants;
import com.xiaodou.st.dashboard.domain.student.StudentDO;

public class StudentListCache {
  public static String getKey(String type , Integer adminId){
    String key = "";
    switch (type) {
      case "1":
        key = CacheConstants.STUDENT_LIST+CacheConstants.COMMON_DELIMITER +adminId;
        break;

      default:
        break;
    }
    return key;
  }
  /**
   * 添加至缓存
   * 
   * @return void
   */
  public static <T> void addListToCache(String type ,List<T> list,Integer adminId) {
    try {
      String key = getKey("1", adminId);
      Integer cacheTime = 48 * 60 * 60;
      JedisUtil.addStringToJedis(key, JSON.toJSONString(list), cacheTime);
    } catch (Exception e) {
      LoggerUtil.error("添加至缓存", e);
    }
  }
public static void main(String[] args) {
  String key = getKey("1", 1);
  Integer cacheTime = 48 * 60 * 60;
  System.out.println(key);
  JedisUtil.addStringToJedis(key, "123aaa", cacheTime);
}
  /**
   * 删除缓存信息
   * 
   * @return void
   */
  public static boolean deleteStudentListFromCache(Integer adminId) {
    try {
      String key = CacheConstants.STUDENT_LIST+CacheConstants.COMMON_DELIMITER +adminId;
      Long delCount = JedisUtil.delKeyFromJedis(key);
      return null != delCount && 1 == delCount;
    } catch (Exception e) {
      LoggerUtil.error("删除缓存信息", e);
      return false;
    }
  }

  /**
   * 从缓存中获取信息
   * 
   * @return UserModel
   */
  public static List<StudentDO> getStudentListFromCache() {
    try {
      String key = CacheConstants.STUDENT_LIST;
      List<StudentDO> list = null;
      String str = JedisUtil.getStringFromJedis(key);
      if (null != str) {
        list = FastJsonUtil.fromJsons(str, new TypeReference<List<StudentDO>>() {});
      }
      return list;
    } catch (Exception e) {
      LoggerUtil.error("从缓存中获取分数信息", e);
      return null;
    }
  }
}
