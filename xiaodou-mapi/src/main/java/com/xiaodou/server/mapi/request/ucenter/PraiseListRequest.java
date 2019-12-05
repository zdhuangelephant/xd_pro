package com.xiaodou.server.mapi.request.ucenter;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class PraiseListRequest extends MapiBaseRequest {

  /**
   * 类型 1、谁点赞过我 2、我点赞过谁 
   */
  @NotEmpty
  @LegalValueList({"1", "2"})
  private String type;

  public PraiseListRequest() {}

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

}
