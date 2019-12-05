package com.xiaodou.server.mapi.response.ucenter;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 
 * 找回密码response
 * 
 * @author weirong.li
 * @version 1.0
 * @since JDK1.7
 */
public class FindPasswordResponse extends BaseResponse {
  public FindPasswordResponse() {}

  public FindPasswordResponse(ResultType type) {
    super(type);
  }

  public FindPasswordResponse(UcenterResType type) {
    super(type);
  }

}
