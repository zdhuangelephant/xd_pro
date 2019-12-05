package com.xiaodou.forum.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.forum.constant.Constant;
import com.xiaodou.forum.dao.forum.ForumCategoryModelDao;
import com.xiaodou.forum.model.forum.ForumCategoryModel;
import com.xiaodou.forum.util.CacheTimeProp;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;


/**
 * 话题分类列表缓存
 * 
 * @author bing.cheng
 * 
 */
@Service("forumCategoryCache")
public class ForumCategoryCache {

  /** forumCategoryModelDao 话题分类dao */
  @Resource
  ForumCategoryModelDao forumCategoryModelDao;

  /** initialDelay 初始调度任务时机 */
  private Integer initialDelay = 0;

  private static int forumCategoryCacheTime = Integer.parseInt(CacheTimeProp
      .getParams("forum.category.list.cache.time"));

  /** 刷新话题分类任务 */
  {
    SummerCommonScheduledExecutor.getExecutor().scheduleWithFixedDelay(new SummerScheduledTask() {
      @Override
      public void doMain() {
        if (null != forumCategoryModelDao) {
          List<ForumCategoryModel> list = forumCategoryModelDao.queryList(null, null);
          if (null == list || list.size() <= 0) {
            return;
          }
          // 缓存
          cacheForumCategoryList(list);
        }
      }

      @Override
      public void onException(Throwable t) {
        LoggerUtil.error("设置话题分类缓存异常.", t);
      }
    }, initialDelay, forumCategoryCacheTime, TimeUnit.SECONDS);
  }

  /**
   * 缓存话题分类列表
   * 
   * @param list
   */
  private void cacheForumCategoryList(List<ForumCategoryModel> list) {
    for (ForumCategoryModel catagory : list) {
      JedisUtil.addStringToJedis(Constant.FORUM + Constant.COMMON_DELIMITER
          + Constant.FORUM_CATEGORY + Constant.COMMON_DELIMITER + catagory.getId(),
          JSON.toJSONString(catagory), forumCategoryCacheTime * 5);
    }
    JedisUtil.addStringToJedis(
        Constant.FORUM + Constant.COMMON_DELIMITER + Constant.FORUM_CATEGORY,
        JSON.toJSONString(list), forumCategoryCacheTime * 5);
  }

  /**
   * 从缓存中获取话题分类列表
   */
  public String getForumCategoryList() {
    return JedisUtil.getStringFromJedis(Constant.FORUM + Constant.COMMON_DELIMITER
        + Constant.FORUM_CATEGORY);
  }

  public String getForumCategory(String catagoryId) {
    return JedisUtil.getStringFromJedis(Constant.FORUM + Constant.COMMON_DELIMITER
        + Constant.FORUM_CATEGORY + Constant.COMMON_DELIMITER + catagoryId);
  }

}
