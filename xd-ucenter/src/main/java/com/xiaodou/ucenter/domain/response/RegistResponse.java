package com.xiaodou.ucenter.domain.response;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucenter.domain.response.resultype.UcenterResType;

/**
 * 
 * @name RegistResponse 
 * CopyRright (c) 2017 by 李德洪
 *
 * @author 李德洪
 * @description TODO
 * @version 1.0
 */
public class RegistResponse extends BaseResultInfo {
  public RegistResponse() {}

  public RegistResponse(UcenterResType type) {
    super(type);
  }

  public RegistResponse(ResultType type) {
    super(type);
  }

  private String xdUniqueId = StringUtils.EMPTY;

  public String getXdUniqueId() {
    return xdUniqueId;
  }

  public void setXdUniqueId(String xdUniqueId) {
    this.xdUniqueId = xdUniqueId;
  }

}
