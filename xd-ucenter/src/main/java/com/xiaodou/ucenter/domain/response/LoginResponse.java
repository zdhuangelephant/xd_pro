package com.xiaodou.ucenter.domain.response;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucenter.domain.response.resultype.UcenterResType;

public class LoginResponse extends BaseResultInfo {

  public LoginResponse() {
    super();
  }

  public LoginResponse(ResultType type) {
    super(type);
  }

  public LoginResponse(UcenterResType resType) {
    super(resType);
  }

  private String xdUniqueId = StringUtils.EMPTY;

  public String getXdUniqueId() {
    return xdUniqueId;
  }

  public void setXdUniqueId(String xdUniqueId) {
    this.xdUniqueId = xdUniqueId;
  }
}
