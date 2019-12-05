package com.xiaodou.server.mapi.response.forum;

import java.util.List;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.vo.forum.Author;
import com.xiaodou.server.mapi.vo.forum.Forum;
import com.xiaodou.summer.vo.out.ResultType;

public class UserForumListResponse extends BaseResponse {
	 public UserForumListResponse(){};
	 public UserForumListResponse(ResultType type) {
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
