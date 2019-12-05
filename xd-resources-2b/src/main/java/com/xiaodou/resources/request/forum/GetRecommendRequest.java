package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.request.BaseRequest;


/**
 * 
 * @author hualong.li
 *
 */
public class GetRecommendRequest extends BaseRequest {
	/**
	 * 最后一条话题id
	 */
	private int forumId;
	/**
	 * 返回的话题数目
	 */
	private int pageSize;

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
