package com.xiaodou.ms.web.request.user;

import java.sql.Timestamp;

import com.xiaodou.ms.model.user.UserModel;
import com.xiaodou.ms.web.request.BaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserReqeuest extends BaseRequest {
	private Long id;
	private String nickName;
	private String portrait;
	private String gender;
	private Integer age;
	private String address;
	private Timestamp tokenTime;
	private String token;
	private String type;
	private Timestamp createTime;
	private String latestDeviceIp;
	private String usedDeviceId;
	private String moduleInfo;
	private String name;
	/** xdUniqueId 小逗用户唯一ID */
	private String xdUniqueId;

	public UserModel initModel() {
		UserModel model = new UserModel();
		model.setId(id);
		model.setNickName(nickName);
		model.setPortrait(portrait);
		model.setGender(gender);
		model.setAge(age);
		model.setAddress(address);
		model.setTokenTime(tokenTime);
		model.setToken(token);
		model.setType(type);
		model.setCreateTime(createTime);
		model.setLatestDeviceIp(latestDeviceIp);
		model.setUsedDeviceId(usedDeviceId);
		model.setModuleInfo(moduleInfo);
		model.setName(name);
		model.setXdUniqueId(xdUniqueId);
		return model;
	}

	

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Timestamp getTokenTime() {
		return tokenTime;
	}

	public void setTokenTime(Timestamp tokenTime) {
		this.tokenTime = tokenTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
	  if(createTime == null)
	    createTime = new Timestamp(System.currentTimeMillis());
		this.createTime = createTime;
	}

	public String getLatestDeviceIp() {
		return latestDeviceIp;
	}

	public void setLatestDeviceIp(String latestDeviceIp) {
		this.latestDeviceIp = latestDeviceIp;
	}

	public String getUsedDeviceId() {
		return usedDeviceId;
	}

	public void setUsedDeviceId(String usedDeviceId) {
		this.usedDeviceId = usedDeviceId;
	}

	public String getModuleInfo() {
		return moduleInfo;
	}

	public void setModuleInfo(String moduleInfo) {
		this.moduleInfo = moduleInfo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getXdUniqueId() {
		return xdUniqueId;
	}

	public void setXdUniqueId(String xdUniqueId) {
		this.xdUniqueId = xdUniqueId;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}
	
}
