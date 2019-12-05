package com.xiaodou.oms.entity;

import java.math.BigDecimal;

public class RefundStatistics implements java.io.Serializable
{
	/**
	 * 统计某产品在某段时间的退款率的POJO
	 */
	private static final long serialVersionUID = 1L;
	private String order_id;
	private String merchant_id;
	private String merchant_name;
	private String merchant_prct_id;
	private String merchant_prct_name;
	private BigDecimal refundAmount;
	private BigDecimal pay_amount;
	
	public RefundStatistics(){};

	
	public RefundStatistics(String orderId, String merchantId,
			String merchantName, String merchantPrctId,
			String merchantPrctName, BigDecimal refundAmount,
			BigDecimal payAmount) {
		super();
		order_id = orderId;
		merchant_id = merchantId;
		merchant_name = merchantName;
		merchant_prct_id = merchantPrctId;
		merchant_prct_name = merchantPrctName;
		this.refundAmount = refundAmount;
		pay_amount = payAmount;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String orderId) {
		order_id = orderId;
	}
	public String getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(String merchantId) {
		merchant_id = merchantId;
	}
	public String getMerchant_name() {
		return merchant_name;
	}
	public void setMerchant_name(String merchantName) {
		merchant_name = merchantName;
	}
	public String getMerchant_prct_id() {
		return merchant_prct_id;
	}
	public void setMerchant_prct_id(String merchantPrctId) {
		merchant_prct_id = merchantPrctId;
	}
	public String getMerchant_prct_name() {
		return merchant_prct_name;
	}
	public void setMerchant_prct_name(String merchantPrctName) {
		merchant_prct_name = merchantPrctName;
	}
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	public BigDecimal getPay_amount() {
		return pay_amount;
	}
	public void setPay_amount(BigDecimal payAmount) {
		pay_amount = payAmount;
	}
	

}
