package com.xiaodou.course.model.order;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

public class ProductOrderModel {

  /** id 主键 */
  @Column(isMajor = true)
  private String id;
  /** gorderId 支付订单ID */
  private String gorderId;
  /** module 所属模块 */
  private String module;
  /** productLine 产品线 */
  private String productLine;
  /** productType 业务类型 */
  private String productType;
  /** uid 用户ID */
  private String uid;
  /** productId 产品ID */
  private String productId;
  /** status 状态 */
  private Integer status;
  /** createTime 创建时间 */
  @Column(betweenScope = true)
  private Timestamp createTime;
  /** finishTime 完成时间 */
  @Column(betweenScope = true)
  private Timestamp finishTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getGorderId() {
    return gorderId;
  }

  public void setGorderId(String gorderId) {
    this.gorderId = gorderId;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getProductLine() {
    return productLine;
  }

  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Timestamp getFinishTime() {
    return finishTime;
  }

  public void setFinishTime(Timestamp finishTime) {
    this.finishTime = finishTime;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(ProductOrderModel.class, "xd_course_product_order_record",
        "D:/MyWorkSpace/MyEclipse8.5/xd-course-2b/src/main/resources/conf/mybatis/order/")
        .buildXml();
  }
}
