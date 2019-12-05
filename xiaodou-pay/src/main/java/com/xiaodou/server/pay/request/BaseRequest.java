package com.xiaodou.server.pay.request;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.server.pay.constant.PayConstant;
import com.xiaodou.server.pay.web.sign.SignMessConf;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseSignPojo;

public abstract class BaseRequest extends BaseSignPojo {
  /** businessType 业务类型 */
  @NotEmpty
  @LegalValueList({PayConstant.BUSINESS_TYPE_ST, PayConstant.BUSINESS_TYPE_WALLET,
      PayConstant.BUSINESS_TYPE_MOSHARE})
  private Integer businessType;

  /** merchantId 商户ID */
  @NotEmpty
  protected String merchantId;

  public String getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  public Integer getBusinessType() {
    return businessType;
  }

  public void setBusinessType(Integer businessType) {
    this.businessType = businessType;
  }

  public String getKey() {
    return SignMessConf.getKey(merchantId);
  }

  public String toString0() {
    return String.format("{%s=%s}", this.getClass().getSimpleName(), FastJsonUtil.toJson(this));
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }

}
