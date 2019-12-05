package com.xiaodou.ms.web.controller.ruide;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.model.ruide.MajorCategoryModel;
import com.xiaodou.ms.service.ruide.MajorCategoryService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.ruide.MajorCategoryAddRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

/**
* @author zdh:
* @date 2017年6月13日
*
*/
@Controller("majorCategoryController")
@RequestMapping("/majorCategory")
public class MajorCategoryController extends BaseController {
	@Resource
	MajorCategoryService majorCategoryService;
	
	/**
	 * 专业类别列表
	 */
	@RequestMapping("/list")
	public ModelAndView majorCategoryList(String majorCategory) {
		if (StringUtils.isNotBlank(majorCategory)) {
			try {
				majorCategory = URLDecoder.decode(new String(Base64Utils.decode(majorCategory)), "utf8");
			} catch (UnsupportedEncodingException e) {
				majorCategory = StringUtils.EMPTY;
			}
		}
		ModelAndView modelAndView = new ModelAndView("ruide/majorCategory/majorCategoryList");
		List<MajorCategoryModel> majorCategoryList = majorCategoryService.queryByCond(majorCategory);
		
		modelAndView.addObject("majorCategoryList", majorCategoryList);
		modelAndView.addObject("majorCategory", majorCategory);
		return modelAndView;
	}
	
	
	/**
	 * 添加专业类别
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	public ModelAndView activityAdd() {
		ModelAndView modelAndView = new ModelAndView("ruide/majorCategory/majorCategoryAdd");
		return modelAndView;
	}

	@RequestMapping("/doAdd")
	public ModelAndView activityDoAdd(MajorCategoryAddRequest request) {
		try {
			BaseResponse response = null;
			Errors errors = request.validate();
			if (errors.hasErrors()) {
				response = new BaseResponse(ResultType.VALID_FAIL);
				response.setRetDesc(this.getErrMsg(errors));
			} else {
				response = majorCategoryService.doAdd(request);
			}
			if (response.getRetCode() == 0) {
				return this.showMessage("添加成功", "", null, true);
			} else {
				return this.showMessage("添加失败", response.getRetDesc(), null, true);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 修改活动
	 */
	@RequestMapping("/edit")
	public ModelAndView activityEdit(Long id) {
		ModelAndView modelAndView = new ModelAndView("ruide/majorCategory/majorCategoryEdit");
		modelAndView.addObject("majorCategory", majorCategoryService.findMajorCategoryById(id));
		return modelAndView;
	}

	@RequestMapping("/doEdit")
	public ModelAndView activityDoEdit(MajorCategoryAddRequest request) {
		try {
			BaseResponse response = null;
			Errors errors = request.validate();
			if (errors.hasErrors()) {
				response = new BaseResponse(ResultType.VALID_FAIL);
				response.setRetDesc(this.getErrMsg(errors));
			} else {
				response = majorCategoryService.doEdit(request);
			}
			if (response.getRetCode() == 0) {
				return this.showMessage("编辑成功", "", null, true);
			} else {  
				return this.showMessage("编辑失败", response.getRetDesc(), "/majorCategory/edit?id=" + request.getId(),
						true);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 刪除活动
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String activityDelete(Long id) {
		//return FastJsonUtil.toJson(majorCategoryService.delete(id));
		try {
			BaseResponse response = null;
			Boolean aBoolean = majorCategoryService.delete(id);
			if (aBoolean) {
				response = new BaseResponse(ResultType.SUCCESS);
			} else {
				response = new BaseResponse(ResultType.SYS_FAIL);
			}
			return JSON.toJSONString(response);
		} catch (Exception e) {
			throw e;
		}
	}
}
