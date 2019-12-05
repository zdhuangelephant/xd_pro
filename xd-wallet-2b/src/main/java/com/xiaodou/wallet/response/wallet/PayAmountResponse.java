package com.xiaodou.wallet.response.wallet;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.wallet.response.BaseResponse;
import com.xiaodou.wallet.response.WalletResType;

/**
 * @name @see com.xiaodou.wallet.response.wallet.PayAmountResponse.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月6日
 * @description 完成支付响应
 * @version 1.0
 */
public class PayAmountResponse extends BaseResponse {

  public PayAmountResponse(ResultType type) {
    super(type);
  }

  public PayAmountResponse(WalletResType type) {
    super(type);
  }

}
