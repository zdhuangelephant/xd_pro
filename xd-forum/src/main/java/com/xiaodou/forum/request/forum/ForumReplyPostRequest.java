package com.xiaodou.forum.request.forum;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 回复帖子request
 * 
 * @author bing.cheng
 *
 */
public class ForumReplyPostRequest extends BaseRequest {
	/** 话题Id */
	@NotEmpty
	private Long forumId;

	/** 话题分类ID */
	@NotEmpty
	private Integer forumCategoryId;

	/** 评论内容 */
	@NotEmpty
	private String content;

	public Long getForumId() {
		return forumId;
	}

	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}

	public Integer getForumCategoryId() {
		return forumCategoryId;
	}

	public void setForumCategoryId(Integer forumCategoryId) {
		this.forumCategoryId = forumCategoryId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
