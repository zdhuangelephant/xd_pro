package com.xiaodou.control.web.controller.server;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.control.enums.MiddleServerEnum;
import com.xiaodou.control.model.server.MiddleServerModel;
import com.xiaodou.control.request.server.MiddleServerRequest;
import com.xiaodou.control.service.admin.AdminPrivilegeService;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;
import com.xiaodou.control.service.ftp.FtpService;
import com.xiaodou.control.service.server.BaseNodeService;
import com.xiaodou.control.service.server.NodeService;
import com.xiaodou.control.service.server.ServerGroupService;
import com.xiaodou.control.service.server.ServerService;
import com.xiaodou.control.web.controller.BaseController;

/**
 * Created by on zhouhuan
 */
@Controller("middleServerController")
@RequestMapping("/middleServer")
public class MiddleServerController extends BaseController {
	@Resource
	ServerService serverService;
	@Resource
	BaseNodeService baseNodeService;
	@Resource
	NodeService nodeService;
	
	@Resource
	FtpService ftpService;
	@Resource
	ServerGroupService serverGroupService;
	@Resource
	AdminPrivilegeService adminPrivilegeService;
	@Resource
	MongoDbServiceFacade mongoDbServiceFacadeImpl;
	
	/**
	 * 新增server
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add")
	@ResponseBody
	public String add(MiddleServerRequest request){	
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			MiddleServerModel middleServer=new MiddleServerModel();
			String serverId=RandomUtil.randomNumber(10).toString();
			middleServer.setServerId(serverId);
			middleServer.setServerType(request.getServerType());
			middleServer.setServerName(MiddleServerEnum.getName(request.getServerType()));
			middleServer.setGroupId(request.getGroupId());
			middleServer.setInstallCommand(request.getInstallCommand());
			middleServer.setProjectDir(request.getProjectDir());
			middleServer.setProjectPort(request.getProjectPort());
			middleServer.setServerUserName(request.getServerUserName());
			middleServer.setServerPassword(request.getServerPassword());
			middleServer.setUser(request.getUser());
			middleServer.setBlockMessageCount(request.getBlockMessageCount());
			middleServer.setTimingMonitoring(request.getTimingMonitoring());
			mongoDbServiceFacadeImpl.addMiddleServer(middleServer);
			map.put("msg", "success");
		} catch (Exception e) {
			LoggerUtil.error("新增中间件服务失败", e);
			map.put("msg", e.toString());
		}	
		return FastJsonUtil.toJson(map);
	}
	
	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String serverId) {
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isNotBlank(serverId)){		
			try {
				mongoDbServiceFacadeImpl.delMiddleServer(serverId);
				map.put("msg", "success");
			} catch (Exception e) {
				map.put("msg", e.toString());
				LoggerUtil.error("删除中间件服务失败", e);
			}	
		}
		return FastJsonUtil.toJson(map);
	}
	/**
	 * 编辑server
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public String edit(MiddleServerRequest request){	
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			MiddleServerModel middleServer=new MiddleServerModel();
			String serverId=request.getServerId();
			middleServer.setServerId(serverId);
			middleServer.setServerType(request.getServerType());
			middleServer.setServerName(MiddleServerEnum.getName(request.getServerType()));
			middleServer.setGroupId(request.getGroupId());
			middleServer.setInstallCommand(request.getInstallCommand());
			middleServer.setProjectDir(request.getProjectDir());
			middleServer.setProjectPort(request.getProjectPort());
			middleServer.setServerUserName(request.getServerUserName());
			middleServer.setServerPassword(request.getServerPassword());
			middleServer.setUser(request.getUser());
			middleServer.setBlockMessageCount(request.getBlockMessageCount());
			middleServer.setTimingMonitoring(request.getTimingMonitoring());
			Map<String, Object> cond= new HashMap<String, Object>();
			cond.put("serverId", serverId);
			mongoDbServiceFacadeImpl.editMiddleServer(cond, middleServer);
			map.put("msg", "success");
		} catch (Exception e) {
			LoggerUtil.error("编辑中间件服务失败", e);
			map.put("msg", e.toString());
		}	
		return FastJsonUtil.toJson(map);
	}
}
