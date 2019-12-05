package com.xiaodou.forum.response.forum;

import com.alibaba.fastjson.JSON;
import com.xiaodou.forum.vo.forum.Forum;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 话题详情返回结果－话题部分
 * 
 * @author weichao.zhai
 * @time 2015年4月6日 下午6:19:08
 */
public class ForumDetailForumResponse extends BaseResponse {

	public ForumDetailForumResponse(ResultType type) {
		super(type);
	}
	
	private Forum forum;

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}
	
	@Override
	public String toString() {
	return JSON.toJSONString(this);
	}
}
