package com.xiaodou.oms.vo;

import java.math.BigDecimal;

/**
 * 
 * @Title:WayBillVO.java
 * 
 * @Description:运单信息
 * 
 * @author zhaoyang
 * @date Jan 26, 2014 3:12:16 PM
 * @version V1.0
 */
public class WayBillVo {
	
	/**抬头*/
	private String title;
	/**内容*/
	private String content;
	/**邮寄地址*/
	private String address;
	/**邮政编码*/
	private String postcode;
	/**接收人姓名*/
	private String receiverName;
	/**接收人电话*/
	private String receiverPhone;
	/**发票金额*/
	private BigDecimal amount;
	/**发票类型*/
	private String InvoiceItem;
	
	public Integer getPostItem() {
		return postItem;
	}

	public void setPostItem(Integer postItem) {
		this.postItem = postItem;
	}

	/** 邮寄实体 0-发票 1-货品 2-货品+发票 */
	private Integer postItem;

	public String getInvoiceItem() {
		return InvoiceItem;
	}

	public void setInvoiceItem(String invoiceItem) {
		InvoiceItem = invoiceItem;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
