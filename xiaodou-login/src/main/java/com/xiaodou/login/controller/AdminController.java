package com.xiaodou.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.login.model.Admin;
import com.xiaodou.login.service.AdminService;
import com.xiaodou.summer.web.BaseController;

/**
 * Created by zhouhuan on 17.08.15.
 */
@Controller("adminController")
@RequestMapping("/admin")
public class AdminController extends BaseController {
	@Resource
	AdminService adminService;

	/**
	 * 管理员登陆
	 * 
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login(String fail) {
		ModelAndView modelAndView = new ModelAndView("/login/login");
		modelAndView.addObject("fail", fail);
		return modelAndView;
	}

	/**
	 * 管理员列表
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list() {	
		Map<String, Object> cond = new HashMap<String, Object>();
		ModelAndView modelAndView = new ModelAndView("/admin/userList");
		try{			
			List<Admin> admins = adminService.queryAdmin(cond);
			modelAndView.addObject("admins", admins);	
		}catch (Exception e) {
			LoggerUtil.error("查询管理员列表失败", e);
		}
		return modelAndView;
	}
}
