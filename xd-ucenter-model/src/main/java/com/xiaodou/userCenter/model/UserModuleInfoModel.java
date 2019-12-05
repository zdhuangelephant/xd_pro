package com.xiaodou.userCenter.model;

import com.alibaba.fastjson.TypeReference;
import com.xiaodou.common.annotation.BaseField;
import com.xiaodou.common.annotation.GeneralField;
import com.xiaodou.common.util.warp.FastJsonUtil;

public class UserModuleInfoModel {
	/**
	 * 主键id
	 */
	@GeneralField
	@BaseField
	private Long id;

	/**
	 * 所属模块
	 */
	@BaseField
	private String module;
	/**
	 * 用户名(手机号)
	 */
	@GeneralField
	@BaseField
	private String userName;
	/**
	 * 所属模块信息
	 */
	@BaseField
	private String moduleInfo;

	public UserModuleInfoModel(){}
	
	public UserModuleInfoModel(String module){
		this.module = module;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getModuleInfo() {
		return moduleInfo;
	}

	public void setModuleInfo(String moduleInfo) {
		this.moduleInfo = moduleInfo;
	}

	public <T> T getModel(T t) {
		return FastJsonUtil.fromJsons(moduleInfo, new TypeReference<T>() {
		});
	}
}
