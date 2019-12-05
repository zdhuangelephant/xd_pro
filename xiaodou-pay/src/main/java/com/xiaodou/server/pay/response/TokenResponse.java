package com.xiaodou.server.pay.response;

import com.xiaodou.server.pay.response.resultType.PaymentResType;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.server.pay.response.TokenResponse.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月26日
 * @description 支付凭证接口出参
 * @version 1.0
 */
public class TokenResponse extends BaseResponse {

  /** tradeNo 支付平台系统生成的token流水号 */
  private String tradeNo;

  public TokenResponse(ResultType resultType) {
    super(resultType);
  }

  public TokenResponse(PaymentResType resultType) {
    super(resultType);
  }

  public String getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(String tradeNo) {
    this.tradeNo = tradeNo;
  }

}
