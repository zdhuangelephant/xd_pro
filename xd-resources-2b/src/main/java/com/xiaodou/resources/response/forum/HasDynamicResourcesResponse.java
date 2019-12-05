package com.xiaodou.resources.response.forum;

import com.alibaba.fastjson.JSON;
import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 是否有动态
 * 
 * @author zhouhuan
 * 
 */
public class HasDynamicResourcesResponse extends BaseResponse {
  private String hasDynamic="0";
  public HasDynamicResourcesResponse(ResultType type) {
    super(type);
  }

  public HasDynamicResourcesResponse(ForumResType type) {
    super(type);
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }

  public String getHasDynamic() {
	  return hasDynamic;
  }

  public void setHasDynamic(String hasDynamic) {
	  this.hasDynamic = hasDynamic;
  }
}
