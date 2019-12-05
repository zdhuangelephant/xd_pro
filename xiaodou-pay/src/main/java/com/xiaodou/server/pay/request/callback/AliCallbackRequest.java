package com.xiaodou.server.pay.request.callback;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

/**
 * @name AliCallbackRequest CopyRright (c) 2018 by 李德洪
 * 
 * @author 李德洪
 * @date 2018年2月7日
 * @description 不论支付宝是POST传输还是GET传输，绝大多数接口除sign及sign_type以外，<br>
 *              <strong>获得的参数集合皆是要参与签名的参数<strong>。<br>
 *              具体返回参数请以接口技术文档说明及实际返回的信息为准。以即时到账的异步通知内容为例,构造下面入参
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AliCallbackRequest extends BaseValidatorPojo {
  /** notify_id 通知校验ID */
  @NotEmpty
  private String notify_id;
  @NotEmpty
  private String out_trade_no;// 商户网站唯一订单号
  @NotEmpty
  private String sign;

  // --------------------------------------------------
  /** notify_time 通知时间 通知的发送时间。格式为yyyy-MM-dd HH:mm:ss */
  @NotEmpty
  private String notify_time;
  /** notify_type 通知的类型。 */
  @NotEmpty
  private String notify_type;
  @NotEmpty
  private String sign_type;
  @NotEmpty
  private String total_fee;
  @NotEmpty
  private String price;
  @NotEmpty
  private String trade_no;
  private String subject;
  private String body;
  private String quantity;
  @NotEmpty
  private String trade_status;
  @NotEmpty
  private String seller_email;
  @NotEmpty
  private String seller_id;
  @NotEmpty
  private String buyer_id;
  @NotEmpty
  private String buyer_email;
  private String gmt_create;
  private String gmt_payment;
  private String is_total_fee_adjust;
  private String use_coupon;
  private String extra_common_param;

  private String _input_charset;
}
