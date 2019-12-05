package com.xiaodou.server.mapi.response.forum;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.response.forum.ShareAllResponse.ProductTag;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.vo.forum.Forum;
import com.xiaodou.summer.vo.out.ResultType;

public class ForumShareResponse extends BaseResponse {
	public ForumShareResponse(){};
	 public ForumShareResponse(ResultType type) {
		    super(type);
		  }
    private List<ForumShare> list=Lists.newArrayList();
	public List<ForumShare> getList() {
		return list;
	}
	public void setList(List<ForumShare> list) {
		this.list = list;
	}

    public List<ProductTag> getProductTagList() {
		return productTagList;
	}
	public void setProductTagList(List<ProductTag> productTagList) {
		this.productTagList = productTagList;
	}
	private List<ProductTag> productTagList;
	public class ForumShare{
		private String timeTag ;
		private List<Forum> forumList;
		public String getTimeTag() {
			return timeTag;
		}
		public void setTimeTag(String timeTag) {
			this.timeTag = timeTag;
		}
		public List<Forum> getForumList() {
			return forumList;
		}
		public void setForumList(List<Forum> forumList) {
			this.forumList = forumList;
		}
	}
	  
}
