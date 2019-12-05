package com.xiaodou.control.model.admin;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.common.annotation.GeneralField;

/**
 * Created by zyp on 14-9-1. 角色权限
 */
public class RolePrivilege {

	/**
	 * 角色Id
	 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer roleId;

	/**
	 * 权限Id
	 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer privilegeId;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}
}
