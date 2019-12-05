package com.xiaodou.resources.response.forum;

import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name PublishColumnistResponse 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月31日
 * @description 发布专栏响应
 * @version 1.0
 */
public class PublishColumnistResponse extends BaseResponse {

  public PublishColumnistResponse(ResultType type) {
    super(type);
  }

  public PublishColumnistResponse(ForumResType type) {
    super(type);
  }
  
}
