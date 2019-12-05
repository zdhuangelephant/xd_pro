package com.xiaodou.login.model;

import java.sql.Timestamp;
import java.util.List;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.common.annotation.GeneralField;

/**
 * Created by zhouhuan on 17.08.15.
 * <p/>
 * 管理员
 */
public class Admin {

	/**
	 * 管理员id
	 */
	@Column(isMajor = true)
	@GeneralField
	private Integer id;

	/**
	 * 名称
	 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String userName;

	/**
	 * 密码
	 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String password;

	/**
	 * 盐值
	 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String salt;

	/**
	 * 最后登陆ip
	 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String lastLoginIp;

	/**
	 * 最后登陆时间
	 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Timestamp lastLoginTime;

	/**
	 * 邮件
	 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String email;

	/**
	 * 手机号码
	 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String telphone;

	/**
	 * 真实姓名
	 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String realName;

	/**
	 * 学习机构
	 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String merchant;

	private boolean isAdmin = false;

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
