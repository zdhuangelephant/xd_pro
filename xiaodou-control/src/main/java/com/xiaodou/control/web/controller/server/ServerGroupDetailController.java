package com.xiaodou.control.web.controller.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.control.enums.BaseNodeStatusEnum;
import com.xiaodou.control.enums.GroupServiceTypeEnum;
import com.xiaodou.control.model.server.MiddleServerModel;
import com.xiaodou.control.model.server.NodeModel;
import com.xiaodou.control.model.server.ServerGroupModel;
import com.xiaodou.control.model.server.ServerModel;
import com.xiaodou.control.model.server.base.BaseNodeModel;
import com.xiaodou.control.model.server.base.BaseServerModel;
import com.xiaodou.control.request.server.ServerRequest;
import com.xiaodou.control.service.admin.AdminPrivilegeService;
import com.xiaodou.control.service.admin.AdminService;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;
import com.xiaodou.control.service.server.BaseNodeService;
import com.xiaodou.control.service.server.NodeService;
import com.xiaodou.control.service.server.ServerGroupService;
import com.xiaodou.control.service.server.ServerService;
import com.xiaodou.control.util.compartor.ServerCompartor;
import com.xiaodou.control.web.controller.BaseController;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * Created by on zhouhuan
 */
@Controller("serverGroupDetailController")
@RequestMapping("/serverGroupDetail")
public class ServerGroupDetailController extends BaseController {
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
	 * 服务列表
	 * 
	 * @return
	 */
	@RequestMapping("/detail")
	public ModelAndView list(ServerRequest request) {
		ModelAndView modelAndView = new ModelAndView("/serverGroup/serverGroupDetail");
		try {
			ServerGroupModel serverGroupModel= mongoDbServiceFacadeImpl.getServerGroupById(request.getGroupId());
			//服务列表
			List<ServerModel> serverList = serverService.packageServerList(mongoDbServiceFacadeImpl.getServerByGroupId(request.getGroupId()));
			modelAndView.addObject("serverList", serverList);
			
			//主机列表
			List<NodeModel> nodeList  =nodeService.packageNodeList(mongoDbServiceFacadeImpl.getNodeListByGroupId(request.getGroupId()));
			modelAndView.addObject("nodeList", nodeList);
			
			//基础服务列表(去除已经添加的)
			if(serverGroupModel.getType().equals("1")){
				List<BaseServerModel> bServerList=Lists.newArrayList();
				List<BaseServerModel> baseServerList = mongoDbServiceFacadeImpl.getBaseServerList(null);	
				for(int i=baseServerList.size()-1;i>=0;i--){
					for(int j=serverList.size()-1;j>=0;j--){
						if(baseServerList.get(i).getBaseServerId().equals(serverList.get(j).getBaseServerId())){
							bServerList.add(baseServerList.get(i));
						}
					}
				}
				baseServerList.removeAll(bServerList);
				ServerCompartor mc = new ServerCompartor();
				Collections.sort(baseServerList, mc);
				modelAndView.addObject("baseServerList", baseServerList);
			}else if(serverGroupModel.getType().equals("2")){
				Map<String,Object>input=Maps.newConcurrentMap();
				input.put("groupId", request.getGroupId());
				List<MiddleServerModel> middleServerList=mongoDbServiceFacadeImpl.getMiddleServerListByCond(input);
				modelAndView.addObject("middleServerList", middleServerList);
			}
			//所有host列表(去除已经添加的)
			List<BaseNodeModel> bNodeList=Lists.newArrayList();
			Map<String,Object> cond=Maps.newConcurrentMap();
			cond.put("status", BaseNodeStatusEnum.Approval.getCode());
			List<BaseNodeModel> baseNodeList = mongoDbServiceFacadeImpl.getBaseNodeList(cond);
			for(int i=baseNodeList.size()-1;i>=0;i--){
				for(int j=nodeList.size()-1;j>=0;j--){
					if(baseNodeList.get(i).getMac().equals(nodeList.get(j).getMac())){
						bNodeList.add(baseNodeList.get(i));
					}
				}
			}
			baseNodeList.removeAll(bNodeList);
			modelAndView.addObject("baseNodeList", baseNodeList);			
			//服务组信息
			
			modelAndView.addObject("type", serverGroupModel.getType());
			modelAndView.addObject("groupId", request.getGroupId());
			modelAndView.addObject("groupName", serverGroupModel.getGroupName());
			//服务组环境类型以及服务组类型
			modelAndView.addObject("groupServiceType", serverGroupModel.getGroupServiceType());
			modelAndView.addObject("groupServiceTypeName", GroupServiceTypeEnum.getName(serverGroupModel.getGroupServiceType()));		
			//服务组类型
			modelAndView.addObject("groupType",serverGroupModel.getGroupType());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LoggerUtil.error("查询服务列表失败", e);
		}
		return modelAndView;
	}
	
	
	
	/**
	 * 关联服务
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/relationServer")
	@ResponseBody
	public String doAdd(String baseServerId,String groupId){
		Map<String, String> map = new HashMap<String, String>();
		try {
			ServerModel server=serverService.addServer(groupId, baseServerId);
			List<NodeModel> nodeList=mongoDbServiceFacadeImpl.getNodeListByGroupId(groupId);
			for(NodeModel node:nodeList){
				serverService.addServerNodeRelation(server.getServerId(),node.getNodeId(), node.getMac());
			}
			map.put("msg", "success");
		} catch (Exception e) {
			LoggerUtil.error("增加服务失败", e);
		}
		return FastJsonUtil.toJson(map);
	}
	

	/**
	 * 删除关联服务
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delRelationServer")
	@ResponseBody
	public String delServer(String baseServerId,String groupId) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			serverService.deleteServer(groupId, baseServerId);
			map.put("msg", "success");
		} catch (Exception e) {
			LoggerUtil.error("增加服务失败", e);
		}
		return FastJsonUtil.toJson(map);
	}
	
	/**
	 * 关联主机
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/relationNode")
	@ResponseBody
	public String relationNode(String mac,String groupId){
		Map<String, String> map = new HashMap<String, String>();
		try {
			NodeModel node=nodeService.addNode(groupId, mac);
			List <ServerModel> serverList=mongoDbServiceFacadeImpl.getServerByGroupId(groupId);
			for(ServerModel server:serverList){
				serverService.addServerNodeRelation(server.getServerId(),node.getNodeId(), node.getMac());
			}
			map.put("msg", "success");
		} catch (Exception e) {
			LoggerUtil.error("增加服务失败", e);
		}
		return FastJsonUtil.toJson(map);
	}
	
	/**
	 * 删除关联主机
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delRelationNode")
	@ResponseBody
	public String delRelationNode(String mac,String groupId){
		Map<String, String> map = new HashMap<String, String>();
		try {
			nodeService.deleteNode(groupId, mac);
			map.put("msg", "success");
		} catch (Exception e) {
			LoggerUtil.error("增加服务失败", e);
		}
		return FastJsonUtil.toJson(map);
	}
	

}
