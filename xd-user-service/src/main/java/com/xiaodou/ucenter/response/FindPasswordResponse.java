package com.xiaodou.ucenter.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucenter.response.resultype.UcenterResType;

/**
 * 
 * 找回密码response
 * 
 * @version 1.0
 * @since JDK1.7
 */
public class FindPasswordResponse extends BaseResponse {
  public FindPasswordResponse(ResultType type) {
    super(type);
  }

  public FindPasswordResponse(UcenterResType type) {
    super(type);
  }

  public FindPasswordResponse() {}
}
