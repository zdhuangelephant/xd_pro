package com.xiaodou.server.mapi.response.forum;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.vo.forum.Forum;
import com.xiaodou.summer.vo.out.ResultType;

public class ForumListResponse extends BaseResponse {
  public ForumListResponse(){};
  public ForumListResponse(ResultType type) {
    super(type);
  }
   public List<Forum> getForumList() {
	return forumList;
  }
  public void setForumList(List<Forum> forumList) {
	  this.forumList = forumList;
  }
  private List<Forum> forumList=Lists.newArrayList();
  }
