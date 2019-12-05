package com.xiaodou.oms.vo.input.order;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * Order查询参数Pojo
 * </p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月2日
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

  /**
   * 所属产品线
   */
  private String productLine;

  /**
   * 业务类型/产品类型 *
   */
  private String productType;

  /**
   * 业务类型/产品类型 列表 *
   */
  private List<String> productTypeList;

  /**
   * 订单ID *
   */
  private String id;

  /**
   * 订单ID列表 *
   */
  private List<String> listId;

  /**
   * 最小订单ID
   */
  private String idLower;

  /**
   * 最大订单ID
   */
  private String idUpper;

  /**
   * 所属大订单ID *
   */
  private String gorderId;

  /**
   * 最早创建时间
   */
  private Timestamp orderTimeLower;

  /**
   * 最迟创建时间
   */
  private Timestamp orderTimeUpper;

  /**
   * 下单用户urs账户 *
   */
  private String buyAccountId;

  /**
   * 订单状态 *
   */
  private Integer orderStatus;

  /**
   * 订单状态列表 *
   */
  private List<Integer> listOrderStatus;

  /**
   * 关单原因
   */
  private List<String> listClosedReason;

  /**
   * 不包含的关单原因
   */
  private List<String> notInListClosedReason;

  /**
   * 不包含的需要展示的关单原因
   */
  private List<String> closedReasonListToShow;

  /**
   * 最早修改时间
   */
  private Timestamp updateTimeLower;

  /**
   * 最迟修改时间
   */
  private Timestamp updateTimeUpper;

  /**
   * 商家id *
   */
  private Integer merchantId;

  /**
   * 供应商ID列表
   */
  private List<Integer> listMerchantId;

  /**
   * 商家订单id *
   */
  private String merchantOrderId;

  /**
   * 搜索关键词 *
   */
  private String keywords;

  /**
   * 关单原因
   */
  private String closedReason;

  private String notClosedReason;

  /**
   * ip
   */
  private String orderIp;

  /** 下面是国际酒店新加查询条件 **/

  /**
   * 入店时间 *
   */
  private Timestamp deliveryBeginTimeLower;

  private Timestamp deliveryBeginTimeUpper;

  /**
   * 离店时间
   */
  private Timestamp deliveryEndTimeLower;

  private Timestamp deliveryEndTimeUpper;

  /**
   * 酒店前台支付增加的查询条件
   * 供应商名称(酒店shotelid)
   */
  private String merchantName;

  /**
   * 下单时间排序 取值 DESC/ASC *
   */
  private String createTimeOrder;

  /**
   * 入店时间排序, 取值 DESC ASC
   */
  private String deliveryBeginTimeOrder;

  /**
   * 离店时间排序 取值 DESC ASC
   */
  private String deliveryEndTimeOrder;


  /**
   * 最早关单时间
   */
  private Timestamp preCloseTimeLower;

  /**
   * 最迟关单时间
   */
  private Timestamp preCloseTimeUpper;

  /**
   * 下单时间倒序 *
   */
  private String orderDescend;

  /**
   * 下单时间正序 *
   */
  private String orderAscend;

  /**
   * NBAPI分销商id
   */
  private String distributorOrderId;
  
 /**
 * getter method for distributorOrderId
 * @return the distributorOrderId
 */
public String getDistributorOrderId() {
    return distributorOrderId;
}

/**
 * setter method for distributorOrderId
 * @Description the distributorOrderId to set
 * @param distributorOrderId 
 */
public void setDistributorOrderId(String distributorOrderId) {
    this.distributorOrderId = distributorOrderId;
}

public String getNotClosedReason() {
    return notClosedReason;
  }

  public void setNotClosedReason(String notClosedReason) {
    this.notClosedReason = notClosedReason;
  }

  public List<String> getClosedReasonListToShow() {
    return closedReasonListToShow;
  }

  public void setClosedReasonListToShow(List<String> closedReasonListToShow) {
    this.closedReasonListToShow = closedReasonListToShow;
  }

  public List<String> getNotInListClosedReason() {
    return notInListClosedReason;
  }

  public void setNotInListClosedReason(List<String> notInListClosedReason) {
    this.notInListClosedReason = notInListClosedReason;
  }

  public String getOrderIp() {
    return orderIp;
  }

  public void setOrderIp(String orderIp) {
    this.orderIp = orderIp;
  }

  public List<String> getListClosedReason() {
    return listClosedReason;
  }

  public void setListClosedReason(List<String> listClosedReason) {
    this.listClosedReason = listClosedReason;
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

  public List<String> getProductTypeList() {
    return productTypeList;
  }

  public void setProductTypeList(List<String> productTypeList) {
    this.productTypeList = productTypeList;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<String> getListId() {
    return listId;
  }

  public void setListId(List<String> listId) {
    this.listId = listId;
  }

  public String getIdLower() {
    return idLower;
  }

  public void setIdLower(String idLower) {
    this.idLower = idLower;
  }

  public String getIdUpper() {
    return idUpper;
  }

  public void setIdUpper(String idUpper) {
    this.idUpper = idUpper;
  }

  public String getGorderId() {
    return gorderId;
  }

  public void setGorderId(String gorderId) {
    this.gorderId = gorderId;
  }

  public Timestamp getOrderTimeLower() {
    return orderTimeLower;
  }

  public void setOrderTimeLower(Timestamp orderTimeLower) {
    this.orderTimeLower = orderTimeLower;
  }

  public Timestamp getOrderTimeUpper() {
    return orderTimeUpper;
  }

  public void setOrderTimeUpper(Timestamp orderTimeUpper) {
    this.orderTimeUpper = orderTimeUpper;
  }

  public String getBuyAccountId() {
    return buyAccountId;
  }

  public void setBuyAccountId(String buyAccountId) {
    this.buyAccountId = buyAccountId;
  }

  public Integer getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(Integer orderStatus) {
    this.orderStatus = orderStatus;
  }

  public List<Integer> getListOrderStatus() {
    return listOrderStatus;
  }

  public void setListOrderStatus(List<Integer> listOrderStatus) {
    this.listOrderStatus = listOrderStatus;
  }

  public Timestamp getUpdateTimeLower() {
    return updateTimeLower;
  }

  public void setUpdateTimeLower(Timestamp updateTimeLower) {
    this.updateTimeLower = updateTimeLower;
  }

  public Timestamp getUpdateTimeUpper() {
    return updateTimeUpper;
  }

  public void setUpdateTimeUpper(Timestamp updateTimeUpper) {
    this.updateTimeUpper = updateTimeUpper;
  }

  public Integer getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(Integer merchantId) {
    this.merchantId = merchantId;
  }

  public List<Integer> getListMerchantId() {
    return listMerchantId;
  }

  public void setListMerchantId(List<Integer> listMerchantId) {
    this.listMerchantId = listMerchantId;
  }

  public String getMerchantOrderId() {
    return merchantOrderId;
  }

  public void setMerchantOrderId(String merchantOrderId) {
    this.merchantOrderId = merchantOrderId;
  }

  public String getKeywords() {
    return keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public String getClosedReason() {
    return closedReason;
  }

  public void setClosedReason(String closedReason) {
    this.closedReason = closedReason;
  }

  /**
   * getter method for deliveryBeginTimeLower
   *
   * @return the deliveryBeginTimeLower
   */
  public Timestamp getDeliveryBeginTimeLower() {
    return deliveryBeginTimeLower;
  }

  /**
   * setter method for deliveryBeginTimeLower
   *
   * @param deliveryBeginTimeLower
   * @Description the deliveryBeginTimeLower to set
   */
  public void setDeliveryBeginTimeLower(Timestamp deliveryBeginTimeLower) {
    this.deliveryBeginTimeLower = deliveryBeginTimeLower;
  }

  /**
   * getter method for deliveryBeginTimeUpper
   *
   * @return the deliveryBeginTimeUpper
   */
  public Timestamp getDeliveryBeginTimeUpper() {
    return deliveryBeginTimeUpper;
  }

  /**
   * setter method for deliveryBeginTimeUpper
   *
   * @param deliveryBeginTimeUpper
   * @Description the deliveryBeginTimeUpper to set
   */
  public void setDeliveryBeginTimeUpper(Timestamp deliveryBeginTimeUpper) {
    this.deliveryBeginTimeUpper = deliveryBeginTimeUpper;
  }

  /**
   * getter method for deliveryEndTimeLower
   *
   * @return the deliveryEndTimeLower
   */
  public Timestamp getDeliveryEndTimeLower() {
    return deliveryEndTimeLower;
  }

  /**
   * setter method for deliveryEndTimeLower
   *
   * @param deliveryEndTimeLower
   * @Description the deliveryEndTimeLower to set
   */
  public void setDeliveryEndTimeLower(Timestamp deliveryEndTimeLower) {
    this.deliveryEndTimeLower = deliveryEndTimeLower;
  }

  /**
   * getter method for deliveryEndTimeUpper
   *
   * @return the deliveryEndTimeUpper
   */
  public Timestamp getDeliveryEndTimeUpper() {
    return deliveryEndTimeUpper;
  }

  /**
   * setter method for deliveryEndTimeUpper
   *
   * @param deliveryEndTimeUpper
   * @Description the deliveryEndTimeUpper to set
   */
  public void setDeliveryEndTimeUpper(Timestamp deliveryEndTimeUpper) {
    this.deliveryEndTimeUpper = deliveryEndTimeUpper;
  }

  /**
   * getter method for merchantName
   *
   * @return the merchantName
   */
  public String getMerchantName() {
    return merchantName;
  }

  /**
   * setter method for merchantName
   *
   * @param merchantName
   * @Description the merchantName to set
   */
  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  /**
   * getter method for createTimeOrder
   *
   * @return the createTimeOrder
   */
  public String getCreateTimeOrder() {
    return createTimeOrder;
  }

  /**
   * setter method for createTimeOrder
   *
   * @param createTimeOrder
   * @Description the createTimeOrder to set
   */
  public void setCreateTimeOrder(String createTimeOrder) {
    this.createTimeOrder = createTimeOrder;
  }

  /**
   * getter method for deliveryBeginTimeOrder
   *
   * @return the deliveryBeginTimeOrder
   */
  public String getDeliveryBeginTimeOrder() {
    return deliveryBeginTimeOrder;
  }

  /**
   * setter method for deliveryBeginTimeOrder
   *
   * @param deliveryBeginTimeOrder
   * @Description the deliveryBeginTimeOrder to set
   */
  public void setDeliveryBeginTimeOrder(String deliveryBeginTimeOrder) {
    this.deliveryBeginTimeOrder = deliveryBeginTimeOrder;
  }

  /**
   * getter method for deliveryEndTimeOrder
   *
   * @return the deliveryEndTimeOrder
   */
  public String getDeliveryEndTimeOrder() {
    return deliveryEndTimeOrder;
  }

  /**
   * setter method for deliveryEndTimeOrder
   *
   * @param deliveryEndTimeOrder
   * @Description the deliveryEndTimeOrder to set
   */
  public void setDeliveryEndTimeOrder(String deliveryEndTimeOrder) {
    this.deliveryEndTimeOrder = deliveryEndTimeOrder;
  }

  public Timestamp getPreCloseTimeLower() {
    return preCloseTimeLower;
  }

  public void setPreCloseTimeLower(Timestamp preCloseTimeLower) {
    this.preCloseTimeLower = preCloseTimeLower;
  }

  public Timestamp getPreCloseTimeUpper() {
    return preCloseTimeUpper;
  }

  public void setPreCloseTimeUpper(Timestamp preCloseTimeUpper) {
    this.preCloseTimeUpper = preCloseTimeUpper;
  }

  public String getOrderDescend() {
    return orderDescend;
  }

  public void setOrderDescend(String orderDescend) {
    this.orderDescend = orderDescend;
  }

  public String getOrderAscend() {
    return orderAscend;
  }

  public void setOrderAscend(String orderAscend) {
    this.orderAscend = orderAscend;
  }
}
