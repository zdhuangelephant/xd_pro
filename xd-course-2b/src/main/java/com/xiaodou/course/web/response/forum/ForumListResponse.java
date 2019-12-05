package com.xiaodou.course.web.response.forum;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.course.common.enums.ForumResType;
import com.xiaodou.course.vo.forum.Forum;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class ForumListResponse extends BaseResponse {
  public ForumListResponse(ResultType type) {
    super(type);
  }

  public ForumListResponse(ForumResType type) {
    super(type);
  }

  public List<Forum> getForumList() {
    return forumList;
  }

  public void setForumList(List<Forum> forumList) {
    this.forumList = forumList;
  }

  private List<Forum> forumList = Lists.newArrayList();
}
