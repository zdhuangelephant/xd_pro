package com.xiaodou.resources.service.forum;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.resources.model.forum.ForumCategoryModel;
import com.xiaodou.resources.response.forum.CategoryResponse;
import com.xiaodou.resources.response.forum.ListResponse;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;
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
  public ListResponse<CategoryResponse> getForumCategoryList() throws Exception {
    ListResponse<CategoryResponse> listResponse =
        new ListResponse<CategoryResponse>(ResultType.SUCCESS);
    List<ForumCategoryModel> forumList = forumServiceFacade.queryCatagoryList(null, null);
    for(int i=0;i<forumList.size();i++){
    	 if(forumList.get(i).getId().toString().equals("0")){
    		 forumList.remove(i);
    	 }
    }
    if (null == forumList || forumList.size() <= 0) {
      return listResponse;
    }
    // 构造返回结果
    List<CategoryResponse> allList = Lists.newArrayList();
    for (ForumCategoryModel model : forumList) {
      try {
    	  CategoryResponse res = new CategoryResponse(model);
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
