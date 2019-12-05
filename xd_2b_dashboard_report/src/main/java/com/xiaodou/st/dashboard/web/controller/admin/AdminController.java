package com.xiaodou.st.dashboard.web.controller.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.st.dashboard.domain.admin.AdminDO;
import com.xiaodou.st.dashboard.domain.admin.ResetPasswordDTO;
import com.xiaodou.st.dashboard.service.admin.AdminService;
import com.xiaodou.st.dashboard.web.controller.BaseController;

/**
 * 
 * @name AdminController CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年3月29日
 * @description TODO
 * @version 1.0
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
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public ModelAndView login(String fail) throws Exception {
		ModelAndView modelAndView = new ModelAndView("/admin/login");
		modelAndView.addObject("fail", fail);
		// throw new Exception();
		return modelAndView;
	}

	/**
	 * 
	 * @description 超级管理员登录
	 * @author 李德洪
	 * @Date 2017年6月23日
	 * @param fail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/manage_login")
	public ModelAndView manageLogin(String fail) throws Exception {
		ModelAndView modelAndView = new ModelAndView("/admin/manageLogin");
		modelAndView.addObject("fail", fail);
		// throw new Exception();
		return modelAndView;
	}

	/**
	 * 
	 * @description 去往修改密码
	 * @author 李德洪
	 * @Date 2017年3月30日
	 * @return
	 */
	@RequestMapping("/reset_password")
	public ModelAndView resetPassword() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("adminUser", super.getAdminUser());
		modelAndView.setViewName("/admin/resetPassword");
		return modelAndView;
	}

	@RequestMapping("/do_reset_password")
	@ResponseBody
	public String doResetPassword(ResetPasswordDTO vo) {
		Boolean flag = false;
		// Boolean validPassword =
		// adminService.validatePassword(super.getAdminUser(),
		// vo.getOldPassword());
		// if (!validPassword) {
		// return String.valueOf(flag);
		// }
		if (!vo.getNewPassword().equals(vo.getConfirmPassword())) {
			return String.valueOf(flag);
		}
		AdminDO admin = new AdminDO();
		admin.setId(super.getAdminUser().getUserId());
		admin.setPassword(vo.getNewPassword());
		flag = adminService.updateAdmin(admin);
		return String.valueOf(flag);
	}

	/**
	 * 去往修改用户页面
	 * 
	 * @description TODO
	 * @author 李德洪
	 * @Date 2017年3月30日
	 * @return
	 */
	@RequestMapping("/reset_user_detail")
	public ModelAndView resetUserDetail() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("adminUser", super.getAdminUser());
		modelAndView.setViewName("/admin/resetUserDetail");
		return modelAndView;
	}

	@RequestMapping("/do_reset_user_detail")
	@ResponseBody
	public String doResetUserDetail(AdminDO adminDO) {
		adminDO.setId(super.getAdminUser().getUserId());
		Boolean flag = adminService.updateAdmin(adminDO);
		return String.valueOf(flag);
	}

	@RequestMapping("/checkPwd")
	@ResponseBody
	public String checkPwd(String currPassword) {
		AdminDO currentUser = adminService.findAdminById(super.getAdminUser().getUserId());
		return String.valueOf(adminService.validatePassword(currentUser, currPassword));
	}

}
