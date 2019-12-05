package com.xiaodou.server.mapi.response.forum;

import java.util.List;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.vo.forum.Forum;
import com.xiaodou.server.mapi.vo.forum.ModuleSlide;
import com.xiaodou.summer.vo.out.ResultType;


/**
 * 发布话题回复
 * 
 * @author hualong.li
 * 
 */
public class IndexResponse extends BaseResponse {
	public IndexResponse(){};
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
