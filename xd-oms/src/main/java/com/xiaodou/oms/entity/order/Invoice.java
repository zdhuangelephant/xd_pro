package com.xiaodou.oms.entity.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 
 * @Title:Invoice.java
 * 
 * @Description:发票信息
 * 
 * @author think
 * @date Mar 19, 2014 9:29:32 AM
 * @version V1.0
 */
public class Invoice implements Serializable  {
	
	private static final long serialVersionUID = -61956873338966134L;
	
	/**发票状态 未开*/
	public static final Integer STATUS_INIT = 0;

	/**发票状态 已开*/
	public static final Integer STATUS_COMPLETED = 0;

	/** id */
	private Long id;

	/** 大订单ID */
	private String gorderId;

	/** 用户ID */
	private String buyAccountId;

	/** 发票金额 */
	private BigDecimal amount;

	/** 抬头 */
	private String title;

	/** 内容 */
	private String content;

	/** 邮寄地址 */
	private String address;

	/** 邮政编码 */
	private String postcode;

	/** 接收人姓名 */
	private String receiverName;

	/** 接收人电话 */
	private String receiverPhone;

	/** 发票状态 */
	private Integer invoiceStatus;

	/** 开票时间 */
	private Timestamp invoiceTime;

	/** 操作人 */
	private String operator;

	/** 备注 */
	private String note;

	/** 创建时间 */
	private Timestamp createTime;

	/** 产品类型 */
	private String productType;
	
	/** 创建时间下限 */
	private Timestamp  createTimeLower;

	/** 创建时间上限 */
	private Timestamp  createTimeUpper;
	
	/** 开发票时间下限 */
	private Timestamp  invoiceTimeLower;

	/** 开发票时间上限 */
	private Timestamp  invoiceTimeUpper;
	
	/**1有效，发票对外可见；0无效，发票对外不可见 */
	private Integer isValid;
	
	/** 订单完成时间 */
	private Timestamp orderCompleteTime;
	
	/** 订单完成时间上限 */
	private Timestamp orderCompleteTimeUpper;

	/** 订单完成时间下限 */
	private Timestamp orderCompleteTimeLower;
	
	/** 发票号 */
	private String invoiceCode;
	
	/** 省 */
	private String province;
	
	/** 市 */
	private String city;
	
	/** 区 */
	private String area;
	
	/** 发票项目 */
	private String invoiceItem;

	public Integer getIsValid() {
		return isValid;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Timestamp getOrderCompleteTime() {
		return orderCompleteTime;
	}

	public void setOrderCompleteTime(Timestamp orderCompleteTime) {
		this.orderCompleteTime = orderCompleteTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGorderId() {
		return gorderId;
	}

	public void setGorderId(String gorderId) {
		this.gorderId = gorderId;
	}

	public String getBuyAccountId() {
		return buyAccountId;
	}

	public void setBuyAccountId(String buyAccountId) {
		this.buyAccountId = buyAccountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public Integer getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(Integer invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Timestamp getInvoiceTime() {
		return invoiceTime;
	}

	public void setInvoiceTime(Timestamp invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public Timestamp getInvoiceTimeLower() {
		return invoiceTimeLower;
	}

	public void setInvoiceTimeLower(Timestamp invoiceTimeLower) {
		this.invoiceTimeLower = invoiceTimeLower;
	}

	public Timestamp getInvoiceTimeUpper() {
		return invoiceTimeUpper;
	}

	public void setInvoiceTimeUpper(Timestamp invoiceTimeUpper) {
		this.invoiceTimeUpper = invoiceTimeUpper;
	}
	
	public Timestamp getOrderCompleteTimeUpper() {
		return orderCompleteTimeUpper;
	}

	public void setOrderCompleteTimeUpper(Timestamp orderCompleteTimeUpper) {
		this.orderCompleteTimeUpper = orderCompleteTimeUpper;
	}

	public Timestamp getOrderCompleteTimeLower() {
		return orderCompleteTimeLower;
	}

	public void setOrderCompleteTimeLower(Timestamp orderCompleteTimeLower) {
		this.orderCompleteTimeLower = orderCompleteTimeLower;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getInvoiceItem() {
		return invoiceItem;
	}

	public void setInvoiceItem(String invoiceItem) {
		this.invoiceItem = invoiceItem;
	}
}
