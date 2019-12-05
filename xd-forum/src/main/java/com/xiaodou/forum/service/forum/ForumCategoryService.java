package com.xiaodou.forum.service.forum;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.forum.model.forum.ForumCategoryModel;
import com.xiaodou.forum.response.forum.ForumCategoryResponse;
import com.xiaodou.forum.response.forum.ListResponse;
import com.xiaodou.forum.service.facade.ForumServiceFacade;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 话题分类service
 * 
 * @author bing.cheng
 * 
 */
@Service("forumCategoryService")
public class ForumCategoryService {

  @Resource
  ForumServiceFacade forumServiceFacade;

  /**
   * 获取话题分类列表
   * 
   * @return
   */
  public ListResponse<ForumCategoryResponse> getForumCategoryList() throws Exception {
    ListResponse<ForumCategoryResponse> listResponse =
        new ListResponse<ForumCategoryResponse>(ResultType.SUCCESS);
    List<ForumCategoryModel> forumList = forumServiceFacade.queryCatagoryList(null, null);
    if (null == forumList || forumList.size() <= 0) {
      return listResponse;
    }
    // 构造返回结果
    List<ForumCategoryResponse> allList = Lists.newArrayList();
    for (ForumCategoryModel model : forumList) {
      try {
        ForumCategoryResponse res = new ForumCategoryResponse(model);
        allList.add(res);
      } catch (Exception e) {
        LoggerUtil.error("查询话题分类列表，构造分类模型异常", e);
        continue;
      }
    }
    listResponse.setList(allList);
    return listResponse;
  }

}
