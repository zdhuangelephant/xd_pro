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

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.control.model.server.ServerModel;
import com.xiaodou.control.request.server.ServerRequest;
import com.xiaodou.control.service.admin.AdminPrivilegeService;
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
@Controller("serverController")
@RequestMapping("/server")
public class ServerController extends BaseController {
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
	 * 修改server
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/editServer")
	@ResponseBody
	public String editServer(String serverId,String requestUrl){	
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(serverId)){
			map.put("msg", "serverId不能为空");
			return FastJsonUtil.toJson(map);
		}
		try {
			serverService.editServerRequestUrl(serverId, requestUrl);
			map.put("msg", "success");
		} catch (Exception e) {
			LoggerUtil.error("获取版本号失败", e);
			map.put("msg", e.toString());
		}	
		return FastJsonUtil.toJson(map);
	}

	/**
	 * 部署时获取版本号
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getVersion")
	@ResponseBody
	public String getVersion(ServerRequest request){	
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ServerModel server= serverService.getById(request.getServerId());
			List<FtpFileVo> fileList = new ArrayList<FtpFileVo>();
			if (server != null ) {
				fileList = ftpService.getFileNameList(server.getWarAdress());
			}
			map.put("fileList", fileList);
			map.put("msg", "success");
		} catch (Exception e) {
			LoggerUtil.error("获取版本号失败", e);
		}	
		return FastJsonUtil.toJson(map);
	}

}
