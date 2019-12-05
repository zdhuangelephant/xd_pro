package com.xiaodou.control.web.controller.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.StringUtils;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.control.model.server.NodeModel;
import com.xiaodou.control.model.server.ServerModel;
import com.xiaodou.control.model.server.ServerNodeRelationModel;
import com.xiaodou.control.request.server.NodeRequest;
import com.xiaodou.control.request.server.ServerRequest;
import com.xiaodou.control.service.admin.AdminService;
import com.xiaodou.control.service.admin.AdminUser;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;
import com.xiaodou.control.service.ftp.FtpService;
import com.xiaodou.control.service.server.BaseNodeService;
import com.xiaodou.control.service.server.NodeService;
import com.xiaodou.control.service.server.ServerGroupService;
import com.xiaodou.control.service.server.ServerService;
import com.xiaodou.control.vo.FtpFileVo;
import com.xiaodou.control.web.controller.BaseController;

/**
 * Created by on zhouhuan
 */
@Controller("nodeController")
@RequestMapping("/node")
public class NodeController extends BaseController {
	@Resource
	NodeService nodeService;
	@Resource
	BaseNodeService baseNodeService;
	@Resource
	FtpService ftpService;
	@Resource
	ServerService serverService;
	@Resource
	MongoDbServiceFacade mongoDbServiceFacadeImpl;
	@Resource
	AdminService adminService;
	@Resource
	ServerGroupService serverGroupService;

	@RequestMapping("/list")
	public ModelAndView list(ServerRequest request) {
		ModelAndView modelAndView = new ModelAndView("/node/nodeList");
		try {
			List<NodeModel> nodeList =nodeService.getNodeModelListByServerId(request.getServerId());
			//包装发行版本和普通版本
			List<FtpFileVo> fileList = ftpService.getFileNameList(request.getWarAdress());
			List<FtpFileVo> fileListNoRelease = Lists.newArrayList();
			ftpService.packageRelease(request, fileList, fileListNoRelease);
			//end包装发行版本和普通版本
			modelAndView.addObject("nodeList", nodeList);
			modelAndView.addObject("fileList", fileList);
			modelAndView.addObject("fileListNoRelease", fileListNoRelease);
			modelAndView.addObject("serverId", request.getServerId());
			modelAndView.addObject("serverName", request.getServerName());
			modelAndView.addObject("groupServiceType",request.getGroupServiceType());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LoggerUtil.error("查询NODE列表失败", e);
		}
		return modelAndView;
	}
	/**
	 * 修改权重
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editWeight")
	public String editWeight(String serverId,String strategy,String mac,String weight) {
		Map<String, String> map = new HashMap<String, String>();
		if(!StringUtils.isBlank(strategy)){
			serverService.editStrategy(serverId, mac,strategy,weight);
		}
		map.put("msg", "success");
		return FastJsonUtil.toJson(map);
	}
	



	/**
	 * 生成发行版
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createReleaseVersion")
	public String createReleaseVersion(String serverId, String version)
	 {
		Map<String, Object> map = new HashMap<String, Object>();
		try{			
			ServerModel server = serverService.getById(serverId);
			if (server != null) {
				String path = server.getWarAdress();
				ftpService.createReleaseVersion(path, version);
				map.put("msg", "success");
			}
		}catch(Exception e){
			LoggerUtil.error("生成发行版失败", e);
	    }
	    return FastJsonUtil.toJson(map);
	}

	

	/**
	 * 增加命令
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addCommand")
	public String addCommand(NodeRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			AdminUser user = this.getUser();
			String id = UUID.randomUUID().toString();
			baseNodeService.addCommand(request,id);
			if(!StringUtils.isBlank(request.getVersion())){
				serverService.editVersion(request.getServerId(), request.getMac(),request.getVersion());
			}
			nodeService.addLog(request, id, user.getUserId().toString(),user.getRealName());
			map.put("msg", "success");
		} catch (TException e) {
			LoggerUtil.error("增加命令失败", e);
		}
		return FastJsonUtil.toJson(map);
	}
	
	/**
	 * 批量增加命令
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/allAddCommand")
	public String allAddCommand(String nodeJson) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			List<NodeRequest> list = FastJsonUtil.fromJsons(nodeJson,new TypeReference<List<NodeRequest>>() {});
			for (NodeRequest n : list) {
				AdminUser user = this.getUser();
				String id = UUID.randomUUID().toString();
				baseNodeService.addCommand(n, id);
				serverService.editVersion(n.getServerId(), n.getMac(),
						n.getVersion());
				nodeService.addLog(n, id, user.getUserId().toString(),user.getRealName());
			}
			map.put("msg", "success");
		} catch (TException e) {
			LoggerUtil.error("批量增加命令失败", e);
		}
		return FastJsonUtil.toJson(map);
	}
	
	
	//##########################MiddleServer################
	
	@RequestMapping("/nodeMiddleServerList")
	public ModelAndView nodeMiddleServerList(String groupId,String serverId,String serverName) {
		ModelAndView modelAndView = new ModelAndView("/node/nodeMiddleServerList");
		try {
			//主机列表
			List<NodeModel> nodeList  =nodeService.packageNodeList(mongoDbServiceFacadeImpl.getNodeListByGroupId(groupId));
			modelAndView.addObject("nodeList", nodeList);
			modelAndView.addObject("serverName", serverName);
			modelAndView.addObject("serverId", serverId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LoggerUtil.error("查询NODE列表失败", e);
		}
		return modelAndView;
	}

}
