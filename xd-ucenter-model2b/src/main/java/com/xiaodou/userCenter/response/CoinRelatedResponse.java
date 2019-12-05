package com.xiaodou.userCenter.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class CoinRelatedResponse extends BaseResultInfo {
  public CoinRelatedResponse(UcenterResType type) {
    super(type);
  }

  /**
   * 用户金币值
   */
  private String coin = Integer.toString(0);

  public CoinRelatedResponse(ResultType type) {
    super(type);
  }

  public String getCoin() {
    return coin;
  }

  public void setCoin(String coin) {
    this.coin = coin;
  }

}
