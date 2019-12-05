package com.xiaodou.wallet.agent.model;

import java.sql.Timestamp;

/**
 * @name @see com.xiaodou.wallet.model.AccountWallet.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月2日
 * @description 账户钱包模型
 * @version 1.0
 */
public class AccountWallet {
  /** id 主键ID */
  private Long id;
  /** productLine 产品线 */
  private String productLine;
  /** userId 用户ID */
  private String userId;
  /** accountAmount 账户余额 */
  private Double accountAmount;
  /* giftStatus 酬谢状态 */
  private Integer giftStatus;
  /* defaultGiftMoney 默认酬谢金额 */
  private Double defaultGiftMoney;
  /* giftDesc 酬谢描述 */
  private String giftDesc;
  /* alreadyClearIncome 已结算酬谢数 */
  private Double alreadyClearIncome;
  /* giftTotalIncome 酬谢总收入*/
  private Double giftTotalIncome;
  /* bindPhone 绑定的手机号 */
  private String bindPhone;
  /* bindAliPayAccount 绑定的支付宝账号 */
  private String bindAliPayAccount;
  /** createTime 账户创建时间 */
  private Timestamp createTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getProductLine() {
    return productLine;
  }

  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Double getAccountAmount() {
    return accountAmount;
  }

  public void setAccountAmount(Double accountAmount) {
    this.accountAmount = accountAmount;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Double getDefaultGiftMoney() {
    return defaultGiftMoney;
  }

  public void setDefaultGiftMoney(Double defaultGiftMoney) {
    this.defaultGiftMoney = defaultGiftMoney;
  }

  public String getGiftDesc() {
    return giftDesc;
  }

  public void setGiftDesc(String giftDesc) {
    this.giftDesc = giftDesc;
  }

  public Double getAlreadyClearIncome() {
    return alreadyClearIncome;
  }

  public void setAlreadyClearIncome(Double alreadyClearIncome) {
    this.alreadyClearIncome = alreadyClearIncome;
  }

  public String getBindPhone() {
    return bindPhone;
  }

  public void setBindPhone(String bindPhone) {
    this.bindPhone = bindPhone;
  }

  public String getBindAliPayAccount() {
    return bindAliPayAccount;
  }

  public void setBindAliPayAccount(String bindAliPayAccount) {
    this.bindAliPayAccount = bindAliPayAccount;
  }

  public Integer getGiftStatus() {
    return giftStatus;
  }

  public void setGiftStatus(Integer giftStatus) {
    this.giftStatus = giftStatus;
  }

  public Double getGiftTotalIncome() {
    return giftTotalIncome;
  }

  public void setGiftTotalIncome(Double giftTotalIncome) {
    this.giftTotalIncome = giftTotalIncome;
  }
}
