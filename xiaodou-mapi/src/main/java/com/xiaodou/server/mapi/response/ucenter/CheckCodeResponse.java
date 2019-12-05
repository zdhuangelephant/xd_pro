package com.xiaodou.server.mapi.response.ucenter;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

public class CheckCodeResponse extends BaseResponse {

  /**
   * 验证码
   */
  private String checkCode;

  public CheckCodeResponse() {}

  public CheckCodeResponse(ResultType type) {
    super(type);
  }

  public CheckCodeResponse(UcenterResType type) {
    super(type);
  }

  public String getCheckCode() {
    return checkCode;
  }

  public void setCheckCode(String checkCode) {
    this.checkCode = checkCode;
  }

}
