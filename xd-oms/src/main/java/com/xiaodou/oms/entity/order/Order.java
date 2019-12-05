package com.xiaodou.oms.entity.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.ValidateEntity;

/**
 * 订单实体对象
 * @author bjjpdu
 *
 */
public class Order implements Serializable{
	
	private static final long serialVersionUID = -6126519328801435278L;
	
	/**订单ID**/
	private String  id = null;
	
	/**所属大订单ID**/
	private String gorderId;
	
	/**业务类型/产品类型**/
	@NotEmpty
	private String productType;
	
	/**下订单时间**/
	@NotEmpty
	private Timestamp orderTime = null;
	
	/**订单状态**/
	@NotEmpty
	private Integer orderStatus = null;
	
	/**订单应付总金额**/
	@NotEmpty
	private BigDecimal orderAmount = null;
	
	/**订单优惠价**/
	private BigDecimal saveAmount;
	
	/**实际支付总金额**/
	private BigDecimal payAmount = null;
	
	/**运费**/
	private BigDecimal logisticsAmount = null;
	
	/**手续费/净利润**/
	private BigDecimal fee = null;
	
	/**不记活动原始应付价格**/
	private BigDecimal originalPayAmount;
	
	/**原始成本价**/
	private BigDecimal costAmount;
	
	/**下单用户urs账户**/
	private String buyAccountId = null;
	
	/**下单时预定的关闭时间**/
	private Timestamp preCloseTime;
	
	/**最近更新时间**/
	private Timestamp updateTime = null;
	
	/**打款标识**/
	private Integer settleUp = null;
	
	/**发货成功时间**/
	private Timestamp successTime = null;
	
	/**商家id**/
	private Integer merchantId = null;
	
	/**商家名称**/
	private String	merchantName = null;
	
	/**商家邮箱账号**/
	private String	merchantAccount = null;
	
	/**商家电话**/
	private String merchantTel = null;
	
	/**商家订单id**/
	private String merchantOrderId = null;
		
	/**开展活动时合作方承担的费用**/
	private BigDecimal merchantAmount;
	
	/**活动ID*/
	private Integer activityId;
	
	/**活动类型*/
	private Integer activityType;
	
	/**备注(内部人员可见)**/
	private String remark;
	
	/**订单描述信息**/
	private String orderDesc;
	
	/**下单ip**/
	private String orderIp = null;
	
	/**支付成功时间**/
	private Timestamp paySuccessTime = null;
	
	/**能否发货**/
	private Integer canDeliver = null;
	
	/**能否打款**/
	private Integer canSettleUp = null;
	
	/**能否退款**/
	private Integer canRefund = null; 
	
	/**misc字段**/
	private String misc;
	
	/**搜索关键词**/
	private String keywords;
	
	/**开始发货时间**/
	private Timestamp deliveryBeginTime;
	
	/**收货时间**/
	private Timestamp deliveryEndTime;
	
	/**此字段值相同的order,代表具有一定的关联*/
	private String relationId;
	
	/**关单原因*/
	private String closedReason;
	
	/**状态描述*/
	private String statusDesc;

    /**订单条目列表*/
	@NotEmpty
	@ValidateEntity
	private List<OrderItem> orderItemList;
	
	/**人工备注*/
	private String note;
	
	/**以下属性不与数据库字段绑定**/
	private  String platformId;
	
	/**大订单**/
	private Gorder gorder;

    /**虚拟关联id*/
    private String virtualRelationId;

    private String signature = null;
    
    /**商户订单号*/
    private String distributorOrderId;

    
    /**
     * 订单维度的退款金额
     */
    private BigDecimal refundAmount;

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

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getSaveAmount() {
        return saveAmount;
    }

    public void setSaveAmount(BigDecimal saveAmount) {
        this.saveAmount = saveAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getLogisticsAmount() {
        return logisticsAmount;
    }

    public void setLogisticsAmount(BigDecimal logisticsAmount) {
        this.logisticsAmount = logisticsAmount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getOriginalPayAmount() {
        return originalPayAmount;
    }

    public void setOriginalPayAmount(BigDecimal originalPayAmount) {
        this.originalPayAmount = originalPayAmount;
    }

    public BigDecimal getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(BigDecimal costAmount) {
        this.costAmount = costAmount;
    }

    public String getBuyAccountId() {
        return buyAccountId;
    }

    public void setBuyAccountId(String buyAccountId) {
        this.buyAccountId = buyAccountId;
    }

    public Timestamp getPreCloseTime() {
        return preCloseTime;
    }

    public void setPreCloseTime(Timestamp preCloseTime) {
        this.preCloseTime = preCloseTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSettleUp() {
        return settleUp;
    }

    public void setSettleUp(Integer settleUp) {
        this.settleUp = settleUp;
    }

    public Timestamp getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Timestamp successTime) {
        this.successTime = successTime;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantAccount() {
        return merchantAccount;
    }

    public void setMerchantAccount(String merchantAccount) {
        this.merchantAccount = merchantAccount;
    }

    public String getMerchantTel() {
        return merchantTel;
    }

    public void setMerchantTel(String merchantTel) {
        this.merchantTel = merchantTel;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public BigDecimal getMerchantAmount() {
        return merchantAmount;
    }

    public void setMerchantAmount(BigDecimal merchantAmount) {
        this.merchantAmount = merchantAmount;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getOrderIp() {
        return orderIp;
    }

    public void setOrderIp(String orderIp) {
        this.orderIp = orderIp;
    }

    public Timestamp getPaySuccessTime() {
        return paySuccessTime;
    }

    public void setPaySuccessTime(Timestamp paySuccessTime) {
        this.paySuccessTime = paySuccessTime;
    }

    public Integer getCanDeliver() {
        return canDeliver;
    }

    public void setCanDeliver(Integer canDeliver) {
        this.canDeliver = canDeliver;
    }

    public Integer getCanSettleUp() {
        return canSettleUp;
    }

    public void setCanSettleUp(Integer canSettleUp) {
        this.canSettleUp = canSettleUp;
    }

    public Integer getCanRefund() {
        return canRefund;
    }

    public void setCanRefund(Integer canRefund) {
        this.canRefund = canRefund;
    }

    public String getMisc() {
        return misc;
    }

    public void setMisc(String misc) {
        this.misc = misc;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Timestamp getDeliveryBeginTime() {
        return deliveryBeginTime;
    }

    public void setDeliveryBeginTime(Timestamp deliveryBeginTime) {
        this.deliveryBeginTime = deliveryBeginTime;
    }

    public Timestamp getDeliveryEndTime() {
        return deliveryEndTime;
    }

    public void setDeliveryEndTime(Timestamp deliveryEndTime) {
        this.deliveryEndTime = deliveryEndTime;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getClosedReason() {
        return closedReason;
    }

    public void setClosedReason(String closedReason) {
        this.closedReason = closedReason;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public Gorder getGorder() {
        return gorder;
    }

    public void setGorder(Gorder gorder) {
        this.gorder = gorder;
    }

    public String getVirtualRelationId() {
        return virtualRelationId;
    }

    public void setVirtualRelationId(String virtualRelationId) {
        this.virtualRelationId = virtualRelationId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public BigDecimal getRefundAmount() {
      return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
      this.refundAmount = refundAmount;
    }

	public String getDistributorOrderId() {
		return distributorOrderId;
	}

	public void setDistributorOrderId(String distributorOrderId) {
		this.distributorOrderId = distributorOrderId;
	}
    
}
