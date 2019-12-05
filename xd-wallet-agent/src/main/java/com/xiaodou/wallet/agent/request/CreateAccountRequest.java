package com.xiaodou.wallet.agent.request;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.validator.annotion.NotEmpty;


public class CreateAccountRequest extends BaseRequest {

  /** businessType 业务类型 */
  @NotEmpty
  private List<Integer> businessType = Lists.newArrayList();

  public List<Integer> getBusinessType() {
    return businessType;
  }

  public void setBusinessType(List<Integer> businessType) {
    this.businessType = businessType;
  }

}
