package com.xiaodou.userCenter.web.controller.selftaught;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.request.UpTokenPojo;
import com.xiaodou.userCenter.service.CommonService;
import com.xiaodou.userCenter.web.controller.BaseController;

@Controller("stCommonController")
@RequestMapping("/selftaught/common")
public class CommonController extends BaseController {

	@Resource
	CommonService commonService;

	/**
	 * 获取UpToken接口
	 * 
	 * @param pojo
	 * @param errors
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/upToken")
	@ResponseBody
	public String upToken(UpTokenPojo pojo, Errors errors,
			HttpServletRequest request) throws Exception {
		try {
			ResultInfo response = null;
			pojo = this.getParamsValue(request, UpTokenPojo.class);
			//this.getParamsValueFromHeader(request, pojo);
			if (null == pojo) {
				return JSON.toJSONString((new ResultInfo(ResultType.VALFAIL)));
			}
			//验证的第二遍，之前在mapi已经验证了一遍
			errors = pojo.validate();
			if (errors.hasErrors()) {
				response = new ResultInfo(ResultType.VALFAIL);
				response.setRetdesc(this.getErrMsg(errors));
				return JSON.toJSONString(response);
			} else {
				response = commonService.getUpToken(pojo);
				return JSON.toJSONString(response);
			}
		} catch (Exception e) {
			LoggerUtil.error("Controller层:信息反馈异常,详细信息请查看日志: ", e);
			throw e;
		}
	}
}
