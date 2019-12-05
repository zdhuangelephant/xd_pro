package com.xiaodou.control.web.controller.server;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.control.web.controller.BaseController;
import com.xiaodou.control.model.server.LogModel;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;

/**
 * Created by on zhouhuan
 */
@Controller("logController")
@RequestMapping("/log")
public class LogController extends BaseController {
	@Resource
	MongoDbServiceFacade mongoDbServiceFacadeImpl;

	/**
	 * 获取日志
	 * 
	 * @return
	 */
	@RequestMapping("/getLog")
	public ModelAndView getLog(String mac, String serverId, String serverName) {
		ModelAndView modelAndView = new ModelAndView("/node/logList");
		try {
			serverName = new String(serverName.getBytes("iso-8859-1"), "utf-8");
			List<LogModel> list = mongoDbServiceFacadeImpl.getLogByCond(mac, serverId);
			for(LogModel n:list){
		      if(StringUtils.isBlank(n.getVersion())&&StringUtils.isNotBlank(n.getMsg())){
		     	 try {
		 			n.setMsg(new String(Base64Utils.decode(n.getMsg()),"UTF-8"));
		 		} catch (UnsupportedEncodingException e) {
		 			// TODO Auto-generated catch block
		 			n.setMsg(new String(Base64Utils.decode(n.getMsg())));
		 		}
		       }
			}
			modelAndView.addObject("logList", list);
			modelAndView.addObject("serverId", serverId);
			modelAndView.addObject("serverName", serverName);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LoggerUtil.error("获取日志失败", e);
		}	
		return modelAndView;
	}
}
