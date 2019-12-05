package com.xiaodou.control.model.admin;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.common.annotation.GeneralField;
import com.xiaodou.control.model.server.ServerGroupModel;

/**
 * Created by zhouhuan
 * <p/>
 * 权限表
 */
public class Privilege {

	/**
	 * 权限id
	 */
	@Column(isMajor = true)
	private Integer id;

	/**
	 * 权限名称
	 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String name;
	/**
	 * 服务组Id(1:基础服务 2：应用服务)
	 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String groupType;

	
	private List<ServerGroupModel> serverGroupList=Lists.newArrayList();
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

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public List<ServerGroupModel> getServerGroupList() {
		return serverGroupList;
	}

	public void setServerGroupList(List<ServerGroupModel> serverGroupList) {
		this.serverGroupList = serverGroupList;
	}

}
