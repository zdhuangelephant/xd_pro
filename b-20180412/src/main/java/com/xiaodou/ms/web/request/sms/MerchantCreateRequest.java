package com.xiaodou.ms.web.request.sms;

import com.xiaodou.ms.web.request.BaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MerchantCreateRequest extends BaseRequest {
  private Long id;
  // 供应商名称
  private String name;
  // 供应商简称
  private String shortName;
  // 供应商地址
  private String address;
  // 联系电话
  private String telephone;
  // 联系人
  private String contactPerson;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getShortName() {
	return shortName;
}
public void setShortName(String shortName) {
	this.shortName = shortName;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getTelephone() {
	return telephone;
}
public void setTelephone(String telephone) {
	this.telephone = telephone;
}
public String getContactPerson() {
	return contactPerson;
}
public void setContactPerson(String contactPerson) {
	this.contactPerson = contactPerson;
}

}
