package com.xiaodou.wallet.request.wallet;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.wallet.constant.WalletConstant;
import com.xiaodou.wallet.request.BaseRequest;

public class CreateAccountRequest extends BaseRequest {

  /** businessType 业务类型 */
  @NotEmpty
  @LegalValueList({WalletConstant.BUSINESS_TYPE_ZKJ, WalletConstant.BUSINESS_TYPE_MOSHARE})
  private List<Integer> businessType = Lists.newArrayList();

  public List<Integer> getBusinessType() {
    return businessType;
  }

  public void setBusinessType(List<Integer> businessType) {
    this.businessType = businessType;
  }
}