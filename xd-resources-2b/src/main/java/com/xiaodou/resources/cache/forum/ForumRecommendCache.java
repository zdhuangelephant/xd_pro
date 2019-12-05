package com.xiaodou.resources.cache.forum;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.resources.constant.forum.Constant;
import com.xiaodou.resources.dao.forum.CascadeQueryForumDao;


/**
 * 话题推荐列表缓存
 * 
 * @author bing.cheng
 * 
 */
@Service("forumRecommendCache")
public class ForumRecommendCache {

  @Resource
  CascadeQueryForumDao cascadeQueryForumDao;

 /** initialDelay 初始调度任务时机 */
//  private Integer initialDelay = 0;

//  private static int forumRecommendCacheTime = Integer.parseInt(CacheTimeProp
//      .getParams("forum.recommend.list.cache.time"));

/* *//** 刷新话题分类任务 *//*
  {
    SummerCommonScheduledExecutor.getExecutor().scheduleWithFixedDelay(new SummerScheduledTask() {
      @Override
      public void doMain() {
        if (null != cascadeQueryForumDao) {
        	List<Forum> list = new ArrayList<Forum>();
             //去查数据库
    	    Map<String, Object> cond = new HashMap<String, Object>();
    	    Map<String, Object> input = new HashMap<String, Object>();
    	    cond.put("input", input);
    	    Map<String, Object> sort = new HashMap<String, Object>();
    	    sort.put("hotList", "DESC");
    	    cond.put("sort", sort);
    	    cond.put("output", ForumUtil.getRecommendForumListOutput());
    	    cond.put("limitCount",20);
    	    List<ForumUserModel> forums = cascadeQueryForumDao.queryForumUserListByCondNoPage(cond);    
    	    if (null != forums && forums.size() > 0) {
    		      for (ForumUserModel model : forums) {
    		        try {
    		          Forum forum = new Forum(model);
    		          forum.setContent(null);
    		          list.add(forum);
    		        } catch (Exception e) {
    		          LoggerUtil.error("话题表中存在脏数据  : " + model.getId(), e);
    		          continue;
    		        }
    		      }
    		    }         
          cacheForumRecommendList(list);
        }
      }

      @Override
      public void onException(Throwable t) {
        LoggerUtil.error("设置推荐资源缓存异常.", t);
      }
    }, initialDelay, forumRecommendCacheTime, TimeUnit.SECONDS);
  }
*/
  /**
   * 缓存话题推荐列表
   * 
   * @param list
   */
 /* private static void cacheForumRecommendList(List<Forum> list) {

    if (null == list || list.size() <= 0) {
      return;
    }
    JedisUtil.addStringToJedis(Constant.FORUM + Constant.COMMON_DELIMITER
        + Constant.FORUM_RECOMMEND, JSON.toJSONString(list), forumRecommendCacheTime * 5);
  }*/

  /**
   * 从缓存中获取话题推荐列表
   */
  public static String getForumRecommendList() {
    return JedisUtil.getStringFromJedis(Constant.FORUM + Constant.COMMON_DELIMITER
        + Constant.FORUM_RECOMMEND);
  }

}
