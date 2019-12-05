package com.xiaodou.server.mapi.vo.request;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 点赞request
 * 
 * @author wuyunkuo
 * 
 */
public class ForumPraiseRequest extends MapiBaseRequest {
  /**
   * 帖子Id/itemId
   */
  @NotEmpty
  private String forumId;

	public String getForumId() {
		return forumId;
	}
	
	public void setForumId(String forumId) {
		this.forumId = forumId;
	}



}
