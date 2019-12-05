package com.xiaodou.autotest.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;

import com.xiaodou.login.model.AdminUser;

/**
 * Created by zhouhuan on 17.08.15.
 */
public class BaseController {

	/**
	 * 获取当前登录用户
	 */
	protected AdminUser getUser() {
		return (AdminUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
	}

	/**
	 * 获取用户名
	 */
	protected Integer getUserId() {
		AdminUser adminUser = this.getUser();
		Integer userId = null;
		userId = adminUser.getUserId();
		return userId;
	}


}
