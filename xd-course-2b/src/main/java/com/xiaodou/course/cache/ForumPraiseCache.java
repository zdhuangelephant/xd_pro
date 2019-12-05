package com.xiaodou.course.cache;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.course.constant.Constant;
import com.xiaodou.course.model.forum.ForumPraiseModel;

public class ForumPraiseCache {
  private static int  forumPraiseCacheTime = 3;
  /**
   * 根据话题id,用户id存入缓存中
   */
  public static void addForumPraiseCache(ForumPraiseModel praise, String forumId, String commentId, String userId){
    if(StringUtils.isBlank(forumId) || StringUtils.isBlank(userId) || StringUtils.isBlank(commentId) || null == praise){
      return;
    }
    JedisUtil.addStringToJedis(Constant.FORUM  + forumId + Constant.COMMON_DELIMITER
      + Constant.FORUM_COMMENT_PRAISE + commentId + Constant.COMMON_DELIMITER
      + Constant.FORUM_USERID + userId, JSON.toJSONString(praise), forumPraiseCacheTime);
  }
  /**
   * 从缓存中获取
   */
  public static ForumPraiseModel getForumPraiseFromCache(String forumId, String commentId, String userId){
    String key = Constant.FORUM  + forumId + Constant.COMMON_DELIMITER
        + Constant.FORUM_COMMENT_PRAISE + commentId + Constant.COMMON_DELIMITER
        + Constant.FORUM_USERID + userId;
    ForumPraiseModel praise = null;
    String str = JedisUtil.getStringFromJedis(key);
    if(null != str){
      praise = JSON.parseObject(str,ForumPraiseModel.class);
    }
    return praise;
  }
}
