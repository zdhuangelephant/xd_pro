package com.xiaodou.forum.service;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.forum.BaseUnitils;
import com.xiaodou.resources.request.forum.ForumDetailCommentsRequest;
import com.xiaodou.resources.request.forum.ForumDetailForumRequest;
import com.xiaodou.resources.service.forum.ForumDetailService;

public class ForumDetailServiceTest extends BaseUnitils {

  @SpringBean("forumDetailService")
  ForumDetailService forumDetailService;

  @Test
  public void forumContentTest() {
    ForumDetailForumRequest request = new ForumDetailForumRequest();
    request.setResourcesId("18");
    // System.out.println(forumDetailService.forumContent(request));
  }

  @Test
  public void forumCommentsTest() {
    ForumDetailCommentsRequest request = new ForumDetailCommentsRequest();
    request.setResourcesId("18");
    // System.out.println(forumDetailService.forumComments(request));
  }

}
