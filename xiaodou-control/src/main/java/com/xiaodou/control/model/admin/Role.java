package com.xiaodou.control.model.admin;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.common.annotation.GeneralField;

/**
 * Created by zhouhuan. 角色表
 */
public class Role {

	/**
	 * 角色id
	 */
	@Column(isMajor = true)
	private Integer id;

	/**
	 * 角色名称
	 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String roleName;

	/**
	 * 角色描述
	 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String roleDescription;

	/**
	 * 排序
	 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer sortOrder;

	/**
	 * 是否生效
	 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer disabled;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getDisabled() {
		return disabled;
	}

	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
}
