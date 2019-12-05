package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.request.BaseRequest;


/**
 * 回帖－写评论请求
 * @author weichao.zhai
 * @time 2015年4月6日 下午4:33:06
 */
public class RepliesRequest extends BaseRequest{
	/** 话题ID */
	private String forumId;
	/** 评论人 */
	private String reviewPeople;
	/** 评论目的ID 
	 * 如果为空，则为回复话题；否则回复评论
	 */
	private String targeReplyId;
	/** 回帖内容 */
	private String content;
	public String getForumId() {
		return forumId;
	}
	public void setForumId(String forumId) {
		this.forumId = forumId;
	}
	public String getReviewPeople() {
		return reviewPeople;
	}
	public void setReviewPeople(String reviewPeople) {
		this.reviewPeople = reviewPeople;
	}
	public String getTargeReplyId() {
		return targeReplyId;
	}
	public void setTargeReplyId(String targeReplyId) {
		this.targeReplyId = targeReplyId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	

}
