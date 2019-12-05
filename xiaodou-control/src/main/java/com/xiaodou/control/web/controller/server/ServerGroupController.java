package com.xiaodou.control.web.controller.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.control.enums.BaseNodeStatusEnum;
import com.xiaodou.control.enums.GroupServiceTypeEnum;
import com.xiaodou.control.model.server.NodeModel;
import com.xiaodou.control.model.server.ServerGroupModel;
import com.xiaodou.control.model.server.ServerGroupTypeModel;
import com.xiaodou.control.model.server.ServerModel;
import com.xiaodou.control.model.server.base.BaseNodeModel;
import com.xiaodou.control.model.server.base.BaseServerModel;
import com.xiaodou.control.request.server.ServerGroupRequest;
import com.xiaodou.control.request.server.ServerRequest;
import com.xiaodou.control.service.admin.AdminPrivilegeService;
import com.xiaodou.control.service.admin.AdminService;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;
import com.xiaodou.control.service.server.BaseNodeService;
import com.xiaodou.control.service.server.NodeService;
import com.xiaodou.control.service.server.ServerGroupService;
import com.xiaodou.control.service.server.ServerService;
import com.xiaodou.control.vo.ConfigInfoVo;
import com.xiaodou.control.vo.LogMonitorVo;
import com.xiaodou.control.web.controller.BaseController;

/**
 * Created by on zhouhuan
 */
@Controller("serverGroupController")
@RequestMapping("/serverGroup")
public class ServerGroupController extends BaseController {
	@Resource
	ServerService serverService;
	@Resource
	ServerGroupService serverGroupService;
	@Resource
	BaseNodeService baseNodeService;
	@Resource
	NodeService nodeService;
	@Resource
	AdminPrivilegeService adminPrivilegeService;
	@Resource
	AdminService adminService;	
	@Resource
	MongoDbServiceFacade mongoDbServiceFacadeImpl;
	/**
	 * 服务组列表
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView("/serverGroup/serverGroupList");
		try {			
			List<ServerGroupModel> serverGroupList = mongoDbServiceFacadeImpl.getServerGroupListByCond(null);	
			List<ServerGroupTypeModel> serverGroupTypeList = mongoDbServiceFacadeImpl.getServerGroupTypeList();
			for(ServerGroupTypeModel t:serverGroupTypeList){
				for(ServerGroupModel s:serverGroupList){
					if(s.getGroupType().equals(t.getServerGroupTypeId())){
						t.getServerGroupList().add(s);
					}
				}
			}
			Map<String,Object> cond=Maps.newConcurrentMap();
			cond.put("status", BaseNodeStatusEnum.Approval.getCode());
			List<BaseNodeModel> baseNodeList = mongoDbServiceFacadeImpl.getBaseNodeList(cond);
			List<BaseServerModel> baseServerList = mongoDbServiceFacadeImpl.getBaseServerList(null);	
			modelAndView.addObject("baseServerList", baseServerList);
			modelAndView.addObject("hostList", baseNodeList);
			modelAndView.addObject("serverGroupTypeList", serverGroupTypeList);
		} catch (Exception e) {		
			LoggerUtil.error("查询服务组列表失败", e);
		}		
		return modelAndView;
	}

	/**
	 * 基础数据
	 * 
	 * @return
	 */
	@RequestMapping("/baseData")
	@ResponseBody
	public String  baseData(String groupId) {
		Map<String,Object> map=Maps.newConcurrentMap();
		try {	
			Map<String,Object> cond=Maps.newConcurrentMap();
			cond.put("status", BaseNodeStatusEnum.Approval.getCode());
			List<BaseNodeModel> baseNodeList = mongoDbServiceFacadeImpl.getBaseNodeList(cond);
			List<BaseServerModel> baseServerList = mongoDbServiceFacadeImpl.getBaseServerList(null);	
			if(StringUtils.isNotBlank(groupId)){
				List<NodeModel> nodeList = mongoDbServiceFacadeImpl.getNodeListByGroupId(groupId);
				List<ServerModel> serverList=mongoDbServiceFacadeImpl.getServerByGroupId(groupId);
				map.put("nodeList", nodeList);
				map.put("serverList", serverList);
			}
			map.put("msg", "success");
			map.put("baseNodeList", baseNodeList);
			map.put("baseServerList", baseServerList);
		} catch (Exception e) {		
			LoggerUtil.error("查询基础数据失败", e);
		}	
		return FastJsonUtil.toJson(map);
	}


	/**
	 * 增加服务组
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doAdd")
	@ResponseBody
	public String doAdd(ServerGroupRequest request) throws Exception {
		Map<String,Object> map=Maps.newConcurrentMap();
		try {
			String groupId=RandomUtil.randomNumber(10).toString();
			request.setGroupId(groupId);
			serverGroupService.add(request);
			List<NodeModel> nodeList=Lists.newArrayList();
			if(StringUtils.isNotBlank(request.getBaseNodeNeedAdd())){
				String[] baseNodeMacs=request.getBaseNodeNeedAdd().split(";");
				for(String mac:baseNodeMacs){
					nodeList.add(nodeService.addNode(groupId, mac));
				}		
			}
			if(StringUtils.isNotBlank(request.getBaseServerNeedAdd())){
				String[] baseServerIds=request.getBaseServerNeedAdd().split(";");
				for(String baseServerId:baseServerIds){
					serverService.addServer(groupId,baseServerId);
				}
			}	
			List <ServerModel> serverList=mongoDbServiceFacadeImpl.getServerByGroupId(request.getGroupId());
			for(ServerModel server:serverList){
				for(NodeModel node:nodeList){
					serverService.addServerNodeRelation(server.getServerId(),node.getNodeId(), node.getMac());
				}
			}
			map.put("msg", "success");
		} catch (Exception e) {
			LoggerUtil.error("增加服务组失败", e);
			map.put("msg", e.toString());
		}
		return FastJsonUtil.toJson(map);
	}

	
	/**
	 * 修改服务组
	 * 
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public String eidtServerById(ServerGroupRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			serverGroupService.edit(request);
			List<NodeModel> nodeList=Lists.newArrayList();
			if(StringUtils.isNotBlank(request.getBaseNodeNeedAdd())){
				String[] baseNodeMacs=request.getBaseNodeNeedAdd().split(";");
				for(String mac:baseNodeMacs){
					nodeList.add(nodeService.addNode(request.getGroupId(), mac));
				}
			}
			if(StringUtils.isNotBlank(request.getBaseNodeNeedDelete())){
				String[] baseNodeMacs=request.getBaseNodeNeedDelete().split(";");
				for(String mac:baseNodeMacs){
					nodeService.deleteNode(request.getGroupId(), mac);
				}
			}
			if(StringUtils.isNotBlank(request.getBaseServerNeedAdd())){
				String[] baseServerIds=request.getBaseServerNeedAdd().split(";");
				for(String baseServerId:baseServerIds){
					serverService.addServer(request.getGroupId(), baseServerId);
					
				}
			}
			if(StringUtils.isNotBlank(request.getBaseServerNeedDelete())){
				String[] baseServerIds=request.getBaseServerNeedDelete().split(";");
				for(String baseServerId:baseServerIds){
					serverService.deleteServer(request.getGroupId(), baseServerId);
				}
			}
			List <ServerModel> serverList=mongoDbServiceFacadeImpl.getServerByGroupId(request.getGroupId());
			for(ServerModel server:serverList){
				for(NodeModel node:nodeList){
					serverService.addServerNodeRelation(server.getServerId(),node.getNodeId(), node.getMac());
				}
			}
			map.put("msg", "success");
		} catch (Exception e) {		
			LoggerUtil.error("修改服务组失败", e);
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
	public String remove(String groupId) {
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isNotBlank(groupId)){		
			try {
				mongoDbServiceFacadeImpl.delServerGroup(groupId);
				serverService.deleteServer(groupId);
				nodeService.deleteNode(groupId,null);
				map.put("msg", "success");
			} catch (Exception e) {
				map.put("msg", e.toString());
				LoggerUtil.error("删除服务组类型失败", e);
			}	
		}
		return FastJsonUtil.toJson(map);
	}



}
