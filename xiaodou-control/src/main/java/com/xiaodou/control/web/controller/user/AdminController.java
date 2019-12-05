package com.xiaodou.control.web.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.control.model.admin.Admin;
import com.xiaodou.control.model.admin.Role;
import com.xiaodou.control.request.admin.AddAdminRequest;
import com.xiaodou.control.request.admin.EditAdminRequest;
import com.xiaodou.control.service.admin.AdminRoleService;
import com.xiaodou.control.service.admin.AdminService;
import com.xiaodou.control.web.controller.BaseController;

/**
 * Created by zyp on 14-9-1.
 */
@Controller("adminController")
@RequestMapping("/admin")
public class AdminController extends BaseController {
	@Resource
	AdminService adminService;
	@Resource
	AdminRoleService adminRoleService;

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

	/**
	 * 添加管理员
	 * 
	 * @return
	 */
	@RequestMapping("/addAdmin")
	@ResponseBody
	public String addUser(AddAdminRequest newAdminRequest) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Admin admin = new Admin();
			admin.setEmail(newAdminRequest.getEmail());
			admin.setUserName(newAdminRequest.getUserName());
			admin.setPassword(newAdminRequest.getPassword());
			admin.setRealName(newAdminRequest.getRealName());
			admin.setTelphone(newAdminRequest.getTelphone());
			admin.setMerchant(newAdminRequest.getMerchant());
			adminService.addAdmin(admin);
			map.put("msg", "success");
		} catch (Exception e) {
			map.put("error", e.toString());
			LoggerUtil.error("增加管理员失败", e);
		}
		return FastJsonUtil.toJson(map);
	}

	/**
	 * 删除管理员信息
	 * 
	 * @return
	 */
	@RequestMapping("/deleteAdmin")
	@ResponseBody
	public String deleteAdmin(Integer adminId) {
		Map<String, String> map = new HashMap<String, String>();
		try{
			if (adminId == null) {
				map.put("error", "adminId不能为空");
			} else {
				adminService.deleteAdmin(adminId);
				map.put("msg", "success");
			}
		}catch(Exception e){
			LoggerUtil.error("删除管理员失败", e);
		}
		return FastJsonUtil.toJson(map);
	}

	/**
	 * 编辑管理员信息
	 * 
	 * @return
	 */
	@RequestMapping("/editAdmin")
	@ResponseBody
	public String editAdminInfo(EditAdminRequest editAdminRequest,
			Errors errors, HttpServletRequest httpServletRequest) {
		Map<String, String> map = new HashMap<String, String>();
		try{
			if (httpServletRequest.getMethod().equals("POST")) {
				editAdminRequest.validate(editAdminRequest, errors);
				if (errors.hasErrors()) {
					map.put("error", this.getErrMsg(errors));
					return FastJsonUtil.toJson(map);
				}
				Admin admin = new Admin();
				admin.setId(editAdminRequest.getAdminId());
				admin.setUserName(editAdminRequest.getUserName());
				admin.setEmail(editAdminRequest.getEmail());
				admin.setTelphone(editAdminRequest.getTelphone());
				admin.setPassword(editAdminRequest.getPassword());
				admin.setRealName(editAdminRequest.getRealName());
				admin.setMerchant(editAdminRequest.getMerchant());
				adminService.updateAdmin(admin);
				map.put("msg", "success");
				}			
		}catch(Exception e){
			LoggerUtil.error("编辑管理员失败", e);
		}
		return FastJsonUtil.toJson(map);
	}

	/**
	 * 为管理员分类角色
	 * 
	 * @return
	 */
	@RequestMapping("/assigenRole")
	@ResponseBody
	public String assignRole(Integer adminId,
			HttpServletRequest httpServletRequest) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			List<Role> roles = adminRoleService.queryRolesByCond(new HashMap<String, Object>());
			List<Role> adminRoles = adminService.queryAdminRoles(adminId);
			map.put("adminRoles", adminRoles);
			map.put("roles", roles);
			map.put("adminId", adminId);
			map.put("msg", "success");
		}catch(Exception e){
			LoggerUtil.error("设置失败", e);
		}
		return FastJsonUtil.toJson(map);
	}

	/**
	 * 设置管理员角色
	 * 
	 * @param adminId
	 * @param needDeleteId
	 * @param needAddId
	 * @return
	 */
	@RequestMapping("/doAssigenRole")
	@ResponseBody
	public String doAssigenRole(Integer adminId, String needDeleteId,
			String needAddId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			adminRoleService.assigenRole(adminId, needDeleteId, needAddId);
			map.put("msg", "success");
		}catch(Exception e){
			LoggerUtil.error("设置管理员角色失败", e);
		}
		return FastJsonUtil.toJson(map);
	}

}
