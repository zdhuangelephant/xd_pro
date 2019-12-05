package com.xiaodou.server.mapi.vo.request;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class ForumRequest extends MapiBaseRequest {
  @NotEmpty
  private String forumId;

  public String getForumId() {
    return forumId;
  }

  public void setForumId(String forumId) {
    this.forumId = forumId;
  }
}
