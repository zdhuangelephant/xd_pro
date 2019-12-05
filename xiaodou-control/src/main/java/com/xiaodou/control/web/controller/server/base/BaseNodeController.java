package com.xiaodou.control.web.controller.server.base;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.xiaodou.control.enums.BaseNodeStatusEnum;
import com.xiaodou.control.enums.DockerStatusEnum;
import com.xiaodou.control.model.server.base.BaseNodeModel;
import com.xiaodou.control.request.server.ServerRequest;
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
@Controller("baseNodeController")
@RequestMapping("/baseNode")
public class BaseNodeController extends BaseController {
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
	 * 获取所有主机信息
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView hostList(ServerRequest request) {
		ModelAndView modelAndView = new ModelAndView("/baseNode/baseNodeList");		
		try {
			List<BaseNodeModel> baseNodeList = mongoDbServiceFacadeImpl.getBaseNodeList(null);
			String format = "yyyy-MM-dd HH:mm:ss.SSS";
			for(BaseNodeModel node:baseNodeList){
				if(null != node.getTime() && !"".equals(node.getTime())){
					SimpleDateFormat sdf = new SimpleDateFormat(format);
				    Date date = sdf.parse(node.getTime());
				    if(System.currentTimeMillis()-date.getTime()>300000){
				    	node.setDockerStatus(DockerStatusEnum.UnknownDocker.getCode());
				    }
				 }
			}
			modelAndView.addObject("nodeList", baseNodeList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LoggerUtil.error("查询基础NODE列表失败", e);
		}
		return modelAndView;
	}
	/**
	 * 修改节点别名
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editAlias")
	public String editAlias(String mac, String alias) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			baseNodeService.editAlias(mac, alias);
			map.put("msg", "success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LoggerUtil.error("修改节点别名失败", e);
		}
		return FastJsonUtil.toJson(map);
	}
	
	/**
	 * 审核机器
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/approval")
	public String approval(String mac) {
		Map<String, String> map = new HashMap<String, String>();
		try {
		    Map<String, Object> input = new HashMap<String, Object>();
		    input.put("mac", mac);
		    BaseNodeModel node = new BaseNodeModel();
		    node.setStatus(BaseNodeStatusEnum.Approval.getCode());
		    mongoDbServiceFacadeImpl.editBaseNode(input, node);
			map.put("msg", "success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LoggerUtil.error("修改节点别名失败", e);
		}
		return FastJsonUtil.toJson(map);
	}
	
	/**
	 * 删除机器
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delBaseNode")
	public String delBaseNode(String mac, String alias) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("mac", mac);
			mongoDbServiceFacadeImpl.delBaseNode(input);
			nodeService.deleteNode(null, mac);	
			map.put("msg", "success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LoggerUtil.error("删除失败", e);
		}
		return FastJsonUtil.toJson(map);
	}
	
}
