package com.xiaodou.ms.model.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.common.annotation.GeneralField;

import lombok.Data;

@Data
public class UserFeedBackModel {
	@GeneralField
	@Column(isMajor = true, sortBy = true, persistent = true, autoIncrement = true)
	private Integer id;
	
	@GeneralField
	@Column(canUpdate = false, sortBy = false)
	private String content;

	@GeneralField
	@Column(canUpdate = false, sortBy = true)
	private Timestamp createTime;

	/* 类型描述列表 */
	@GeneralField
	@Column(canUpdate = false, sortBy = false)
	private String categoryDescs;

	/* 邮箱或者手机号 */
	@GeneralField
	@Column(canUpdate = false)
	private String number;

	/* 图片列表 */
	@GeneralField
	@Column(canUpdate = false, sortBy = false)
	private String imageUrls ;
	/* 用户id */
	@GeneralField
	@Column(canUpdate = false)
	private Integer userId;
	/* 设备类型 */
	@GeneralField
	@Column(canUpdate = false)
	private String deviceType;

	/*****app版本****/
	@Column(canUpdate = false)
	@GeneralField
	private String appVersion;
	
	/******系统版本******/
	@GeneralField
	@Column(canUpdate = false)
	private String osVersion;
	
	/******用户注册电话******/
    @GeneralField
    @Column
    private String telephone;
    
    /******处理状态******/
    @GeneralField
    @Column
    private Integer handleStatus;
    
    /******处理记录******/
    @GeneralField
    @Column
    private String handleNote;
    
    
	private List<String> tmpImageUrl = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCategoryDescs() {
		return categoryDescs;
	}

	public void setCategoryDescs(String categoryDescs) {
		this.categoryDescs = categoryDescs;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(String imageUrls) {
		this.imageUrls = imageUrls;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public List<String> getTmpImageUrl() {
		return tmpImageUrl;
	}

	public void setTmpImageUrl(List<String> tmpImageUrl) {
		this.tmpImageUrl = tmpImageUrl;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(Integer handleStatus) {
		this.handleStatus = handleStatus;
	}

	public String getHandleNote() {
		return handleNote;
	}

	public void setHandleNote(String handleNote) {
		this.handleNote = handleNote;
	}
	
}
