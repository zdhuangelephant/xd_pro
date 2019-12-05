package com.xiaodou.course.web.request.forum;

import com.xiaodou.course.web.request.BaseRequest;

public class ProductForumListRequest extends BaseRequest {
  private String forumTag;
  private String forumType;
  private String forumTime;
  // 分页
  private String forumId;

  public String getForumTag() {
    return forumTag;
  }

  public void setForumTag(String forumTag) {
    this.forumTag = forumTag;
  }

  public String getForumType() {
    return forumType;
  }

  public void setForumType(String forumType) {
    this.forumType = forumType;
  }

  public String getForumId() {
    return forumId;
  }

  public void setForumId(String forumId) {
    this.forumId = forumId;
  }

  public String getForumTime() {
    return forumTime;
  }

  public void setForumTime(String forumTime) {
    this.forumTime = forumTime;
  }
}
