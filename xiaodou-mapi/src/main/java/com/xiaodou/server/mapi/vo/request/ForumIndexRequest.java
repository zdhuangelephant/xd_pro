package com.xiaodou.server.mapi.vo.request;

import com.xiaodou.server.mapi.request.MapiBaseRequest;

public class ForumIndexRequest extends MapiBaseRequest {
    private String forumId;
	public String getForumId() {
		return forumId;
	}
	public void setForumId(String forumId) {
		this.forumId = forumId;
	}

}
