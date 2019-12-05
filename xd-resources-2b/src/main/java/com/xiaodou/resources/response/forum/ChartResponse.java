package com.xiaodou.resources.response.forum;

import java.util.List;

import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.vo.forum.ModuleSlide;
import com.xiaodou.summer.vo.out.ResultType;


/**
 * 发布话题回复
 * 
 * @author hualong.li
 * 
 */
public class ChartResponse extends BaseResponse {
  public ChartResponse(ResultType type) {
    super(type);
  }

  public List<ModuleSlide> getImageUrlList() {
	return imageUrlList;
}

public void setImageUrlList(List<ModuleSlide> imageUrlList) {
	this.imageUrlList = imageUrlList;
}

// 幻灯片
  private List<ModuleSlide> imageUrlList;

}
