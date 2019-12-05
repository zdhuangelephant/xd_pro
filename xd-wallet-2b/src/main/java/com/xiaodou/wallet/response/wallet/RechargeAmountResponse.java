package com.xiaodou.wallet.response.wallet;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.wallet.response.BaseResponse;
import com.xiaodou.wallet.response.WalletResType;

/**
 * @name @see com.xiaodou.wallet.response.wallet.RechargeAmountResponse.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月3日
 * @description 充值响应
 * @version 1.0
 */
public class RechargeAmountResponse extends BaseResponse {

  public RechargeAmountResponse(ResultType type) {
    super(type);
  }

  public RechargeAmountResponse(WalletResType type) {
    super(type);
  }

  /** payStatus 支付状态 */
  private String payStatus;
  /** payInfo 支付结果信息 */
  private String payInfo;
  /** payUrl 支付地址 */
  private String payUrl;

  public String getPayStatus() {
    return payStatus;
  }

  public void setPayStatus(String payStatus) {
    this.payStatus = payStatus;
  }

  public String getPayInfo() {
    return payInfo;
  }

  public void setPayInfo(String payInfo) {
    this.payInfo = payInfo;
  }

  public String getPayUrl() {
    return payUrl;
  }

  public void setPayUrl(String payUrl) {
    this.payUrl = payUrl;
  }

}
