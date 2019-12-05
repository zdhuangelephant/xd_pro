package com.xiaodou.control.request.admin;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.xiaodou.control.request.BaseRequest;

/**
 * Created by zyp on 14-9-18.
 */
public class EditAdminRequest extends BaseRequest {

	private Integer adminId;

	private String userName;

	/**
	 * 密码
	 */
	private String password;

	private String repeatPassword;

	/**
	 * 邮件
	 */
	private String email;

	/**
	 * 手机号码
	 */
	private String telphone;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 学习机构
	 */
	private String merchant;

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
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

	@Override
	public void validate(Object o, Errors errors) {

		if (this.adminId == null) {
			errors.reject("adminId", "管理员id不能为空");
		}

		if (StringUtils.isBlank(this.userName)) {
			errors.reject("userName", "管理员用户名不能为空");
		}

		if (StringUtils.isNotBlank(this.password)
				&& StringUtils.isNotBlank(this.repeatPassword)
				&& !this.password.equals(this.repeatPassword)) {
			errors.reject("repeatPassword", "密码和重复密码不相同");
		}

	}
}
