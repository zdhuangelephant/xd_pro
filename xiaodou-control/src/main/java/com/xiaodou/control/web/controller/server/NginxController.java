package com.xiaodou.control.web.controller.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.control.model.admin.Privilege;
import com.xiaodou.control.model.admin.Role;
import com.xiaodou.control.model.server.NginxModel;
import com.xiaodou.control.model.server.NginxServerGroupRelationModel;
import com.xiaodou.control.model.server.ServerGroupModel;
import com.xiaodou.control.request.server.NginxRequest;
import com.xiaodou.control.service.admin.AdminPrivilegeService;
import com.xiaodou.control.service.admin.AdminRoleService;
import com.xiaodou.control.service.admin.AdminService;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;
import com.xiaodou.control.service.server.BaseNodeService;
import com.xiaodou.control.service.server.ServerGroupService;
import com.xiaodou.control.service.server.ServerService;
import com.xiaodou.control.web.controller.BaseController;

/**
 * Created by on zhouhuan
 */
@Controller("nginxController")
@RequestMapping("/nginx")
public class NginxController extends BaseController {
	@Resource
	ServerService serverService;
	@Resource
	com.xiaodou.control.service.server.NginxService nginxService;
	@Resource
	ServerGroupService serverGroupService;
	@Resource
	BaseNodeService baseNodeService;
	@Resource
	AdminPrivilegeService adminPrivilegeService;
	@Resource
	AdminService adminService;	
	@Resource
	MongoDbServiceFacade mongoDbServiceFacadeImpl;
	@Resource
	AdminRoleService adminRoleService;
	/**
	 * 服务页面
	 * 
	 * @return
	 */
	@RequestMapping("/getNginx")
	@ResponseBody
	public String getNginx(String nginxServerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			NginxModel nginx=nginxService.getNginx(nginxServerId);
			map.put("msg", "success");
			map.put("nginx", nginx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LoggerUtil.error("修改服务失败", e);
			map.put("msg", e.toString());
		}
		return FastJsonUtil.toJson(map);
	}



	/**
	 * 增加修改Nginx
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNginx")
	@ResponseBody
	public String addNginx(NginxRequest request){
		Map<String, String> map = new HashMap<String, String>();
		try {
			Errors errors = request.validate();
			if (errors.hasErrors()) {
				return this.getErrMsg(errors);
			}
			nginxService.addOrUpdate(new NginxModel(request));
			map.put("msg", "success");
		} catch (Exception e) {
			LoggerUtil.error("增加修改Nginx失败", e);
			map.put("msg", e.toString());
		}
		return FastJsonUtil.toJson(map);
	}
	/**
	 * 服务组列表(权限检测)
	 * 
	 * @return
	 */
	@RequestMapping("/listWithNginxServerId")
	@ResponseBody
	public String listWithNginxServerId(String nginxServerId) {
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
			List<Privilege> list = Lists.newArrayList();
			List<ServerGroupModel> serverGroupList =Lists.newArrayList();
			if (isSuperAdmin) {
				list = adminPrivilegeService.queryAllPrivileges();
				serverGroupList = mongoDbServiceFacadeImpl.getServerGroupListByCond(null);	
				map.put("superAdmin", "true");
			} else {
				list = adminPrivilegeService.queryAllPrivilegeByAdmin(roleIds);
				for(Privilege p:list){
					Map<String, Object> input =Maps.newConcurrentMap();
					input.put("groupType", p.getGroupType());
					serverGroupList.addAll(mongoDbServiceFacadeImpl.getServerGroupListByCond(input));
				}
				map.put("superAdmin", "false");
			}
			Map<String, Object> input=Maps.newConcurrentMap();
			input.put("nginxServerId", nginxServerId);
			List<NginxServerGroupRelationModel> relationList=mongoDbServiceFacadeImpl.getNginxServerGroupRelationByCond(input);
			map.put("relationList", relationList);
			map.put("groupList", serverGroupList);
			map.put("msg", "success");
		} catch (Exception e) {		
			LoggerUtil.error("查询服务组失败", e);
		}		
		return FastJsonUtil.toJson(map);
	}
	
	
	
	/**
	 * 设置Nginx与ServerGroup之间的关系
	 * 
	 * @param adminId
	 * @param needDeleteId
	 * @param needAddId
	 * @return
	 */
	@RequestMapping("/editNginxServerGroup")
	@ResponseBody
	public String editNginxServerGroup(String nginxServerId, String needDeleteId,
			String needAddId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			nginxService.editNginxServerGroup(nginxServerId, needDeleteId, needAddId);
			map.put("msg", "success");
		}catch(Exception e){
			LoggerUtil.error("设置管理员角色失败", e);
		}
		return FastJsonUtil.toJson(map);
	}
}
