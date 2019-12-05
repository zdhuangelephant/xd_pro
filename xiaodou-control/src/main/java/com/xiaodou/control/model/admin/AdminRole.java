package com.xiaodou.control.model.admin;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.common.annotation.GeneralField;

/**
 * Created by zyp on 14-9-1.
 * <p/>
 * 管理员角色
 */
public class AdminRole {

	/**
	 * 管理员id
	 */
	@GeneralField
	@Column(isMajor = true)
	private Integer adminId;

	/**
	 * 角色id
	 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer roleId;

	/**
	 * 是否默认角色
	 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer isDefault;

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
}
