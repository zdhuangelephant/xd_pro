package com.xiaodou.resources.response.forum;

import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name ModifyColumnistResponse 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月31日
 * @description 修改专栏请求
 * @version 1.0
 */
public class ModifyColumnistResponse extends BaseResponse {
  public ModifyColumnistResponse(ResultType type) {
    super(type);
  }

  public ModifyColumnistResponse(ForumResType type) {
    super(type);
  }
}
