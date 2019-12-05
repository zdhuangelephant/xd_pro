package com.xiaodou.course.web.response.forum;

import com.xiaodou.course.common.enums.ForumResType;
import com.xiaodou.course.vo.forum.Forum;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class ForumResponse extends BaseResponse {
	 public ForumResponse(ResultType type) {
		    super(type);
		  }
	  public ForumResponse(ForumResType type) {
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
