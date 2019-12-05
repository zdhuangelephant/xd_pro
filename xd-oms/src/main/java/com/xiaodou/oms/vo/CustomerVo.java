package com.xiaodou.oms.vo;

/**
 * 
 * @Title:CustomerInfo.java
 * 
 * @Description:封装用户下单时的个人信息
 *
 * @author  zhaoyang
 * @date    Jan 26, 2014 3:09:37 PM
 * @version V1.0
 */
public class CustomerVo {
	
	private String buyAccountId;
	private String iP;
	private String receiverEmail;
	private String receiverPhone;
	private String clientType;
	private String outerOrigin;
	private String innerOrigin;
	private String city;
	private Integer isNeedInvoice;
	private String receiverName;
	
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public Integer getIsNeedInvoice() {
		return isNeedInvoice;
	}
	public void setIsNeedInvoice(Integer isNeedInvoice) {
		this.isNeedInvoice = isNeedInvoice;
	}
	public String getBuyAccountId() {
		return buyAccountId;
	}
	public void setBuyAccountId(String buyAccountId) {
		this.buyAccountId = buyAccountId;
	}
	public String getIP() {
		return iP;
	}
	public void setIP(String ip) {
		iP = ip;
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
	public String getInnerOrigin() {
		return innerOrigin;
	}
	public void setInnerOrigin(String innerOrigin) {
		this.innerOrigin = innerOrigin;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
