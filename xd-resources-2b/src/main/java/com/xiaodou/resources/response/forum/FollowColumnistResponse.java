package com.xiaodou.resources.response.forum;

import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name FollowColumnistResponse CopyRright (c) 2016 by zhaodan
 * 
 * @author zhaodan
 * @date 2016年9月1日
 * @description 关注专栏响应
 * @version 1.0
 */
public class FollowColumnistResponse extends BaseResponse {
  public FollowColumnistResponse(ResultType type) {
    super(type);
  }

  public FollowColumnistResponse(ForumResType type) {
    super(type);
  }
}
