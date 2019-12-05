package com.xiaodou.oms.agent.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 
 * @Title:Payment.java
 * 
 * @Description:收退款请求
 * 
 * @author zhaoyang
 * @date Mar 13, 2014 10:55:32 AM
 * @version V1.0
 */
public class PayRequest implements Serializable {

	private static final long serialVersionUID = 8979944668296688061L;

	/**操作类型-收款 */
	public static final Integer OPER_TYPE_PAY = 0;
	
	/**操作类型-退款 */
	public static final Integer OPER_TYPE_REFUND = 1;
	
	/**支付类型-一次支付 */
	public static final Integer PAY_TYPE_ONE = 1;
	
	/**支付类型-二次支付 */
	public static final Integer PAY_TYPE_TWO = 2;
	
	/**状态-待处理 */
	public static final Integer PAY_STATUS_WAIT = 0;
	
	/**状态-处理中 */
	public static final Integer PAY_STATUS_PROCESSING = 1;
	
	/**状态-处理成功 */
	public static final Integer PAY_STATUS_SUCCESS = 2;
	
	/**状态-处理失败 */
	public static final Integer PAY_STATUS_FAILURE = -1;
	
	/**状态-未找到前置交易 */
	public static final Integer PAY_STATUS_NO_PRE_NO = 3;
	
	/** 主键 */
	private Integer id;

	/** 大订单号 */
	private String gorderId;

	/** 订单号 */
	private String orderId;

	/** 用户账号 */
	private String buyAccountId;

	/** 操作类型 0收款，1退款 */
	private Integer operType;

	/** 支付类型 1一次支付，2二次支付 */
	private Integer payType;

	/** 收退金额 */
	private BigDecimal amount;

	/** payment交易流水号 */
	private String payNo;

	/** payment前置交易流水号 */
	private String prePayNo;

	/** 状态：0，待处理，1，处理中，2，处理成功，-1处理失败 */
	private Integer payStatus;

	/** 记录生成时间 */
	private Timestamp createTime;

	/** 请求发送时间 */
	private Timestamp sentTime;

	/** payment回调时间 */
	private Timestamp paymentTime;

	/** 业务类型 */
	private String productType;

	/** 备注 */
	private String note;
	
	/** 失败原因 */
	private String failureReason;
	
	/** 处理时间 */
	private String processDays;
	
	/**生成时间下限*/
	private Timestamp createTimeLower;

	/**生成时间上限*/
	private Timestamp createTimeUpper;
	
	/**发送时间下限*/
	private Timestamp sentTimeLower;

	/**发送时间上限*/
	private Timestamp sentTimeUpper;
	
	/**payment回调时间下限*/
	private Timestamp paymentTimeLower;

	/**payment回调时间上限*/
	private Timestamp paymentTimeUpper;
	
	/**业务类型列表*/
	private List productTypeList;
	
	/**客户端类型*/
	private String clientType;

	/**外部来源*/
	private String outerOrigin;

    /**回调url*/
    private String callbackUrl;

    /**标识*/
    private String uuid;

    /**注释*/
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getBuyAccountId() {
        return buyAccountId;
    }

    public void setBuyAccountId(String buyAccountId) {
        this.buyAccountId = buyAccountId;
    }

    public Integer getOperType() {
        return operType;
    }

    public void setOperType(Integer operType) {
        this.operType = operType;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getPrePayNo() {
        return prePayNo;
    }

    public void setPrePayNo(String prePayNo) {
        this.prePayNo = prePayNo;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getSentTime() {
        return sentTime;
    }

    public void setSentTime(Timestamp sentTime) {
        this.sentTime = sentTime;
    }

    public Timestamp getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Timestamp paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getProcessDays() {
        return processDays;
    }

    public void setProcessDays(String processDays) {
        this.processDays = processDays;
    }

    public Timestamp getCreateTimeLower() {
        return createTimeLower;
    }

    public void setCreateTimeLower(Timestamp createTimeLower) {
        this.createTimeLower = createTimeLower;
    }

    public Timestamp getCreateTimeUpper() {
        return createTimeUpper;
    }

    public void setCreateTimeUpper(Timestamp createTimeUpper) {
        this.createTimeUpper = createTimeUpper;
    }

    public Timestamp getSentTimeLower() {
        return sentTimeLower;
    }

    public void setSentTimeLower(Timestamp sentTimeLower) {
        this.sentTimeLower = sentTimeLower;
    }

    public Timestamp getSentTimeUpper() {
        return sentTimeUpper;
    }

    public void setSentTimeUpper(Timestamp sentTimeUpper) {
        this.sentTimeUpper = sentTimeUpper;
    }

    public Timestamp getPaymentTimeLower() {
        return paymentTimeLower;
    }

    public void setPaymentTimeLower(Timestamp paymentTimeLower) {
        this.paymentTimeLower = paymentTimeLower;
    }

    public Timestamp getPaymentTimeUpper() {
        return paymentTimeUpper;
    }

    public void setPaymentTimeUpper(Timestamp paymentTimeUpper) {
        this.paymentTimeUpper = paymentTimeUpper;
    }

    public List getProductTypeList() {
        return productTypeList;
    }

    public void setProductTypeList(List productTypeList) {
        this.productTypeList = productTypeList;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getOuterOrigin() {
        return outerOrigin;
    }

    public void setOuterOrigin(String outerOrigin) {
        this.outerOrigin = outerOrigin;
    }
}
