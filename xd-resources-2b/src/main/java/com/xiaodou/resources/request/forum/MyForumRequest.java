package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.request.BaseRequest;


/**
 * 我参与的，我发布的 话题
 * 
 * @author zhouhuan
 * @time 2015年4月6日 下午6:17:26
 */
public class MyForumRequest extends BaseRequest {

  /** 最后一个话题ID */
  private String resourcesId;
  
  /** 显示个数 */
  private Integer size = 20;

  /** 返回的资源类型0代表说说,1代表文章,2代表视频,其他代表文章加视频*/
  private String resourcesType;

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }






public String getResourcesId() {
	return resourcesId;
}

public void setResourcesId(String resourcesId) {
	this.resourcesId = resourcesId;
}

public String getResourcesType() {
	return resourcesType;
}

public void setResourcesType(String resourcesType) {
	this.resourcesType = resourcesType;
}


}
