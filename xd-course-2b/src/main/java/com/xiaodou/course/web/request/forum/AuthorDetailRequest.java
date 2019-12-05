package com.xiaodou.course.web.request.forum;

import com.xiaodou.summer.vo.in.BaseValidatorPojo;

public class AuthorDetailRequest extends BaseValidatorPojo {
  /** uid 作者ID */
  private String uid;
  private String forumId;

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getForumId() {
    return forumId;
  }

  public void setForumId(String forumId) {
    this.forumId = forumId;
  }
}
