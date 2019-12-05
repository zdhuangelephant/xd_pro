package com.xiaodou.oms.entity.product;

import java.sql.Timestamp;

public class Merchant{
	Integer id; //商家ID，外部系统调用时必须
	String name;//商家名称，外部系统调用时必须
	String passport;//商家网易宝账户，外部系统调用时必须
	String platformId; //商家对应的网易宝平台ID，外部系统调用时必须
	Integer status;//商家状态，可选
	Timestamp createTime; //创建时间	，可选
	String telephone; //商家电话，可选
	String fullName;//商家全称，可选
	String merchantCode;//商家在系统中的唯一编码，可选
	String merchantKeyPath;//商家密钥
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassport() {
		return passport;
	}
	public void setPassport(String passport) {
		this.passport = passport;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getPlatformId() {
		return platformId;
	}
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
}
