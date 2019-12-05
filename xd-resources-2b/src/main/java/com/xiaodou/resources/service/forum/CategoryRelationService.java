package com.xiaodou.resources.service.forum;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.resources.model.forum.CategoryRelationModel;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;


/**
 * 话题分类话题关系表
 * 
 * @author zhouhuan
 * 
 */
@Service
public class CategoryRelationService {
   @Resource
   ForumServiceFacade forumServiceFacade;
   public void insert(CategoryRelationModel model){
	   forumServiceFacade.categoryRelationInsert(model);
   }
}
