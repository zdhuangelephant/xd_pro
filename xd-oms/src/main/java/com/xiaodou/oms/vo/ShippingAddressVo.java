package com.xiaodou.oms.vo;

import java.sql.Timestamp;

/**
 * 
 * @Title:ShippingAddressInfo.java
 * 
 * @Description:收货地址信息
 *
 * @author  zhaoyang
 * @date    Jan 26, 2014 3:12:46 PM
 * @version V1.0
 */
public class ShippingAddressVo {
	
	/**预期送货时间*/
	private Timestamp expectedShippingTime;
	/**邮寄地址*/
	private String address;
	/**邮政编码*/
	private String postcode;
	/**接收人姓名*/
	private String receiverName;
	/**接收人电话*/
	private String receiverPhone;
	
	private String province;//省份
	private String city;//城市
	private String region;//行政区
	
	public Timestamp getExpectedShippingTime() {
		return expectedShippingTime;
	}

	public void setExpectedShippingTime(Timestamp expectedShippingTime) {
		this.expectedShippingTime = expectedShippingTime;
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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

}
