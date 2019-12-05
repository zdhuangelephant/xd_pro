package com.xiaodou.oms.entity.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderItem implements Serializable {

  private static final long serialVersionUID = -7154907710214930407L;

  /** 订单条目主键 **/
  private String id;

  /** 大订单号(不与具体的数据库字段做绑定) **/
  private String gorderId;

  /** 小订单号 **/
  private String orderId;

  /** 商品id **/
  private Long prctId;

  /** 货品id **/
  private Long subPrctId;

  /** 商家商品ID **/
  private String merchantPrctId;

  /**
   * 商家商品Name
   */
  private String merchantPrctName;

  /** 商家货品ID **/
  private String subMerchantPrctId;

  /**
   * 商家货品name
   */
  private String subMerchantPrctName;

  /** 商品类型 **/
  private String productType;

  /** 商品名称 **/
  private String productName;

  /** 商品单价 **/
  private BigDecimal unitPrice;

  /** 购买数量 **/
  private Integer buyCount;

  /** 商品应付金额 **/
  private BigDecimal itemOrderAmount;

  /** 商品原始应付金额 **/
  private BigDecimal originalItemPayAmount;

  /** 商品实际支付金额 **/
  private BigDecimal itemPayAmount;

  /** 商品成本价 **/
  private BigDecimal itemCostAmount;

  /** 商品手续费/净利润 **/
  private BigDecimal itemFee;

  /** 如果发生了退款应该退多少钱 **/
  private BigDecimal refundAmount;

  /** 订单条目创建时间 **/
  private Timestamp createTime;

  /** 最近一次更新时间 **/
  private Timestamp updateTime;

  /** 是否能退货 **/
  private Integer canRefund;

  /** 最晚退货时间 **/
  private Timestamp refundDeadline;

  /** 退货状态 0正常，1申请退货，2退货审核通过，3退货审核不通过，4退货中，5退款成功，6退款失败 **/
  private Integer refundStatus;

  /** 是否能换货 **/
  private Integer canExchange;

  /** 最晚换货时间 **/
  private Timestamp exchangeDeadline;

  /** 换货状态 0正常，1申请换货，2换货审核通过，3换货审核不通过，4换货退回，5换货发货中，6换货成功 **/
  private Integer exchangeStatus;

  /** 购买成功数量 **/
  private Integer okCount;

  /** 购买成功金额 **/
  private BigDecimal okAmount;

  /** 商品在做活动或推广时所节省的费用 **/
  private BigDecimal itemSaveAmount;

  /** 活动标签，用于标识参与了那些活动，以；分割 */
  private String activityLabel;

  /** 商品积分 */
  private Integer itemPoint;

  /** 商品图片路径 */
  private String goodsImageUrl;

  /** 商家id **/
  private Integer merchantId;

  /** 买家urs账号 **/
  private String buyAccountId;

  /** 商品状态 */
  private Integer itemStatus;

  /** 关联id */
  private String relationId;

  /** misc字段 **/
  private String misc;

  /** 关联gorder */
  private Gorder gorder;

  /** 关联order */
  private Order order;

  /** 虚拟关联id */
  private String virtualRelationId;

  /** 备注信息 */
  private String note;

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public BigDecimal getItemOrderAmount() {
    return itemOrderAmount;
  }

  public void setItemOrderAmount(BigDecimal itemOrderAmount) {
    this.itemOrderAmount = itemOrderAmount;
  }

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

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Long getPrctId() {
    return prctId;
  }

  public void setPrctId(Long prctId) {
    this.prctId = prctId;
  }

  public Long getSubPrctId() {
    return subPrctId;
  }

  public void setSubPrctId(Long subPrctId) {
    this.subPrctId = subPrctId;
  }

  public String getMerchantPrctId() {
    return merchantPrctId;
  }

  public void setMerchantPrctId(String merchantPrctId) {
    this.merchantPrctId = merchantPrctId;
  }

  public String getSubMerchantPrctId() {
    return subMerchantPrctId;
  }

  public void setSubMerchantPrctId(String subMerchantPrctId) {
    this.subMerchantPrctId = subMerchantPrctId;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  public Integer getBuyCount() {
    return buyCount;
  }

  public void setBuyCount(Integer buyCount) {
    this.buyCount = buyCount;
  }

  public BigDecimal getOriginalItemPayAmount() {
    return originalItemPayAmount;
  }

  public void setOriginalItemPayAmount(BigDecimal originalItemPayAmount) {
    this.originalItemPayAmount = originalItemPayAmount;
  }

  public BigDecimal getItemPayAmount() {
    return itemPayAmount;
  }

  public void setItemPayAmount(BigDecimal itemPayAmount) {
    this.itemPayAmount = itemPayAmount;
  }

  public BigDecimal getItemCostAmount() {
    return itemCostAmount;
  }

  public void setItemCostAmount(BigDecimal itemCostAmount) {
    this.itemCostAmount = itemCostAmount;
  }

  public BigDecimal getItemFee() {
    return itemFee;
  }

  public void setItemFee(BigDecimal itemFee) {
    this.itemFee = itemFee;
  }

  public BigDecimal getRefundAmount() {
    return refundAmount;
  }

  public void setRefundAmount(BigDecimal refundAmount) {
    this.refundAmount = refundAmount;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
  }

  public Integer getCanRefund() {
    return canRefund;
  }

  public void setCanRefund(Integer canRefund) {
    this.canRefund = canRefund;
  }

  public Timestamp getRefundDeadline() {
    return refundDeadline;
  }

  public void setRefundDeadline(Timestamp refundDeadline) {
    this.refundDeadline = refundDeadline;
  }

  public Integer getRefundStatus() {
    return refundStatus;
  }

  public void setRefundStatus(Integer refundStatus) {
    this.refundStatus = refundStatus;
  }

  public Integer getCanExchange() {
    return canExchange;
  }

  public void setCanExchange(Integer canExchange) {
    this.canExchange = canExchange;
  }

  public Timestamp getExchangeDeadline() {
    return exchangeDeadline;
  }

  public void setExchangeDeadline(Timestamp exchangeDeadline) {
    this.exchangeDeadline = exchangeDeadline;
  }

  public Integer getExchangeStatus() {
    return exchangeStatus;
  }

  public void setExchangeStatus(Integer exchangeStatus) {
    this.exchangeStatus = exchangeStatus;
  }

  public Integer getOkCount() {
    return okCount;
  }

  public void setOkCount(Integer okCount) {
    this.okCount = okCount;
  }

  public BigDecimal getOkAmount() {
    return okAmount;
  }

  public void setOkAmount(BigDecimal okAmount) {
    this.okAmount = okAmount;
  }

  public BigDecimal getItemSaveAmount() {
    return itemSaveAmount;
  }

  public void setItemSaveAmount(BigDecimal itemSaveAmount) {
    this.itemSaveAmount = itemSaveAmount;
  }

  public String getActivityLabel() {
    return activityLabel;
  }

  public void setActivityLabel(String activityLabel) {
    this.activityLabel = activityLabel;
  }

  public Integer getItemPoint() {
    return itemPoint;
  }

  public void setItemPoint(Integer itemPoint) {
    this.itemPoint = itemPoint;
  }

  public String getGoodsImageUrl() {
    return goodsImageUrl;
  }

  public void setGoodsImageUrl(String goodsImageUrl) {
    this.goodsImageUrl = goodsImageUrl;
  }

  public Integer getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(Integer merchantId) {
    this.merchantId = merchantId;
  }

  public String getBuyAccountId() {
    return buyAccountId;
  }

  public void setBuyAccountId(String buyAccountId) {
    this.buyAccountId = buyAccountId;
  }

  public Integer getItemStatus() {
    return itemStatus;
  }

  public void setItemStatus(Integer itemStatus) {
    this.itemStatus = itemStatus;
  }

  public String getRelationId() {
    return relationId;
  }

  public void setRelationId(String relationId) {
    this.relationId = relationId;
  }

  public String getMisc() {
    return misc;
  }

  public void setMisc(String misc) {
    this.misc = misc;
  }

  public Gorder getGorder() {
    return gorder;
  }

  public void setGorder(Gorder gorder) {
    this.gorder = gorder;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public String getVirtualRelationId() {
    return virtualRelationId;
  }

  public void setVirtualRelationId(String virtualRelationId) {
    this.virtualRelationId = virtualRelationId;
  }

  /**
   * getter method for merchantPrctName
   * 
   * @return the merchantPrctName
   */
  public String getMerchantPrctName() {
    return merchantPrctName;
  }

  /**
   * setter method for merchantPrctName
   * 
   * @Description the merchantPrctName to set
   * @param merchantPrctName
   */
  public void setMerchantPrctName(String merchantPrctName) {
    this.merchantPrctName = merchantPrctName;
  }

  /**
   * getter method for subMerchantPrctName
   * 
   * @return the subMerchantPrctName
   */
  public String getSubMerchantPrctName() {
    return subMerchantPrctName;
  }

  /**
   * setter method for subMerchantPrctName
   * 
   * @Description the subMerchantPrctName to set
   * @param subMerchantPrctName
   */
  public void setSubMerchantPrctName(String subMerchantPrctName) {
    this.subMerchantPrctName = subMerchantPrctName;
  }
}
