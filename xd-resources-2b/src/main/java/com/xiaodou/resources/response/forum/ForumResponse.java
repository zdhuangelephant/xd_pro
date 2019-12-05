package com.xiaodou.resources.response.forum;

import com.alibaba.fastjson.JSON;
import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 话题发布接口返回话题Id
 * 
 * @author wuyunkuo
 * 
 */
public class ForumResponse extends BaseResponse {
  private String resourcesId;
  public ForumResponse(ResultType type) {
    super(type);
  }

  public ForumResponse(ForumResType type) {
    super(type);
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }

  public String getResourcesId() {
	  return resourcesId;
  }

  public void setResourcesId(String resourcesId) {
	  this.resourcesId = resourcesId;
  }

}
