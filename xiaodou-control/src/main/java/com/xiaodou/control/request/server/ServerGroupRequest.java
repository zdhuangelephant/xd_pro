package com.xiaodou.control.request.server;

import com.xiaodou.control.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class ServerGroupRequest extends BaseRequest {
	private String groupId;
	@NotEmpty
	private String groupName;
	private String groupType;
	private String baseServerNeedAdd;
	private String baseServerNeedDelete;
	private String baseNodeNeedAdd;
	private String baseNodeNeedDelete;
	private String groupServiceType;
	//应用类型1:应用服务 2:中间件服务
	private String type;


	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getGroupServiceType() {
		return groupServiceType;
	}

	public void setGroupServiceType(String groupServiceType) {
		this.groupServiceType = groupServiceType;
	}

	public String getBaseServerNeedAdd() {
		return baseServerNeedAdd;
	}

	public void setBaseServerNeedAdd(String baseServerNeedAdd) {
		this.baseServerNeedAdd = baseServerNeedAdd;
	}

	public String getBaseServerNeedDelete() {
		return baseServerNeedDelete;
	}

	public void setBaseServerNeedDelete(String baseServerNeedDelete) {
		this.baseServerNeedDelete = baseServerNeedDelete;
	}

	public String getBaseNodeNeedAdd() {
		return baseNodeNeedAdd;
	}

	public void setBaseNodeNeedAdd(String baseNodeNeedAdd) {
		this.baseNodeNeedAdd = baseNodeNeedAdd;
	}

	public String getBaseNodeNeedDelete() {
		return baseNodeNeedDelete;
	}

	public void setBaseNodeNeedDelete(String baseNodeNeedDelete) {
		this.baseNodeNeedDelete = baseNodeNeedDelete;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



}
