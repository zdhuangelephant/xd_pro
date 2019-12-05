package com.xiaodou.forum.response.forum;

import com.alibaba.fastjson.JSON;
import com.xiaodou.forum.enums.ForumResType;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 话题发布接口返回话题Id
 * 
 * @author wuyunkuo
 * 
 */
public class ForumResponse extends BaseResponse {

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

}
