package com.xiaodou.control.model.server;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@CollectionName("serverGroupType")
public class ServerGroupTypeModel extends MongoBaseModel {
	@Pk
	private String serverGroupTypeId;
	private String serverGroupTypeName;
	private List<ServerGroupModel> serverGroupList=Lists.newArrayList();
	public String getServerGroupTypeName() {
		return serverGroupTypeName;
	}
	public void setServerGroupTypeName(String serverGroupTypeName) {
		this.serverGroupTypeName = serverGroupTypeName;
	}
	public String getServerGroupTypeId() {
		return serverGroupTypeId;
	}
	public void setServerGroupTypeId(String serverGroupTypeId) {
		this.serverGroupTypeId = serverGroupTypeId;
	}
	public List<ServerGroupModel> getServerGroupList() {
		return serverGroupList;
	}
	public void setServerGroupList(List<ServerGroupModel> serverGroupList) {
		this.serverGroupList = serverGroupList;
	}
	


}
