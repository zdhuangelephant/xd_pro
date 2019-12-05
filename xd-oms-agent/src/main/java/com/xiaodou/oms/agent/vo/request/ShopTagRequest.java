package com.xiaodou.oms.agent.vo.request;

public class ShopTagRequest extends BaseRequest{
  private String type;
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
