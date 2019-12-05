package com.xiaodou.oms.entity.order;

import java.sql.Timestamp;

public class ShopTag {
  /** 主键  */
  private String id;
  /** uuid */
  private String tag;
  /** 处理状态  */
  private String status;
  /** 判重类型  */
  private String type;
  /** 业务类型  */
  private String productType;
  /** 创建时间  */
  private Timestamp createTime;
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getTag() {
    return tag;
  }
  public void setTag(String tag) {
    this.tag = tag;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public Timestamp getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }
  public String getProductType() {
    return productType;
  }
  public void setProductType(String productType) {
    this.productType = productType;
  }
}
