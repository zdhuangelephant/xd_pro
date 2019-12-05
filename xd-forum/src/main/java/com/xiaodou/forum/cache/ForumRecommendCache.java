package com.xiaodou.forum.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.forum.constant.Constant;
import com.xiaodou.forum.dao.forum.CascadeQueryForumDao;
import com.xiaodou.forum.enums.ForumEnum;
import com.xiaodou.forum.model.forum.ForumUserModel;
import com.xiaodou.forum.util.CacheTimeProp;
import com.xiaodou.forum.util.ForumUtil;
import com.xiaodou.forum.vo.forum.ForumRecommendVo;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;


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
  private Integer initialDelay = 0;

  private static int forumRecommendCacheTime = Integer.parseInt(CacheTimeProp
      .getParams("forum.recommend.list.cache.time"));

  /** 刷新话题分类任务 */
  {
    SummerCommonScheduledExecutor.getExecutor().scheduleWithFixedDelay(new SummerScheduledTask() {
      @Override
      public void doMain() {
        if (null != cascadeQueryForumDao) {
          LinkedList<ForumRecommendVo> allList = null;

          // 2、查询数据库 存缓存 返回列表
          // ①、推荐 recommend = 1
          Map<String, Object> cond = new HashMap<String, Object>();
          Map<String, Object> input = new HashMap<String, Object>();
          input.put("recommend", ForumEnum.IsRecommendForum.getCode());
          cond.put("input", input);
          Map<String, Object> sort = new HashMap<String, Object>();
          sort.put("updateTime", "DESC");
          cond.put("sort", sort);
          // TODO 目前没有分页，全部展示
          cond.put("limitCount", 50);
          cond.put("output", ForumUtil.getForumListOutput());

          List<ForumUserModel> list = cascadeQueryForumDao.queryForumUserListByCondNoPage(cond);
          if (null == list || list.size() <= 0) {
            return;
          }

          // 构造返回结果
          allList = new LinkedList<ForumRecommendVo>();
          // 普通推荐话题临时存储
          List<ForumUserModel> commonList = new ArrayList<ForumUserModel>();
          for (ForumUserModel model : list) {
            // 筛选出普通话题
            if (ForumEnum.IsTopForum.getCode() != model.getTop()) {
              commonList.add(model);
            } else {
              // 置顶话题存入返回结果列表
              try {
                allList.add(new ForumRecommendVo(model));
              } catch (Exception e) {
                continue;
              }
            }
            Collections.sort(allList, new Comparator<ForumRecommendVo>() {
              @Override
              public int compare(ForumRecommendVo _this, ForumRecommendVo _other) {
                return Integer.parseInt(_other.getPraiseNumber())
                    - Integer.parseInt(_this.getPraiseNumber());
              }
            });
          }

          for (ForumUserModel commonModel : commonList) {
            // 置顶话题存入返回结果列表
            try {
              allList.add(new ForumRecommendVo(commonModel));
            } catch (Exception e) {
              continue;
            }
          }

          cacheForumRecommendList(allList);
        }
      }

      @Override
      public void onException(Throwable t) {
        LoggerUtil.error("设置话题分类缓存异常.", t);
      }
    }, initialDelay, forumRecommendCacheTime, TimeUnit.SECONDS);
  }

  /**
   * 缓存话题推荐列表
   * 
   * @param list
   */
  private static void cacheForumRecommendList(List<ForumRecommendVo> list) {

    if (null == list || list.size() <= 0) {
      return;
    }
    JedisUtil.addStringToJedis(Constant.FORUM + Constant.COMMON_DELIMITER
        + Constant.FORUM_RECOMMEND, JSON.toJSONString(list), forumRecommendCacheTime * 5);
  }

  /**
   * 从缓存中获取话题推荐列表
   */
  public static String getForumRecommendList() {
    return JedisUtil.getStringFromJedis(Constant.FORUM + Constant.COMMON_DELIMITER
        + Constant.FORUM_RECOMMEND);
  }

}
