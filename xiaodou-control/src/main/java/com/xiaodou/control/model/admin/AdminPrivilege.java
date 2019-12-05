package com.xiaodou.control.model.admin;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.common.annotation.GeneralField;

/**
 * Created by zyp on 15/8/26.
 */
public class AdminPrivilege {

	// 管理员Id
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer adminId;

	// 权限Id
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer privilegeId;

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Integer getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}
}
