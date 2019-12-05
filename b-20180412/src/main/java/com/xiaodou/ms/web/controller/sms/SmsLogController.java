package com.xiaodou.ms.web.controller.sms;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.ms.model.sms.SmsLogModel;
import com.xiaodou.ms.service.sms.SmsLogService;
import com.xiaodou.ms.web.request.sms.SmsLogRequest;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * @name @see com.xiaodou.ms.web.controller.sms.SmsLogController.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author zedong.huang
 * @date 2017年12月6日
 * @description TODO
 * @version 1.0
 */
@Controller("smsMerchantController")
@RequestMapping("/smsLog")
public class SmsLogController {
	@Resource
	SmsLogService smsLogService;

	/**
	 * 短信记录查看
	 */
	@RequestMapping("/list")
	public ModelAndView resourceMerchant(Integer page, SmsLogRequest request) {
		try {
			ModelAndView mv = new ModelAndView("/sms/sysLogList");
			page = (null == page || 0 == page) ? 1 : page;
			
			Page<SmsLogModel> smsLogListPage = smsLogService.querySmsLogs(page,request);
			mv.addObject("smsLogListPage", smsLogListPage);
			
			if(request != null) {
				mv.addObject("request", request);
			}
			
			return mv;
		} catch (Exception e) {
			LoggerUtil.error("列表异常", e);
			throw e;
		}
	}
}
