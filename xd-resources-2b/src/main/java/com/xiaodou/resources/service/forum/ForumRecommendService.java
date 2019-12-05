package com.xiaodou.resources.service.forum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.resources.cache.forum.ForumRecommendCache;
import com.xiaodou.resources.constant.product.CourseConstant;
import com.xiaodou.resources.model.forum.ForumUserModel;
import com.xiaodou.resources.model.forum.ModuleSlideModel;
import com.xiaodou.resources.request.forum.ForumListByColumnRequest;
import com.xiaodou.resources.request.forum.ReCommendListRequest;
import com.xiaodou.resources.response.forum.ChartResponse;
import com.xiaodou.resources.response.forum.ForumListResponse;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;
import com.xiaodou.resources.util.ForumUtil;
import com.xiaodou.resources.vo.forum.Forum;
import com.xiaodou.resources.vo.forum.ModuleSlide;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 话题推荐service
 * 
 * @author bing.cheng
 * 
 */
@Service("forumRecommendService")
public class ForumRecommendService {

  @Resource
  ForumServiceFacade forumServiceFacade;

  @Resource
  ModuleSlideService moduleSlideService;

  @Resource
  ForumRecommendCache forumRecommendCache;

  @Resource
  ForumListService forumListService;

  /**
   * 获取推荐列表 TODO
   * 
   * @return
   */
  public ForumListResponse getRecommendList(ReCommendListRequest request) {
    ForumListResponse response = new ForumListResponse(ResultType.SUCCESS);
    List<Forum> recommendForums = new ArrayList<Forum>();
    /*
     * // 1、先去缓存取。 String result = ForumRecommendCache.getForumRecommendList(); if
     * (!StringUtils.isBlank(result)) { recommendForums = (ArrayList<Forum>)
     * JSON.parseObject(result, new TypeReference<ArrayList<Forum>>() {});
     * response.setList(recommendForums); return response; }
     */
    // 2.去查数据库
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> input = new HashMap<String, Object>();
    if (request.getThreshold() != null) {
      input.put("hotList", request.getThreshold());
    }
    if (request.getResourcesId() != null) {
      ForumUserModel forumUserModel =
          forumServiceFacade.queryForumUserById(request.getResourcesId().toString());
      if (null != forumUserModel && null != forumUserModel.getUpdateTime()) {
        input.put("updateTimeUpper", DateUtil.SDF_FULL.format(forumUserModel.getUpdateTime()));
      }
    }
    cond.put("input", input);
    Map<String, Object> sort = new HashMap<String, Object>();
    sort.put("hotList", "DESC");
    sort.put("updateTime", "DESC");
    cond.put("sort", sort);
    cond.put("output", ForumUtil.getRecommendForumListOutput());
    cond.put("limitCount", request.getSize());
    List<ForumUserModel> forums = forumServiceFacade.queryForumUserListByCondNoPage(cond);
    if (null != forums && forums.size() > 0) {
      for (ForumUserModel model : forums) {
        try {
          Forum forum = new Forum(model);
          if (!forum.getDigest().toString().equals("0")) {
            forum.setContent(null);
          }
          recommendForums.add(forum);
        } catch (Exception e) {
          LoggerUtil.error("话题表中存在脏数据  : " + model.getId(), e);
          continue;
        }
      }
    }
    response.setList(forumListService.getAllList(recommendForums));
    return response;
  }


  /**
   * 获取专栏最热列表 TODO
   * 
   * @return
   */
  public ForumListResponse getRecommendListByColumnId(ForumListByColumnRequest request) {
    ForumListResponse response = new ForumListResponse(ResultType.SUCCESS);
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("columnId", request.getColumnId());
    input.put("digestSearch", 0);
    cond.put("input", input);
    Map<String, Object> sort = new HashMap<String, Object>();
    sort.put("hotList", "DESC");
    cond.put("sort", sort);
    cond.put("output", ForumUtil.getRecommendForumListOutput());
    cond.put("limitStart", (request.getPageNo() - 1) * request.getSize());
    cond.put("limitCount", request.getSize());
    List<ForumUserModel> forums = forumServiceFacade.queryForumUserListByCondNoPage(cond);
    List<Forum> recommendForums = new ArrayList<Forum>();
    if (null != forums && forums.size() > 0) {
      for (ForumUserModel model : forums) {
        try {
          Forum forum = new Forum(model);
          forum.setContent(null);
          recommendForums.add(forum);
        } catch (Exception e) {
          LoggerUtil.error("话题表中存在脏数据  : " + model.getId(), e);
          continue;
        }
      }
    }
    response.setList(forumListService.getAllList(recommendForums));
    return response;
  }

  public ChartResponse getChart() {
    // 幻灯片列表
    ChartResponse response = new ChartResponse(ResultType.SUCCESS);
    List<ModuleSlideModel> sildes =
        moduleSlideService.moduleSlideList(CourseConstant.MODULE_SLIDE_HOME);
    List<ModuleSlide> slideList = new ArrayList<>();
    for (ModuleSlideModel moduleSlideModel : sildes) {
      ModuleSlide moduleSlide = new ModuleSlide();
      moduleSlide.setTitle(moduleSlideModel.getDescription());
      moduleSlide.setImageUrl(moduleSlideModel.getImageUrl());
      moduleSlide.setRedirectUrl(moduleSlideModel.getLinkUrl());
      slideList.add(moduleSlide);
    }
    response.setImageUrlList(slideList);
    return response;
  }

}
