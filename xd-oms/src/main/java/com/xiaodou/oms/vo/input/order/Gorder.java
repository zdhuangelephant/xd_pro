package com.xiaodou.oms.vo.input.order;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>Gorder查询参数Pojo</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月1日
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Gorder{
  
  /**
   * 所属业务线
   */
  private String productLine;
  
  /**
   * 业务类型/产品类型
   */
  private String productType;
  

  /**
   * 业务类型/产品类型列表
   */
  private List<String> productTypeList;
  
  /**
   * 大订单ID*
   */
  private String id;
  
  /**
   * 大订单ID列表
   */
  private List<String> listId;
  
  /**
   * 最小大订单ID
   */
  private String idLower;
  
  /**
   * 最大大订单ID
   */
  private String idUpper;

  /**
   * 最早创建时间
   */
  private Timestamp gorderTimeLower;
  
  /**
   * 最迟创建时间
   */
  private Timestamp gorderTimeUpper;

  /**
   * 用户ID*
   */
  private String buyAccountId;
  
  /**
   * 大订单状态*
   */
  private Integer gorderStatus;
  
  /**
   * 大订单状态列表*
   */
  private List<Integer> listGorderStatus;

  /**
   * 实际支付方式*
   */
  private String realPayMethod;

  /**
   * 支付系统订单id*
   */
  private String payOrderId;

  /**
   * 最早支付成功时间
   */
  private Timestamp paySuccessTimeLower;

  /**
   * 最迟支付成功时间
   */
  private Timestamp paySuccessTimeUpper;

  /**
   *
   */
  private Timestamp preCloseTimeLower;

  /**
   *
   */
  private Timestamp preCloseTimeUpper;

  /**
   * 收货人邮箱*
   */
  private String receiverEmail;

  /**
   * 收货人电话*
   */
  private String receiverPhone;

  /**
   * 是否需要发票*
   */
  private Integer isNeedInvoice;

  /**
   * 收货人姓名*
   */
  private String receiverName;

  /**
   * 订单外部来源*
   */
  private String outerOrigin;

  /**
   * 客户端类型：10web端；20客户端
   */
  private String clientType;
  
  /**
   * 最早修改时间
   */
  private Timestamp updateTimeLower;
  
  /**
   * 最迟修改时间
   */
  private Timestamp updateTimeUpper;
  
  /**
   * 1正常订单；2已删除订单
   */
  private Integer tags;

  /**
   * 订单来源,满足DMO需求
   */
  private String orderFrom;
  
  /**
   * 下单时间排序 DESC/ASC
   */
  private String createTimeOrder;
  

  public Integer getTags() {
    return tags;
  }

  public void setTags(Integer tags) {
    this.tags = tags;
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
     * @Description the createTimeOrder to set
     * @param createTimeOrder
     */
    public void setCreateTimeOrder(String createTimeOrder) {
	this.createTimeOrder = createTimeOrder;
    }

    public List<Integer> getListGorderStatus() {
	return listGorderStatus;
    }

  public void setListGorderStatus(List<Integer> listGorderStatus) {
    this.listGorderStatus = listGorderStatus;
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

  public Timestamp getGorderTimeLower() {
    return gorderTimeLower;
  }

  public void setGorderTimeLower(Timestamp gorderTimeLower) {
    this.gorderTimeLower = gorderTimeLower;
  }

  public Timestamp getGorderTimeUpper() {
    return gorderTimeUpper;
  }

  public void setGorderTimeUpper(Timestamp gorderTimeUpper) {
    this.gorderTimeUpper = gorderTimeUpper;
  }

  public String getBuyAccountId() {
    return buyAccountId;
  }

  public void setBuyAccountId(String buyAccountId) {
    this.buyAccountId = buyAccountId;
  }

  public Integer getGorderStatus() {
    return gorderStatus;
  }

  public void setGorderStatus(Integer gorderStatus) {
    this.gorderStatus = gorderStatus;
  }

  public String getRealPayMethod() {
    return realPayMethod;
  }

  public void setRealPayMethod(String realPayMethod) {
    this.realPayMethod = realPayMethod;
  }

  public String getPayOrderId() {
    return payOrderId;
  }

  public void setPayOrderId(String payOrderId) {
    this.payOrderId = payOrderId;
  }

  public Timestamp getPaySuccessTimeLower() {
    return paySuccessTimeLower;
  }

  public void setPaySuccessTimeLower(Timestamp paySuccessTimeLower) {
    this.paySuccessTimeLower = paySuccessTimeLower;
  }

  public Timestamp getPaySuccessTimeUpper() {
    return paySuccessTimeUpper;
  }

  public void setPaySuccessTimeUpper(Timestamp paySuccessTimeUpper) {
    this.paySuccessTimeUpper = paySuccessTimeUpper;
  }

  public String getReceiverEmail() {
    return receiverEmail;
  }

  public void setReceiverEmail(String receiverEmail) {
    this.receiverEmail = receiverEmail;
  }

  public String getReceiverPhone() {
    return receiverPhone;
  }

  public void setReceiverPhone(String receiverPhone) {
    this.receiverPhone = receiverPhone;
  }

  public Integer getIsNeedInvoice() {
    return isNeedInvoice;
  }

  public void setIsNeedInvoice(Integer isNeedInvoice) {
    this.isNeedInvoice = isNeedInvoice;
  }

  public String getReceiverName() {
    return receiverName;
  }

  public void setReceiverName(String receiverName) {
    this.receiverName = receiverName;
  }

  public String getOuterOrigin() {
    return outerOrigin;
  }

  public void setOuterOrigin(String outerOrigin) {
    this.outerOrigin = outerOrigin;
  }

  public String getClientType() {
    return clientType;
  }

  public void setClientType(String clientType) {
    this.clientType = clientType;
  }

  public String getOrderFrom() {
    return orderFrom;
  }

  public void setOrderFrom(String orderFrom) {
    this.orderFrom = orderFrom;
  }
}
