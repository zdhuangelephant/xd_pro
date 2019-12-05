package com.xiaodou.server.pay.payplatform.dc;

import com.xiaodou.summer.vo.out.ResultInfo;

/**
 * @name @see com.xiaodou.server.pay.thirdpayplaform.PayStatusResponse.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月26日
 * @description 三方平台支付状态查询结果
 * @version 1.0
 */
public class DcPayStatusResponse extends ResultInfo {

  /** thirdPlatformId 三方支付平台交易号 */
  private String thirdPlatformTradeNo;
  /** userThirdPlatformId 用户三方平台ID */
  private String userThirdPlatformId;
  /** payStatus 支付状态 */
  private PlatformPayStatus payStatus;
  /** payStatusDesc 支付状态描述 */
  private String payStatusDesc;
  /** finishTime 支付完成时间 */
  private String finishTime;

  public String getThirdPlatformTradeNo() {
    return thirdPlatformTradeNo;
  }

  public void setThirdPlatformTradeNo(String thirdPlatformTradeNo) {
    this.thirdPlatformTradeNo = thirdPlatformTradeNo;
  }

  public String getUserThirdPlatformId() {
    return userThirdPlatformId;
  }

  public void setUserThirdPlatformId(String userThirdPlatformId) {
    this.userThirdPlatformId = userThirdPlatformId;
  }

  public PlatformPayStatus getPayStatus() {
    return payStatus;
  }

  public void setPayStatus(PlatformPayStatus payStatus) {
    this.payStatus = payStatus;
  }

  public String getPayStatusDesc() {
    return payStatusDesc;
  }

  public void setPayStatusDesc(String payStatusDesc) {
    this.payStatusDesc = payStatusDesc;
  }

  public String getFinishTime() {
    return finishTime;
  }

  public void setFinishTime(String finishTime) {
    this.finishTime = finishTime;
  }

}
