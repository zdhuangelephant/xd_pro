package com.xiaodou.course.web.response.forum;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;

public class ShareAllResponse extends ForumListResponse {
	 public ShareAllResponse(ResultType type) {
		    super(type);
		  }
     public List<ProductTag> getList() {
		return list;
	}
	public void setList(List<ProductTag> list) {
		this.list = list;
	}
	private List<ProductTag> list=Lists.newArrayList();
    public class ProductTag{
    	 private String courseId;
    	 private String name;
		public String getCourseId() {
			return courseId;
		}
		public void setCourseId(String courseId) {
			this.courseId = courseId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
     }
}
