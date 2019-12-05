package com.xiaodou.control.web.controller.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.control.model.admin.Privilege;
import com.xiaodou.control.model.admin.Role;
import com.xiaodou.control.model.server.ServerGroupModel;
import com.xiaodou.control.model.server.ServerGroupTypeModel;
import com.xiaodou.control.service.admin.AdminPrivilegeService;
import com.xiaodou.control.service.admin.AdminService;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;
import com.xiaodou.control.service.server.BaseNodeService;
import com.xiaodou.control.service.server.ServerGroupService;
import com.xiaodou.control.service.server.ServerService;
import com.xiaodou.control.web.controller.BaseController;

/**
 * Created by on zhouhuan
 */
@Controller("serverGroupTypeController")
@RequestMapping("/serverGroupType")
public class ServerGroupTypeController extends BaseController {
	@Resource
	ServerService serverService;
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
	/**
	 * 服务组列表(权限检测)
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView("/serverGroupType/serverGroupTypeList");
		try {			
			List<ServerGroupTypeModel> serverGroupTypeList = mongoDbServiceFacadeImpl.getServerGroupTypeList();	
			modelAndView.addObject("serverGroupTypeList", serverGroupTypeList);
		} catch (Exception e) {		
			LoggerUtil.error("查询服务组失败", e);
		}		
		return modelAndView;
	}

	/**
	 * 服务组列表(权限检测)
	 * 
	 * @return
	 */
	@RequestMapping("/indexList")
	@ResponseBody
	public String indexList() {
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
				List<ServerGroupModel> serverGroupList = mongoDbServiceFacadeImpl.getServerGroupListByCond(null);	
				for(Privilege p:list){
					for(ServerGroupModel s:serverGroupList){
						if(s.getGroupType().equals(p.getGroupType())){
							p.getServerGroupList().add(s);
						}
					}
				}
				map.put("superAdmin", "true");
			} else {
				list = adminPrivilegeService.queryAllPrivilegeByAdmin(roleIds);
				List<ServerGroupModel> serverGroupList = mongoDbServiceFacadeImpl.getServerGroupListByCond(null);	
				for(Privilege p:list){
					for(ServerGroupModel s:serverGroupList){
						if(s.getGroupType().equals(p.getGroupType())){
							p.getServerGroupList().add(s);
						}
					}
				}
				map.put("superAdmin", "false");
			}
			map.put("groupTypeList", list);
			map.put("msg", "success");
		} catch (Exception e) {		
			LoggerUtil.error("查询列表错误", e);
		}		
		return FastJsonUtil.toJson(map);
	}


	/**
	 * 增加服务组类型
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doAdd")
	@ResponseBody
	public String doAdd(String serverGroupTypeName) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isNotBlank(serverGroupTypeName)){		
			try {
				ServerGroupTypeModel model=new ServerGroupTypeModel();
				model.setServerGroupTypeId(RandomUtil.randomNumber(10).toString());
				model.setServerGroupTypeName(serverGroupTypeName);
				mongoDbServiceFacadeImpl.addServerGroupType(model);
				Privilege privilege = new Privilege();
				privilege.setName(serverGroupTypeName);
				privilege.setGroupType(model.getServerGroupTypeId());
				adminPrivilegeService.addPrivilege(privilege);			
				map.put("msg", "success");
			} catch (Exception e) {
				map.put("msg", e.toString());
				LoggerUtil.error("增加服务组类型失败", e);
			}	
		}
		return FastJsonUtil.toJson(map);
	}

	
	
	/**
	 * 修改服务组类型
	 * 
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public String edit(String serverGroupTypeId,String serverGroupTypeName) {
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isNotBlank(serverGroupTypeId)){		
			try {
				ServerGroupTypeModel model=new ServerGroupTypeModel();
				model.setServerGroupTypeId(serverGroupTypeId);
				model.setServerGroupTypeName(serverGroupTypeName);
				Map<String, Object> input =Maps.newConcurrentMap();
				input.put("serverGroupTypeId", serverGroupTypeId);
				mongoDbServiceFacadeImpl.editServerGroupType(input, model);
				Privilege privilege = new Privilege();
				privilege.setName(serverGroupTypeName);
				privilege.setGroupType(serverGroupTypeId);
				adminPrivilegeService.updatePrivilege(privilege);
				map.put("msg", "success");
			} catch (Exception e) {
				map.put("msg", e.toString());
				LoggerUtil.error("修改服务组类型失败", e);
			}	
		}
		return FastJsonUtil.toJson(map);
	}

	/**
	 * 删除服务组类型
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public String remove(String serverGroupTypeId) {
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isNotBlank(serverGroupTypeId)){		
			try {
				mongoDbServiceFacadeImpl.delServerGroupType(serverGroupTypeId);
				adminPrivilegeService.deletePrivilege(serverGroupTypeId);
				map.put("msg", "success");
			} catch (Exception e) {
				map.put("msg", e.toString());
				LoggerUtil.error("删除服务组类型失败", e);
			}	
		}
		return FastJsonUtil.toJson(map);
	}

}
