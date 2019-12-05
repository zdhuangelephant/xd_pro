package com.xiaodou.server.mapi.request.payment;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class AliCallbackRequest extends MapiBaseRequest {
  @NotEmpty
  private String notify_id;
  @NotEmpty
  private String out_trade_no;// 商户网站唯一订单号


  public String getNotify_id() {
    return notify_id;
  }

  public void setNotify_id(String notify_id) {
    this.notify_id = notify_id;
  }

  public String getOut_trade_no() {
    return out_trade_no;
  }

  public void setOut_trade_no(String out_trade_no) {
    this.out_trade_no = out_trade_no;
  }
}
