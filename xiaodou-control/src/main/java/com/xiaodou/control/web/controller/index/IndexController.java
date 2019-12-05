package com.xiaodou.control.web.controller.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.control.model.admin.Privilege;
import com.xiaodou.control.model.admin.Role;
import com.xiaodou.control.service.admin.AdminPrivilegeService;
import com.xiaodou.control.service.admin.AdminService;
import com.xiaodou.control.service.server.ServerGroupService;
import com.xiaodou.control.web.controller.BaseController;

/**
 * Created by on zhouhuan
 */
@Controller("indexController")
@RequestMapping("/")
public class IndexController extends BaseController {
	@Resource
	ServerGroupService serverGroupService;
	@Resource
	AdminService adminService;	
	@Resource
	AdminPrivilegeService adminPrivilegeService;
	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("/index");
		return modelAndView;
	}
	
	/**
	 * 服务组列表(权限检测)
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public String list() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Role> roles = adminService.queryAdminRoles(this.getUserId());
			List<Integer> roleIds = new ArrayList<>();
			Boolean isSuperAdmin = false;
			for (Role role : roles) {
				roleIds.add(role.getId());
				if (role.getId().equals(1)) {
					isSuperAdmin = true;
					break;
				}
			}
			List<Privilege> list = new ArrayList<Privilege>();
			if (isSuperAdmin) {
				list = adminPrivilegeService.queryAllPrivileges();
				map.put("superAdmin", "true");
			} else {
				list = adminPrivilegeService.queryAllPrivilegeByAdmin(roleIds);
				map.put("superAdmin", "false");
			}
			map.put("groupList", list);
			map.put("msg", "success");
		} catch (Exception e) {		
			LoggerUtil.error("查询服务组失败", e);
		}		
		return FastJsonUtil.toJson(map);
	}
}
