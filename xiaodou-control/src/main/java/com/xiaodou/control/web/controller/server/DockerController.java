package com.xiaodou.control.web.controller.server;

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
import com.xiaodou.control.enums.StatusEnum;
import com.xiaodou.control.model.server.ProjectModel;
import com.xiaodou.control.model.server.base.BaseNodeModel;
import com.xiaodou.control.request.server.AllContainers;
import com.xiaodou.control.request.server.DockerImages;
import com.xiaodou.control.service.admin.AdminService;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;
import com.xiaodou.control.service.ftp.FtpService;
import com.xiaodou.control.service.server.BaseNodeService;
import com.xiaodou.control.service.server.ServerGroupService;
import com.xiaodou.control.service.server.ServerService;
import com.xiaodou.control.web.controller.BaseController;

/**
 * Created by on zhouhuan
 */
@Controller("dockerController")
@RequestMapping("/docker")
public class DockerController extends BaseController {
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

	@RequestMapping("/dockerDetail")
	public ModelAndView dockerDetail(String mac) {
		ModelAndView modelAndView = new ModelAndView("/docker/dockerDetail");
		try {
			BaseNodeModel baseNodeModel = mongoDbServiceFacadeImpl.getBaseNodeByMac(mac);
			List<AllContainers> cList=baseNodeModel.getContainers();
			List<DockerImages> iList=baseNodeModel.getDockerImages();
			List<ProjectModel> pList=mongoDbServiceFacadeImpl.getProjectByMac(mac);
			String format = "yyyy-MM-dd HH:mm:ss.SSS";
			for(ProjectModel project:pList){
				if(null != project.getUpdateTime() && !"".equals(project.getUpdateTime())){
					SimpleDateFormat sdf = new SimpleDateFormat(format);
				    Date date = sdf.parse(project.getUpdateTime());
				    if(System.currentTimeMillis()-date.getTime()>300000){
				    	project.setState(StatusEnum.STOP.getCode());
				    }
				 }
			}
			if(baseNodeModel!=null){
				modelAndView.addObject("mac", mac);
				modelAndView.addObject("alias", baseNodeModel.getAlias());
				modelAndView.addObject("cList", cList);
				modelAndView.addObject("iList", iList);
				modelAndView.addObject("pList", pList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LoggerUtil.error("查询NODE列表失败", e);
		}
		return modelAndView;		
	}
	/**
	 * 修改镜像命令
	 * 
	 * @return
	 */
	@RequestMapping("/eidtImageCommand")
	@ResponseBody
	public String eidtImageCommand(String mac,String startCommand,String imageId) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			BaseNodeModel baseNodeModel = mongoDbServiceFacadeImpl.getBaseNodeByMac(mac);
			if(baseNodeModel!=null){
				for(DockerImages d:baseNodeModel.getDockerImages()){
					if(d.getId().equals(imageId)){
						 d.setStartCommand(startCommand);
						 Map<String, Object> input = new HashMap<String, Object>();
					     input.put("mac", mac);
					     mongoDbServiceFacadeImpl.editBaseNode(input, baseNodeModel);
						 break;
					}
				}
			}
			map.put("flag", "true");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LoggerUtil.error("修改镜像命令失败", e);
		}
		return FastJsonUtil.toJson(map);
	}
	/**
	 * 删除废弃应用
	 * 
	 * @return
	 */
	@RequestMapping("/delProject")
	@ResponseBody
	public String delProject(String projectId) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			 mongoDbServiceFacadeImpl.delProject(projectId);
			map.put("flag", "true");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LoggerUtil.error("删除应用失败", e);
		}
		return FastJsonUtil.toJson(map);
	}
	
	
}
