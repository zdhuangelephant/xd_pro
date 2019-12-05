package com.xiaodou.forum.service;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.forum.BaseUnitils;
import com.xiaodou.resources.service.forum.ForumPraiseService;

public class Praise extends BaseUnitils {

	 @SpringBean("forumPraiseService")
	  ForumPraiseService forumPraiseService;
	 
	 @Test
	 public void praise(){
		 forumPraiseService.updatePraise(true, 16, 1447998377828010763l);
		 while(true){}
	 }
	 
}
