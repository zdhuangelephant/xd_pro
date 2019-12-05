package com.xiaodou.ms.model.user;

import java.sql.Timestamp;

import com.xiaodou.common.annotation.GeneralField;

import lombok.Data;

/**
 * Created by zyp on 15/6/26.
 */
@Data
public class UserModel {
	@GeneralField
	private Long id;
	// private Integer module;
	// private String userName;
	@GeneralField
	private String nickName;
	// private String salt;
	// private String password;
	@GeneralField
	private String portrait;
	@GeneralField
	private String gender;
	@GeneralField
	private Integer age;
	@GeneralField
	private String address;
	@GeneralField
	private Timestamp tokenTime;
	@GeneralField
	private String token;
	@GeneralField
	private String type;
	@GeneralField
	private Timestamp createTime;
	@GeneralField
	private String latestDeviceIp;
	@GeneralField
	private String usedDeviceId;
	@GeneralField
	private String moduleInfo;
	@GeneralField
	private String module;//地域
	@GeneralField
	private String name;
	private String forumTitle;
	private String forumId;
	private String forumClassify;
	private String forumType;
	@GeneralField
	private String xdUniqueId; // 11/23
	private Integer userType;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getForumTitle() {
		return forumTitle;
	}
	public void setForumTitle(String forumTitle) {
		this.forumTitle = forumTitle;
	}
	public String getForumId() {
		return forumId;
	}
	public void setForumId(String forumId) {
		this.forumId = forumId;
	}
	public String getForumClassify() {
		return forumClassify;
	}
	public void setForumClassify(String forumClassify) {
		this.forumClassify = forumClassify;
	}
	public String getForumType() {
		return forumType;
	}
	public void setForumType(String forumType) {
		this.forumType = forumType;
	}
	public String getXdUniqueId() {
		return xdUniqueId;
	}
	public void setXdUniqueId(String xdUniqueId) {
		this.xdUniqueId = xdUniqueId;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	

}
