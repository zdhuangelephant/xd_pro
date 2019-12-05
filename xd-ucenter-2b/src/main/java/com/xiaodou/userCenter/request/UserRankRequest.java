package com.xiaodou.userCenter.request;

import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class UserRankRequest extends BaseRequest {
  @NotEmpty
  @LegalValueList({"1", "2", "3"})
  private String type;//  榜单类型 1 人气(按被赞次数) 2 学霸（按积分数）3 星星（按星星数量）

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

}
