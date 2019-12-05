package com.xiaodou.resources.response.forum;

import com.xiaodou.resources.vo.forum.Forum;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 我参与的，我发布的 话题
 * @author weichao.zhai
 * @time 2015年4月6日 下午6:22:26
 */
public class MyForumResponse extends ListResponse<Forum>{
	
	/** 返回个数 */
	private String size;

	public MyForumResponse(ResultType type) {
		super(type);
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

}
