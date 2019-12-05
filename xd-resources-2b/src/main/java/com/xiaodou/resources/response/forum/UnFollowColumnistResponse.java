package com.xiaodou.resources.response.forum;

import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name UnFollowColumnistResponse CopyRright (c) 2016 by zhaodan
 * 
 * @author zhaodan
 * @date 2016年9月1日
 * @description 取消关注专栏响应
 * @version 1.0
 */
public class UnFollowColumnistResponse extends BaseResponse {
  public UnFollowColumnistResponse(ResultType type) {
    super(type);
  }

  public UnFollowColumnistResponse(ForumResType type) {
    super(type);
  }
}
