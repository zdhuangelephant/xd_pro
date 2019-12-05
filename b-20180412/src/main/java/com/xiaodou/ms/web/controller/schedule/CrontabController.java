package com.xiaodou.ms.web.controller.schedule;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.service.schedule.CrontabService;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

/**MS系统定时调度控制器
 * @name CrontabController 
 * CopyRright (c) 2018 by <a href="mailto:huangzedong@corp.51xiaodou.com">zdhuang</a>
 *
 * @author <a href="mailto:huangzedong@corp.51xiaodou.com">zdhuang</a>
 * @date 2018年1月9日
 * @description TODO
 * @version 1.0
 */
@Controller("crontabController")
@RequestMapping("/crontab")
public class CrontabController {

	@Resource
	CrontabService crontabService;

	@RequestMapping("/course_push")
	@ResponseBody
	public BaseResponse pushForum(String tag) {
		BaseResponse  response = new BaseResponse();
		if (StringUtils.isBlank(tag)) {
			  response.setRetCode(ResultType.SYS_FAIL.getRetCode());
			  response.setRetDesc("tag不允许为空");
		  }	
		response = crontabService.pushForum(tag);
		return response;
	}
}
