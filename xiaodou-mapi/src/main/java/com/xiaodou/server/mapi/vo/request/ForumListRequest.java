package com.xiaodou.server.mapi.vo.request;

import com.xiaodou.server.mapi.request.MapiBaseRequest;

public class ForumListRequest extends MapiBaseRequest {
    private String forumTag;
    private String forumType;
    //分页
    private String forumId;
    private String forumTime;
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
