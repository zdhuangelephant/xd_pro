package com.xiaodou.oms.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @Title:OrderRequestInfo.java
 * 
 * @Description:封装下单请求信息
 * 
 * @author zhaoyang
 * @date Jan 26, 2014 3:23:24 PM
 * @version V1.0
 */
public class OrderRequestVo {
	
	/**产品类型/业务类型*/
	private String productType;
	
	/**支付系统交易流水号*/
	private String payId;
	
	/**客户个人信息*/
	private CustomerVo customerInfo = new CustomerVo();
	
	/**发票信息*/
	private WayBillVo wayBillInfo = new WayBillVo();
	
	/**收货地址信息*/
	private ShippingAddressVo ShippingAddressInfo = new ShippingAddressVo();
	
	/**货品数据信息*/
	private List<OrderItemVo> orderItemInfoList = new ArrayList<OrderItemVo>();
	
	/**订单金额*/
	private BigDecimal orderAmount;

	public CustomerVo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerVo customerInfo) {
		this.customerInfo = customerInfo;
	}


	public WayBillVo getWayBillInfo() {
		return wayBillInfo;
	}

	public void setWayBillInfo(WayBillVo wayBillInfo) {
		this.wayBillInfo = wayBillInfo;
	}

	public ShippingAddressVo getShippingAddressInfo() {
		return ShippingAddressInfo;
	}

	public void setShippingAddressInfo(ShippingAddressVo shippingAddressInfo) {
		ShippingAddressInfo = shippingAddressInfo;
	}

	public List<OrderItemVo> getOrderItemInfoList() {
		return orderItemInfoList;
	}

	public void setOrderItemInfoList(List<OrderItemVo> orderItemInfoList) {
		this.orderItemInfoList = orderItemInfoList;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}
}
