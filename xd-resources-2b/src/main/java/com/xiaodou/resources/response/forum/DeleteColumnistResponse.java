package com.xiaodou.resources.response.forum;

import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name DeleteColumnistResponse CopyRright (c) 2016 by zhaodan
 * 
 * @author zhaodan
 * @date 2016年9月1日
 * @description 删除专栏响应
 * @version 1.0
 */
public class DeleteColumnistResponse extends BaseResponse {
  public DeleteColumnistResponse(ResultType type) {
    super(type);
  }

  public DeleteColumnistResponse(ForumResType type) {
    super(type);
  }
}
