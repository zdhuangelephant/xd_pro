package com.xiaodou.ms.web.controller.config;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.xiaodou.common.info.model.CommonInfo;
import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.ms.service.config.ConfInfoService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.config.ConfInfoRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

@Controller("confInfoController")
@RequestMapping("/confInfo")
public class ConfInfoController extends BaseController {
	@Resource
	ConfInfoService confInfoService;

//	@Resource
//	ProductModuleService productModuleService;

	/**
	 * config列表
	 */
	@RequestMapping("/list")
	public ModelAndView list(String infoType) {
		if (StringUtils.isNotBlank(infoType)) {
			try {
				infoType = URLDecoder.decode(new String(Base64Utils.decode(infoType)), "utf8");
			} catch (UnsupportedEncodingException e) {
				infoType = StringUtils.EMPTY;
			}
		}
		try {
			ModelAndView modelAndView = new ModelAndView("/config/confInfoList");
			HashMap<String, Object> inputs = Maps.newHashMap();
			inputs.put("infoTypeLike", infoType);
			List<CommonInfo> confInfolist = confInfoService.listConfInfo(inputs, null);
//			for (CommonInfo confInfo : confInfolist) {
//				confInfo.setModule(
//						productModuleService.findModuleById(Long.parseLong(confInfo.getModule())).getName());
//			}

			modelAndView.addObject("confInfolist", confInfolist);
			return modelAndView;
		} catch (Exception e) {
			LoggerUtil.error("列表异常", e);
			throw e;
		}
	}


	// /**
	// * 系统任务添加
	// *
	// * @return
	// */
	// @RequestMapping("/doAdd")
	// public ModelAndView doAdd(ConfInfoRequest request, Errors errors) throws
	// Exception {
	// try {
	// BaseResponse response = null;
	// errors = request.validate();
	// if (errors.hasErrors()) {
	// response = new BaseResponse(ResultType.VALID_FAIL);
	// response.setRetDesc(this.getErrMsg(errors));
	// } else {
	// response = confInfoService.addConfInfo(request);
	// }
	// if (response.getRetCode() == 0) {
	// return this.showMessage("添加成功", "", null, true);
	// } else {
	// return this.showMessage("添加失败", response.getRetDesc(), null, true);
	// }
	// } catch (Exception e) {
	// LoggerUtil.error("目录创建异常", e);
	// throw e;
	// }
	// }

	/**
	 * 系统任务修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/edit")
	public ModelAndView sysMissionEditPage(Integer id) {
		CommonInfo confInfo = confInfoService.findConfInfoById(id);
		ModelAndView mv = new ModelAndView("/config/confInfoEdit");
		mv.addObject("confInfo", confInfo);
		return mv;
	}

	/**
	 * 系统任务修改
	 * 
	 * @return
	 */
	@RequestMapping("/doEdit")
	@ResponseBody
	public ModelAndView sysMissionEdit(ConfInfoRequest request, Errors errors) throws Exception {
		try {
			BaseResponse response = null;
			errors = request.validate();
			if (errors.hasErrors()) {
				response = new BaseResponse(ResultType.VALID_FAIL);
				response.setRetDesc(this.getErrMsg(errors));
			} else {
				response = confInfoService.edit(request);
			}
			if (response.getRetCode() == 0) {
				return this.showMessage("编辑成功", "", null, true);
			} else {
				return this.showMessage("编辑失败", response.getRetDesc(), null, true);
			}
		} catch (Exception e) {
			LoggerUtil.error("config编辑异常", e);
			throw e;
		}
	}

	/**
	 * 标准任务删除
	 * 
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String standardDelete(Integer id) {
		try {
			BaseResponse response = null;
			Boolean aBoolean = confInfoService.delete(id);
			if (aBoolean) {
				response = new BaseResponse(ResultType.SUCCESS);
			} else {
				response = new BaseResponse(ResultType.SYS_FAIL);
			}
			return JSON.toJSONString(response);
		} catch (Exception e) {
			LoggerUtil.error("目录删除异常", e);
			throw e;
		}
	}
}
