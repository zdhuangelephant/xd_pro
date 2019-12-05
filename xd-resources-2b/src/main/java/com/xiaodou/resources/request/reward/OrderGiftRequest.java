package com.xiaodou.resources.request.reward;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class OrderGiftRequest extends BaseRequest {
  /** resourceId 赞赏资源ID */
  @NotEmpty
  private String resourceId;
  /* targetUserId 目标用户id */
  @NotEmpty
  private String targetUserId;
  /* giftType 赞赏类型 */
  @NotEmpty
  @LegalValueList({"0", "1", "2"})
  private Integer giftType;
  /* giftMoney 支付金额 */
  private Double giftMoney;
  /** payType 支付类型 */
  private String payType;
  /* rate 汇率 */
  private String rate;

  public String getRate() {
    return rate;
  }

  public void setRate(String rate) {
    this.rate = rate;
  }

  public String getPayType() {
    return payType;
  }

  public void setPayType(String payType) {
    this.payType = payType;
  }

  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  public String getTargetUserId() {
    return targetUserId;
  }

  public void setTargetUserId(String targetUserId) {
    this.targetUserId = targetUserId;
  }

  public Integer getGiftType() {
    return giftType;
  }

  public void setGiftType(Integer giftType) {
    this.giftType = giftType;
  }

  public Double getGiftMoney() {
    return giftMoney;
  }

  public void setGiftMoney(Double giftMoney) {
    this.giftMoney = giftMoney;
  }

}
