package com.xiaodou.course.web.response.forum;

import java.util.List;

import com.xiaodou.course.vo.forum.Forum;
import com.xiaodou.course.vo.product.ModuleSlide;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;


/**
 * 发布话题回复
 * 
 * @author hualong.li
 * 
 */
public class IndexResponse extends BaseResponse {
  public IndexResponse(ResultType type) {
    super(type);
  }

  public List<ModuleSlide> getImageUrlList() {
	return imageUrlList;
}

public void setImageUrlList(List<ModuleSlide> imageUrlList) {
	this.imageUrlList = imageUrlList;
}

public Forum getForum() {
	return forum;
}

public void setForum(Forum forum) {
	this.forum = forum;
}

  public List<Forum> getList() {
	return list;
}

public void setList(List<Forum> list) {
	this.list = list;
}

// 幻灯片
  private List<ModuleSlide> imageUrlList;
  //校园之声   
  private Forum forum;
  //公开课知识分享
  private List<Forum> list;

}
