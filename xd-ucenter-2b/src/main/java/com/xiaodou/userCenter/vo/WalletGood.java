package com.xiaodou.userCenter.vo;

import com.xiaodou.userCenter.model.AccountWalletGood;
import com.xiaodou.wallet.agent.model.IGoodEntity;

public class WalletGood implements IGoodEntity {

  public WalletGood(AccountWalletGood good) {
    this.good = good;
  }

  private AccountWalletGood good;

  @Override
  public Double getAmount() {
    return good.getGoodsPrice();
  }

  @Override
  public Double getCount() {
    return good.getGoodsCount();
  }

  @Override
  public String getName() {
    return good.getGoodsName();
  }

}
