package com.xiaodou.ucenter.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucenter.response.resultype.UcenterResType;

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
