package com.xiaodou.wallet.model;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

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
  @Column(isMajor = true)
  private Long id;
  /** userId 用户ID */
  @Column(canUpdate = false)
  private String userId;
  /** accountClassify 账户分类 */
  @Column(canUpdate = false)
  private String accountClassify;
  /** businessType 业务类型 */
  @Column(canUpdate = false)
  private Integer businessType;
  /** accountAmount 账户余额 */
  private Double accountAmount;
  /** createTime 账户创建时间 */
  @Column(canUpdate = false, betweenScope = true)
  private Timestamp createTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getAccountClassify() {
    return accountClassify;
  }

  public void setAccountClassify(String accountClassify) {
    this.accountClassify = accountClassify;
  }

  public Integer getBusinessType() {
    return businessType;
  }

  public void setBusinessType(Integer businessType) {
    this.businessType = businessType;
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

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(AccountWallet.class, "xd_account_wallet",
        "/Users/zhaodan/xiaodou/server/WorkSpace/xd-wallet-zkj/src/main/resources/conf/mybatis").buildXml();
  }
}
