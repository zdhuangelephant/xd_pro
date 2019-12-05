package com.xiaodou.ms.model.sms;

import lombok.Data;

/**
 * Created by zyp on 15/6/26.
 */
@Data
public class SmsMerchantModel {

  private Long id;

  private String name;

  private String shortName;

  private String address;

  private String telephone;

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
