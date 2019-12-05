package com.xiaodou.forum.service;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.forum.BaseUnitils;
import com.xiaodou.forum.request.forum.ForumDetailCommentsRequest;
import com.xiaodou.forum.request.forum.ForumDetailForumRequest;
import com.xiaodou.forum.service.forum.ForumDetailService;

public class ForumDetailServiceTest extends BaseUnitils {

  @SpringBean("forumDetailService")
  ForumDetailService forumDetailService;

  @Test
  public void forumContentTest() {
    ForumDetailForumRequest request = new ForumDetailForumRequest();
    request.setForumId("18");
    // System.out.println(forumDetailService.forumContent(request));
  }

  @Test
  public void forumCommentsTest() {
    ForumDetailCommentsRequest request = new ForumDetailCommentsRequest();
    request.setForumId("18");
    // System.out.println(forumDetailService.forumComments(request));
  }

}
