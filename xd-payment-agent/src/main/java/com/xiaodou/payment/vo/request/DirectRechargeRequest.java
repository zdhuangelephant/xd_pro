package com.xiaodou.payment.vo.request;

/**
 * 直接领取红包，并充入现金账号
 *
 * Date: 2014/9/15
 * Time: 11:53
 *
 * @author Tian.Dong
 */
public class DirectRechargeRequest {
  /**
   * 活动id
   */
  private String activityId;
  /**
   * 会员卡号
   */
  private String cardNo;

  public String getActivityId() {
    return activityId;
  }

  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }

  public String getCardNo() {
    return cardNo;
  }

  public void setCardNo(String cardNo) {
    this.cardNo = cardNo;
  }
}
