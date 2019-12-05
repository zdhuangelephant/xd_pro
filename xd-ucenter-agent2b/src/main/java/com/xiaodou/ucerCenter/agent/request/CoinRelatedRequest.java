package com.xiaodou.ucerCenter.agent.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.ucerCenter.agent.constant.UcenterModelConstant;

public class CoinRelatedRequest extends BaseRequest {
  /* 对金币操作的相关类型 */
  @NotEmpty
  private String relatedType;
  /* 金币数 */
  @NotEmpty(field = "relatedType", value = UcenterModelConstant.UPDATE_COIN)
  private Integer coin;

  public String getRelatedType() {
    return relatedType;
  }

  public void setRelatedType(String relatedType) {
    this.relatedType = relatedType;
  }

  public Integer getCoin() {
    return coin;
  }

  public void setCoin(Integer coin) {
    this.coin = coin;
  }
}
