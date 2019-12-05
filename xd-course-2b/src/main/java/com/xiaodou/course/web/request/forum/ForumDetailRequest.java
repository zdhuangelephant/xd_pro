package com.xiaodou.course.web.request.forum;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

public class ForumDetailRequest extends BaseValidatorPojo {
  @NotEmpty
  private String forumId;

  public String getForumId() {
    return forumId;
  }

  public void setForumId(String forumId) {
    this.forumId = forumId;
  }

}
