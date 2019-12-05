package com.xiaodou.control.model.server;

import com.xiaodou.control.constant.Constant;
import com.xiaodou.control.enums.GroupServiceTypeEnum;
import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@CollectionName("serverGroup")
public class ServerGroupModel extends MongoBaseModel {
	@Pk
	private String groupId;
	private String groupName;
	private String groupType;
	private String groupServiceType =GroupServiceTypeEnum.NativeGroup.getCode();
	private String groupTypeName;
	private String type=Constant.application;
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

	public String getGroupTypeName() {
		return groupTypeName;
	}

	public void setGroupTypeName(String groupTypeName) {
		this.groupTypeName = groupTypeName;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
}
