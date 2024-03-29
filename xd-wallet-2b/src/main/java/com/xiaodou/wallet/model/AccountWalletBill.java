package com.xiaodou.wallet.model;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * @name @see com.xiaodou.wallet.model.AccountWalletBill.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月2日
 * @description 账户钱包账单模型
 * @version 1.0
 */
public class AccountWalletBill {

  /** id 主键 */
  @Column(isMajor = true)
  private Long id;
  /** walletId 钱包ID */
  @Column(canUpdate = false)
  private Long walletId;
  /** tradeNo 支付系统交易号 */
  @Column(canUpdate = false)
  private String tradeNo;
  /** operateType 操作类型 */
  private Integer operateType;
  /** operateDesc 操作类型明细 */
  private String operateDesc;
  /** operateTag 操作行为备注 */
  @Column(canUpdate = false)
  private String operateTag;
  /** operateAmount 操作金额 */
  @Column(canUpdate = false)
  private Double operateAmount;
  /** operateCount 操作数量 */
  @Column(canUpdate = false)
  private Double operateCount;
  /** operateTime 操作时间 */
  @Column(betweenScope = true, canUpdate = false)
  private Timestamp operateTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getBillNo() {
    return id;
  }

  public Long getWalletId() {
    return walletId;
  }

  public void setWalletId(Long walletId) {
    this.walletId = walletId;
  }

  public String getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(String tradeNo) {
    this.tradeNo = tradeNo;
  }

  public Integer getOperateType() {
    return operateType;
  }

  public void setOperateType(Integer operateType) {
    this.operateType = operateType;
  }

  public String getOperateDesc() {
    return operateDesc;
  }

  public void setOperateDesc(String operateDesc) {
    this.operateDesc = operateDesc;
  }

  public String getOperateTag() {
    return operateTag;
  }

  public void setOperateTag(String operateTag) {
    this.operateTag = operateTag;
  }

  public Double getOperateAmount() {
    return operateAmount;
  }

  public void setOperateAmount(Double operateAmount) {
    this.operateAmount = operateAmount;
  }

  public Double getOperateCount() {
    return operateCount;
  }

  public void setOperateCount(Double operateCount) {
    this.operateCount = operateCount;
  }

  public Timestamp getOperateTime() {
    return operateTime;
  }

  public void setOperateTime(Timestamp operateTime) {
    this.operateTime = operateTime;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(AccountWalletBill.class, "xd_account_wallet_bill",
        "/Users/zhaodan/xiaodou/server/WorkSpace/xd-wallet-zkj/src/main/resources/conf/mybatis")
        .buildXml();
  }
}
