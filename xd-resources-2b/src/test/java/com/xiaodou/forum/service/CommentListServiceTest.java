package com.xiaodou.forum.service;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.forum.BaseUnitils;
import com.xiaodou.resources.request.forum.MyCommentRequest;
import com.xiaodou.resources.service.forum.CommentListService;

public class CommentListServiceTest extends BaseUnitils {

  @SpringBean("commentListService")
  public CommentListService commentListService;

  @Test
  public void test() {

    MyCommentRequest request = new MyCommentRequest();
    request.setCommentId("");
    request.setSize(20);
    // MyCommentResponse response = commentListService.getMyComment(request);

    // System.out.println(JSON.toJSONString(response));

  }

}
