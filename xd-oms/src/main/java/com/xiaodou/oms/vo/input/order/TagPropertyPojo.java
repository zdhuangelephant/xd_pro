package com.xiaodou.oms.vo.input.order;

import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class TagPropertyPojo extends BasePojo{
  @NotEmpty
  private String type;
  @NotEmpty
  private String productType;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }
}
