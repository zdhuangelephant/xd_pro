package com.xiaodou.course.web.response.forum;

import java.util.List;

import com.xiaodou.course.common.enums.ForumResType;
import com.xiaodou.course.vo.forum.Author;
import com.xiaodou.course.vo.forum.Forum;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class UserForumListResponse extends BaseResponse {
	 public UserForumListResponse(ResultType type) {
		    super(type);
		  }
    public UserForumListResponse(ForumResType type) {
    	 super(type);
	}
	private Author author;
	private List<Forum> list;
	public List<Forum> getList() {
		return list;
	}
	public void setList(List<Forum> list) {
		this.list = list;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}

}
