package com.xiaodou.ucenter.response;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucenter.response.resultype.UcenterResType;

/**
 * 
 * 注册response
 * 
 * @author weirong.li
 * @version 1.0
 * @since JDK1.7
 */
public class RegistUserResponse extends BaseResponse {
  public RegistUserResponse() {}

  public RegistUserResponse(UcenterResType type) {
    super(type);
  }

  /**
   * token值
   */
  private String token = StringUtils.EMPTY;

  public RegistUserResponse(ResultType type) {
    super(type);
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

}
