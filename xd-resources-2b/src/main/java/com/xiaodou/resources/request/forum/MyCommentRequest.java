package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.request.BaseRequest;


/**
 * 我参与的，我回贴的 评论
 * @author weichao.zhai
 * @time 2015年4月6日 下午6:13:33
 */
public class MyCommentRequest extends BaseRequest{
	
	/** 最后一个评论ID */
	private String commentId;
	/** 显示个数 */
	private Integer size=20;
	
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	
}
