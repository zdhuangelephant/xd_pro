package com.xiaodou.control.web.controller.server.expand;

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
import com.xiaodou.control.model.server.NginxServerGroupRelationModel;
import com.xiaodou.control.model.server.ServerGroupModel;
import com.xiaodou.control.model.server.ServerGroupTypeModel;
import com.xiaodou.control.model.server.expand.LogMonitorGroupRelationModel;
import com.xiaodou.control.model.server.expand.LogMonitorModel;
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
@Controller("logMonitorController")
@RequestMapping("/logMonitor")
public class LogMonitorController extends BaseController {
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
	 * 日志收集列表
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView("/expand/logMonitorList");
		try {			
			List<LogMonitorModel> logMonitorList = mongoDbServiceFacadeImpl.getLogMonitorList();	
			modelAndView.addObject("logMonitorList", logMonitorList);
		} catch (Exception e) {		
			LoggerUtil.error("查询日志收集配置列表失败", e);
		}		
		return modelAndView;
	}

	/**
	 * 增加日志收集配置
	 * 
	 * @return
	 * @throws Exception
	 */
	/**
	 * @param logPath
	 * @param logPrefix
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doAdd")
	@ResponseBody
	public String doAdd(String logPath,String logPrefix) throws Exception {
		Map<String, String> map = new HashMap<String, String>();		
			try {
				LogMonitorModel model=new LogMonitorModel();
				model.setLogMonitorId(RandomUtil.randomNumber(10).toString());
				model.setLogPath(logPath);
				model.setLogPrefix(logPrefix);
				mongoDbServiceFacadeImpl.addLogMonitor(model);			
				map.put("msg", "success");
			} catch (Exception e) {
				map.put("msg", e.toString());
				LoggerUtil.error("增加日志收集配置失败", e);
			}	
		return FastJsonUtil.toJson(map);
	}

	
	
	/**
	 * 修改日志收集配置
	 * 
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public String edit(String logMonitorId,String logPath,String logPrefix) {
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isNotBlank(logMonitorId)){		
			try {
				LogMonitorModel model=new LogMonitorModel();
				model.setLogPath(logPath);
				model.setLogPrefix(logPrefix);
				Map<String, Object> input =Maps.newConcurrentMap();
				input.put("logMonitorId", logMonitorId);
				mongoDbServiceFacadeImpl.editLogMonitor(input, model);
				map.put("msg", "success");
			} catch (Exception e) {
				map.put("msg", e.toString());
				LoggerUtil.error("修改日志收集配置失败", e);
			}	
		}
		return FastJsonUtil.toJson(map);
	}

	/**
	 * 删除日志收集配置
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public String remove(String logMonitorId) {
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isNotBlank(logMonitorId)){		
			try {
				mongoDbServiceFacadeImpl.delLogMonitor(logMonitorId);
				Map<String,Object> input =Maps.newConcurrentMap();
				input.put("logMonitorId", logMonitorId);
				mongoDbServiceFacadeImpl.delLogMonitorGroupRelationByCond(input);
				map.put("msg", "success");
			} catch (Exception e) {
				map.put("msg", e.toString());
				LoggerUtil.error("删除日志收集配置", e);
			}	
		}
		return FastJsonUtil.toJson(map);
	}

	
	/**
	 * 日志收集服务组关联关系检测
	 * 
	 * @return
	 */
	@RequestMapping("/relationServerGroup")
	@ResponseBody
	public String relationServerGroup(String logMonitorId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<ServerGroupModel> serverGroupList = mongoDbServiceFacadeImpl.getServerGroupListByCond(null);
			for(ServerGroupModel serverGroup:serverGroupList){
				ServerGroupTypeModel type=mongoDbServiceFacadeImpl.getServerGroupTypeById(serverGroup.getGroupType());
				if(type!=null){
					serverGroup.setGroupTypeName(type.getServerGroupTypeName());
				}
			}
			Map<String, Object> cond=Maps.newConcurrentMap();
			cond.put("logMonitorId", logMonitorId);
			List<LogMonitorGroupRelationModel> relationList=mongoDbServiceFacadeImpl.getLogMonitorGroupRelationListByCond(cond);
			map.put("relationList", relationList);
			map.put("groupList", serverGroupList);
			map.put("msg", "success");
		} catch (Exception e) {		
			LoggerUtil.error("查询服务组失败", e);
		}		
		return FastJsonUtil.toJson(map);
	}
	
	
	/**
	 * 设置LogMonitor与ServerGroup之间的关系
	 * 
	 * @param adminId
	 * @param needDeleteId
	 * @param needAddId
	 * @return
	 */
	@RequestMapping("/editLogMonitorServerGroup")
	@ResponseBody
	public String editLogMonitorServerGroup(String logMonitorId, String needDeleteId,
			String needAddId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if (StringUtils.isNotBlank(needDeleteId)) {
				String[] needDeleteIdArray = needDeleteId.split(";");
				for (String id : needDeleteIdArray) {			
					Map<String,Object> input =Maps.newConcurrentMap();
					input.put("logMonitorId", logMonitorId);
					input.put("groupId", id);
					mongoDbServiceFacadeImpl.delLogMonitorGroupRelationByCond(input);
				}
			}

			if (StringUtils.isNotBlank(needAddId)) {
				String[] needAddIdArray = needAddId.split(";");
				for (String id : needAddIdArray) {
					LogMonitorGroupRelationModel model=new LogMonitorGroupRelationModel();
					model.setLogMonitorGroupRelationId(RandomUtil.randomNumber(10).toString());
					model.setGroupId(id);
					model.setLogMonitorId(logMonitorId);
					mongoDbServiceFacadeImpl.addLogMonitorGroupRelation(model);
				}
			}
			map.put("msg", "success");
		}catch(Exception e){
			LoggerUtil.error("设置管理员角色失败", e);
		}
		return FastJsonUtil.toJson(map);
	}
	
}
