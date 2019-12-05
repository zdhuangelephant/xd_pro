package com.xiaodou.control.web.controller.server.out;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.control.enums.CommandEnum;
import com.xiaodou.control.model.server.NodeModel;
import com.xiaodou.control.model.server.ServerGroupModel;
import com.xiaodou.control.model.server.ServerModel;
import com.xiaodou.control.model.server.ServerNodeRelationModel;
import com.xiaodou.control.model.server.base.BaseServerModel;
import com.xiaodou.control.request.server.NodeRequest;
import com.xiaodou.control.service.admin.AdminPrivilegeService;
import com.xiaodou.control.service.admin.AdminService;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;
import com.xiaodou.control.service.server.BaseNodeService;
import com.xiaodou.control.service.server.NodeService;
import com.xiaodou.control.service.server.ServerGroupService;
import com.xiaodou.control.service.server.ServerService;
import com.xiaodou.control.web.controller.BaseController;

/**
 * Created by on zhouhuan
 */
@Controller("outApiController")
@RequestMapping("/outApi")
public class OutApiController extends BaseController {
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
	/**
	 * @param groupId
	 * @param serverName
	 * @param version
	 * @return
	 */
	@RequestMapping("/jenkinsDeploy")
	@ResponseBody
	public String jenkinsDeploy(String groupId,String serverName,String version) {
		try {	
			if(StringUtils.isBlank(groupId)||StringUtils.isBlank(serverName)||StringUtils.isBlank(version)){
				return "fail";
			}
			Map <String,Object> cond=Maps.newConcurrentMap();
			cond.put("serverName", serverName);
			List<BaseServerModel> baseServerList=mongoDbServiceFacadeImpl.getBaseServerList(cond);
			ServerGroupModel serverGroup=mongoDbServiceFacadeImpl.getServerGroupById(groupId);
			List<NodeModel> nodeList=mongoDbServiceFacadeImpl.getNodeListByGroupId(groupId);
			List<ServerModel> serverList=Lists.newArrayList();
			if(serverGroup==null||baseServerList==null){
				return "fail";
			}
			if(baseServerList.size()==1){
				BaseServerModel baseServer=baseServerList.get(0);
				if(baseServer!=null){
					Map <String,Object> cond2=Maps.newConcurrentMap();
					cond2.put("baseServerId", baseServer.getBaseServerId());
					cond2.put("groupId", groupId);
					serverList=mongoDbServiceFacadeImpl.getServerListByCond(cond2);	
					if(serverList==null||serverList.size()==0){
						serverList.add(serverService.addServer(groupId, baseServer.getBaseServerId()));	
					}
					
				}
			}else if(baseServerList.size()==0){
				String baseServerId=RandomUtil.randomNumber(11).toString();
				BaseServerModel  baseServer=new BaseServerModel();
				baseServer.setBaseDir("/home/work/xiaodou");
				baseServer.setBaseServerId(baseServerId);
				baseServer.setServerName(serverName);
				baseServer.setTomcatPort(RandomUtil.randomNumber(4).toString());
				baseServer.setUser("work");
				baseServer.setWarAdress(serverName);
				mongoDbServiceFacadeImpl.addBaseServer(baseServer);
				serverList.add(serverService.addServer(groupId, baseServerId));	
			}else{
				return "fail";			
			}
			for(ServerModel server:serverList){
				for(NodeModel node:nodeList){
					Map <String,Object> cond1=Maps.newConcurrentMap();
					cond1.put("serverId",server.getServerId());
					cond1.put("nodeId", node.getNodeId());
					List<ServerNodeRelationModel> serverNodeRelationList=mongoDbServiceFacadeImpl.getServerNodeRelationListByCond(cond1);	
					if(serverNodeRelationList!=null&&serverNodeRelationList.size()>0){		
					}else{
						serverService.addServerNodeRelation(server.getServerId(),node.getNodeId(), node.getMac());
					}
					String nodeCommandId=RandomUtil.randomNumber(11).toString();
					NodeRequest request=new NodeRequest();
					request.setCommandId(CommandEnum.Deploy.getCode());
					request.setCommandName(CommandEnum.Deploy.getName());
					request.setMac(node.getMac());
					request.setServerId(server.getServerId());
					request.setVersion(version);
					baseNodeService.addCommand(request, nodeCommandId);
					serverService.editVersion(server.getServerId(),node.getMac(),version);		
					nodeService.addLog(request,nodeCommandId,"00000","auto");
					if(StringUtils.isNotBlank(server.getRequestUrl())){
						this.sendRequest(server.getRequestUrl());
					}
				}	
			}
			
		} catch (Exception e) {		
			LoggerUtil.error("自动构建部署失败", e);
			return "fail";
		}		
		return "success";
	}
	
	
	public void sendRequest(String requestUrl){
		String urls[]=requestUrl.split(";");
		try {
			if(urls.length>0){
				Thread.sleep(60000);
				for(String url:urls){
					 this.send(url);
				 }
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	 
	}

	public void send(String url){
		HttpMethod httpMethod;
		HttpUtil http = HttpUtil.getInstance();
		httpMethod = HttpMethodUtil.getGetMethod(url);
		http.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
	    http.setMethod(httpMethod);
	    http.getHttpResult();		
	}


}
