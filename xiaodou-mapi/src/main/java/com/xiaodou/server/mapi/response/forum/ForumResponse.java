package com.xiaodou.server.mapi.response.forum;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.vo.forum.Forum;
import com.xiaodou.summer.vo.out.ResultType;

public class ForumResponse extends BaseResponse {
	public ForumResponse(){};
	public ForumResponse(ResultType type) {
		    super(type);
		  }
	private Forum forum;

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}
}
