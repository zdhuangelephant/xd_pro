package com.xiaodou.control.web.controller.server.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.control.model.server.base.BaseServerModel;
import com.xiaodou.control.request.server.BaseServerRequest;
import com.xiaodou.control.service.admin.AdminPrivilegeService;
import com.xiaodou.control.service.admin.AdminService;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;
import com.xiaodou.control.service.server.BaseNodeService;
import com.xiaodou.control.service.server.BaseServerService;
import com.xiaodou.control.service.server.ServerGroupService;
import com.xiaodou.control.service.server.ServerService;
import com.xiaodou.control.web.controller.BaseController;

/**
 * Created by on zhouhuan
 */
@Controller("baseServerController")
@RequestMapping("/baseServer")
public class BaseServerController extends BaseController {
	@Resource
	BaseServerService baseServerService;
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
	 * 基础服务列表
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView("/baseServer/baseServerList");
		try {			
			List<BaseServerModel> baseServerList = mongoDbServiceFacadeImpl.getBaseServerList(null);	
			modelAndView.addObject("baseServerList", baseServerList);
		} catch (Exception e) {		
			LoggerUtil.error("查询基础服务列表失败", e);
		}		
		return modelAndView;
	}

	


	/**
	 * 增加基础服务类型
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doAdd")
	@ResponseBody
	public String doAdd(BaseServerRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();		
			try {
				Errors errors = request.validate();
				if (errors.hasErrors()) {
					map.put("msg", this.getErrMsg(errors));
					return FastJsonUtil.toJson(map);
				}
				BaseServerModel model=new BaseServerModel();
				model.setBaseServerId(RandomUtil.randomNumber(10).toString());
				model.setBaseDir(request.getBaseDir());
				model.setServerName(request.getServerName());
				model.setTomcatPort(request.getTomcatPort());
				model.setUser(request.getUser());
				model.setWarAdress(request.getWarAdress());
				mongoDbServiceFacadeImpl.addBaseServer(model);
				map.put("msg", "success");
			} catch (Exception e) {
				map.put("msg", e.toString());
				LoggerUtil.error("增加服务组类型失败", e);
			}	
		return FastJsonUtil.toJson(map);
	}

	
	
	
	/**
	 * 修改基础服务
	 * 
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public String edit(BaseServerRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isNotBlank(request.getBaseServerId())){		
			try {
				Errors errors = request.validate();
				if (errors.hasErrors()) {
					map.put("msg", this.getErrMsg(errors));
					return FastJsonUtil.toJson(map);
				}
				BaseServerModel model=new BaseServerModel();
				model.setBaseDir(request.getBaseDir());
				model.setServerName(request.getServerName());
				model.setTomcatPort(request.getTomcatPort());
				model.setUser(request.getUser());
				model.setWarAdress(request.getWarAdress());
				Map<String, Object> input =Maps.newConcurrentMap();
				input.put("baseServerId", request.getBaseServerId());
				mongoDbServiceFacadeImpl.editBaseServer(input, model);
				map.put("msg", "success");
			} catch (Exception e) {
				map.put("msg", e.toString());
				LoggerUtil.error("修改服务组类型失败", e);
			}	
		}
		return FastJsonUtil.toJson(map);
	}

	/**
	 * 删除基础服务
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public String remove(String baseServerId) {
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isNotBlank(baseServerId)){		
			try {
				mongoDbServiceFacadeImpl.delBaseServer(baseServerId);
				map.put("msg", "success");
			} catch (Exception e) {
				map.put("msg", e.toString());
				LoggerUtil.error("删除基础服务失败", e);
			}	
		}
		return FastJsonUtil.toJson(map);
	}

}
