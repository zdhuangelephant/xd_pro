package com.xiaodou.control.web.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.control.model.admin.Admin;
import com.xiaodou.control.model.admin.AdminRole;
import com.xiaodou.control.model.admin.Privilege;
import com.xiaodou.control.model.admin.Role;
import com.xiaodou.control.model.admin.RolePrivilege;
import com.xiaodou.control.request.admin.EditRoleRequest;
import com.xiaodou.control.request.admin.NewRoleRequest;
import com.xiaodou.control.service.admin.AdminPrivilegeService;
import com.xiaodou.control.service.admin.AdminRoleService;
import com.xiaodou.control.service.admin.AdminService;
import com.xiaodou.control.web.controller.BaseController;

/**
 * Created by zhouhuan.
 */
@Controller("roleController")
@RequestMapping("/role")
public class RoleController extends BaseController {

	@Resource
	AdminRoleService adminRoleService;

	@Resource
	AdminPrivilegeService adminPrivilegeService;

	@Resource
	AdminService adminService;

	/**
	 * 角色列表
	 * 
	 * @return
	 */
	@RequestMapping("list")
	public ModelAndView list() {
		Map<String, Object> cond = new HashMap<String, Object>();
		ModelAndView modelAndView = new ModelAndView("/admin/roleList");
	    try{		
			List<Role> roles = adminRoleService.queryRolesByCond(cond);		
			modelAndView.addObject("roles", roles);		
		}catch (Exception e) {
			LoggerUtil.error("查询角色列表失败", e);
		}
		return modelAndView;
	}

	/**
	 * 添加角色
	 * 
	 * @return
	 */
	@RequestMapping("/addRole")
	@ResponseBody
	public String addRole(NewRoleRequest newRoleRequest, Errors errors,
			HttpServletRequest httpServletRequest) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if (httpServletRequest.getMethod().equals("POST")) {
				newRoleRequest.validate(newRoleRequest, errors);
				if (errors.hasErrors()) {
					map.put("error", "添加角色失败");
					return FastJsonUtil.toJson(map);
				}
				Role role = new Role();
				role.setDisabled(newRoleRequest.getDisabled());
				role.setRoleDescription(newRoleRequest.getRoleDescription());
				role.setRoleName(newRoleRequest.getRoleName());
				role.setSortOrder(0);
				adminRoleService.addRole(role);
				map.put("msg", "success");
				return FastJsonUtil.toJson(map);
			}
		} catch (Exception e) {
			map.put("error", e.toString());
			LoggerUtil.error("增加角色失败", e);
		}
		return FastJsonUtil.toJson(map);
	}

	/**
	 * 删除角色
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/deleteRole")
	@ResponseBody
	public String deleteRole(Integer roleId) {
		Map<String, String> map = new HashMap<String, String>();
		try{
			if (roleId == null) {
				map.put("error", "roleId不能为空");
			} else {
				adminRoleService.deleteRole(roleId);
				map.put("msg", "success");
			}
		} catch (Exception e) {
			map.put("error", e.toString());
			LoggerUtil.error("删除角色失败", e);
		}
		return FastJsonUtil.toJson(map);
	}

	/**
	 * 编辑角色
	 * 
	 * @return
	 */
	@RequestMapping("/editRole")
	@ResponseBody
	public String editRole(
			@ModelAttribute("editRoleRequest") EditRoleRequest editRoleRequest,
			Errors errors, HttpServletRequest httpServletRequest) {
		Map<String, String> map = new HashMap<String, String>();
		try{
			if (httpServletRequest.getMethod().equals("POST")) {
				editRoleRequest.validate(editRoleRequest, errors);
				if (errors.hasErrors()) {
					map.put("error", "编辑角色失败");
					return FastJsonUtil.toJson(map);
				}
				Role role = new Role();
				role.setDisabled(editRoleRequest.getDisabled());
				role.setRoleDescription(editRoleRequest.getRoleDescription());
				role.setId(editRoleRequest.getRoleId());
				role.setRoleName(editRoleRequest.getRoleName());
				role.setSortOrder(0);
				adminRoleService.updateRole(role);
				map.put("msg", "success");
				return FastJsonUtil.toJson(map);
			}
		} catch (Exception e) {
			map.put("error", e.toString());
			LoggerUtil.error("编辑角色失败", e);
		}
		return FastJsonUtil.toJson(map);
	}

	/**
	 * 设置角色权限
	 * 
	 * @param roleId
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping("setPrivilege")
	@ResponseBody
	public String setPrivilege(Integer roleId,
			HttpServletRequest httpServletRequest) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{	
			List<RolePrivilege> rolePrivileges = adminRoleService
					.queryRolePrivileges(roleId);
			List<Privilege> privileges = adminPrivilegeService.queryAllPrivileges();
			map.put("roleId", roleId);
			map.put("rolePrivileges", rolePrivileges);
			map.put("privileges", privileges);
		} catch (Exception e) {
			map.put("error", e.toString());
			LoggerUtil.error("设置角色权限失败", e);
		}
		return FastJsonUtil.toJson(map);
	}

	/**
	 * 设置权限
	 * 
	 * @param roleId
	 * @param needDeleteId
	 * @param needAddId
	 */
	@RequestMapping("doSetPrivilege")
	@ResponseBody
	public String doSetPrivilege(Integer roleId, String needDeleteId,
			String needAddId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{	
			adminPrivilegeService.setPrivilege(roleId, needDeleteId, needAddId);
			map.put("msg", "success");
		} catch (Exception e) {
				map.put("error", e.toString());
				LoggerUtil.error("设置权限失败", e);
			}
		return FastJsonUtil.toJson(map);
	}

	@RequestMapping("/admins")
	@ResponseBody
	public String roleAdminList(Integer roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AdminRole> adminRoles = adminRoleService.queryRoleAdmins(roleId);
		List<Admin> admins = new ArrayList<Admin>();
		for (AdminRole adminRole : adminRoles) {
			admins.add(adminService.findAdminById(adminRole.getAdminId()));
		}
		map.put("admins", admins);
		return FastJsonUtil.toJson(map);
	}
}
