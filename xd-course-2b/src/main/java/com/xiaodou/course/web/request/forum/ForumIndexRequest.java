package com.xiaodou.course.web.request.forum;

import com.xiaodou.summer.vo.in.BaseValidatorPojo;

public class ForumIndexRequest extends BaseValidatorPojo {
  private String forumId;

  public String getForumId() {
    return forumId;
  }

  public void setForumId(String forumId) {
    this.forumId = forumId;
  }

}
