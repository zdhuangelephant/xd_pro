package com.xiaodou.control.web.controller.server;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.control.web.controller.BaseController;
import com.xiaodou.control.model.server.LogModel;
import com.xiaodou.control.model.server.NginxLogModel;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;

/**
 * Created by on zhouhuan
 */
@Controller("nginxLogController")
@RequestMapping("/nginxLog")
public class NginxLogController extends BaseController {
	@Resource
	MongoDbServiceFacade mongoDbServiceFacadeImpl;

	/**
	 * 获取日志
	 * 
	 * @return
	 */
	@RequestMapping("/getNginxLog")
	public ModelAndView getNginxLog(String mac, String alias) {
		ModelAndView modelAndView = new ModelAndView("/baseNode/nginxLogList");
		try {
			alias = new String(alias.getBytes("iso-8859-1"), "utf-8");
			List<NginxLogModel> list = mongoDbServiceFacadeImpl.getNginxLogByCond(mac);
			modelAndView.addObject("nginxLogList", list);
			modelAndView.addObject("alias", alias);		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LoggerUtil.error("获取日志失败", e);
		}	
		return modelAndView;
	}
}
