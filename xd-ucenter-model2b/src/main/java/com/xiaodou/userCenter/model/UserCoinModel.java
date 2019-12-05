package com.xiaodou.userCenter.model;

public class UserCoinModel {

  private Long id;
  private Long userId;
  private Integer coin;

  public UserCoinModel() {}

  public UserCoinModel(Integer coin) {
    this.coin = coin;
  }

  public UserCoinModel(Long userId, Integer coin) {
    this.userId = userId;
    this.coin = coin;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Integer getCoin() {
    return coin;
  }

  public void setCoin(Integer coin) {
    this.coin = coin;
  }
}
