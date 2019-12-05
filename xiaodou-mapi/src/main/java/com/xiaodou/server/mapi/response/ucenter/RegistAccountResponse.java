package com.xiaodou.server.mapi.response.ucenter;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 
 * 注册response
 * 
 * @author weirong.li
 * @version 1.0
 * @since JDK1.7
 */
public class RegistAccountResponse extends BaseResponse {

  public RegistAccountResponse() {}

  public RegistAccountResponse(UcenterResType type) {
    super(type);
  }

  /**
   * token值
   */
  private String token = StringUtils.EMPTY;

  public RegistAccountResponse(ResultType type) {
    super(type);
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

}
