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
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.model.ruide.MajorCategoryModel;
import com.xiaodou.ms.model.ruide.TutorMajorModel;
import com.xiaodou.ms.service.ruide.MajorCategoryService;
import com.xiaodou.ms.service.ruide.TutorMajorService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.ruide.TutorMajorAddRequest;
import com.xiaodou.ms.web.request.ruide.TutorMajorEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

@Controller("tutorMajorController")
@RequestMapping("/tutorMajor")
public class TutorMajorController extends BaseController {
	@Resource
	TutorMajorService tutorMajorService;
	@Resource
	MajorCategoryService majorCategoryService;

	/**
	 * 导师列表
	 */
	@RequestMapping("/teacher/list")
	public ModelAndView teacherList(String authorName) {
		if (StringUtils.isNotBlank(authorName)) {
			try {
				authorName = URLDecoder.decode(new String(Base64Utils.decode(authorName)), "utf8");
			} catch (UnsupportedEncodingException e) {
				authorName = StringUtils.EMPTY;
			}
		}
		ModelAndView modelAndView = new ModelAndView("ruide/tutorMajor/teacherList");
		List<TutorMajorModel> teacherList = tutorMajorService.search(authorName, (short) 1);
		modelAndView.addObject("authorName", authorName);
		modelAndView.addObject("teacherList", teacherList);
		return modelAndView;
	}

	/**
	 * 添加导师页面
	 * @return
	 */
	@RequestMapping("/teacher/add")
	public ModelAndView teacherAdd() {
		ModelAndView modelAndView = new ModelAndView("ruide/tutorMajor/teacherAdd");
		return modelAndView;
	}

	@RequestMapping("/teacher/doAdd")
	public ModelAndView teacherDoAdd(TutorMajorAddRequest request) {
		try {
			BaseResponse response = null;
			Errors errors = request.validate();
			if (errors.hasErrors()) {
				response = new BaseResponse(ResultType.VALID_FAIL);
				response.setRetDesc(this.getErrMsg(errors));
			} else {
				response = tutorMajorService.doAdd(request, (short) 1);
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
	 * 修改导师页面
	 */
	@RequestMapping("/teacher/edit")
	public ModelAndView teacherEdit(Long id) {
		ModelAndView modelAndView = new ModelAndView("ruide/tutorMajor/teacherEdit");
		modelAndView.addObject("teacher", tutorMajorService.getTeacherById(id));
		return modelAndView;
	}

	@RequestMapping("/teacher/doEdit")
	public ModelAndView teacherDoEdit(TutorMajorEditRequest request) {
		try {
			BaseResponse response = null;
			Errors errors = request.validate();
			if (errors.hasErrors()) {
				response = new BaseResponse(ResultType.VALID_FAIL);
				response.setRetDesc(this.getErrMsg(errors));
			} else {
				response = tutorMajorService.doEdit(request);
			}
			if (response.getRetCode() == 0) {
				return this.showMessage("编辑成功", "", null, true);
			} else {
				return this.showMessage("编辑失败", response.getRetDesc(), "/tutorMajor/teacher/edit?id=" + request.getId(),
						true);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 刪除指定导师
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/teacher/delete")
	@ResponseBody
	public String teacherDelete(Long id) {
		try {
			BaseResponse response = null;
			Boolean aBoolean = tutorMajorService.delete(id);
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

	//////////////////////////////////////////////////////////////////

	/**
	 * 专业列表
	 */
	@RequestMapping("/profession/list")
	public ModelAndView professionList(String authorName) {
		if (StringUtils.isNotBlank(authorName)) {
			try {
				authorName = URLDecoder.decode(new String(Base64Utils.decode(authorName)), "utf8");
			} catch (UnsupportedEncodingException e) {
				authorName = StringUtils.EMPTY;
			}
		}
		ModelAndView modelAndView = new ModelAndView("ruide/tutorMajor/professionList");
		List<TutorMajorModel> professionList = tutorMajorService.search(authorName, (short) 2);
		for (TutorMajorModel tutorMajorModel : professionList) {
			MajorCategoryModel majorCat = majorCategoryService
					.findMajorCategoryById(tutorMajorModel.getMajorCategoryId());
			tutorMajorModel.setMajorCategoryModel(majorCat);
		}
		modelAndView.addObject("authorName", authorName);
		modelAndView.addObject("professionList", professionList);
		return modelAndView;
	}

	/**
	 * 添加专业
	 * 
	 * @return
	 */
	@RequestMapping("/profession/add")
	public ModelAndView professionAdd() {
		ModelAndView modelAndView = new ModelAndView("ruide/tutorMajor/professionAdd");
		List<MajorCategoryModel> majorCategoryList = majorCategoryService.queryByCond(null);

		modelAndView.addObject("majorCategoryList", majorCategoryList);
		return modelAndView;
	}

	@RequestMapping("/profession/doAdd")
	public ModelAndView professionDoAdd(TutorMajorAddRequest request) {
		try {
			BaseResponse response = null;
			Errors errors = request.validate();
			if (errors.hasErrors()) {
				response = new BaseResponse(ResultType.VALID_FAIL);
				response.setRetDesc(this.getErrMsg(errors));
			} else {
				response = tutorMajorService.doAdd(request, (short) 2);
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
	 * 修改专业
	 */
	@RequestMapping("/profession/edit")
	public ModelAndView professionEdit(Long id) {
		ModelAndView modelAndView = new ModelAndView("ruide/tutorMajor/professionEdit");
		modelAndView.addObject("profession", tutorMajorService.getMajorById(id));
		List<MajorCategoryModel> majorCategoryList = majorCategoryService.queryByCond(null);
		modelAndView.addObject("majorCategoryList", majorCategoryList);
		return modelAndView;
	}

	@RequestMapping("/profession/doEdit")
	public ModelAndView professionDoEdit(TutorMajorEditRequest request) {
		try {
			BaseResponse response = null;
			Errors errors = request.validate();
			if (errors.hasErrors()) {
				response = new BaseResponse(ResultType.VALID_FAIL);
				response.setRetDesc(this.getErrMsg(errors));
			} else {
				response = tutorMajorService.doEdit(request);
			}
			if (response.getRetCode() == 0) {
				return this.showMessage("编辑成功", "", null, true);
			} else {
				return this.showMessage("编辑失败", response.getRetDesc(),
						"/tutorMajor/profession/edit?id=" + request.getId(), true);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 刪除指定专业
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/profession/delete")
	@ResponseBody
	public String professionDelete(Long id) {
		try {
			BaseResponse response = null;
			Boolean aBoolean = tutorMajorService.delete(id);
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

	/////////////////////////////////////////////////////////////////////////////

	/**
	 * 论文列表
	 */
	@RequestMapping("/paper/list")
	public ModelAndView paperList(String titleName) {
		if (StringUtils.isNotBlank(titleName)) {
			try {
				titleName = URLDecoder.decode(new String(Base64Utils.decode(titleName)), "utf8");
			} catch (UnsupportedEncodingException e) {
				titleName = StringUtils.EMPTY;
			}
		}
		ModelAndView modelAndView = new ModelAndView("ruide/tutorMajor/paperList");
		List<TutorMajorModel> paperList = tutorMajorService.search(titleName, (short) 3);
		modelAndView.addObject("titleName", titleName);
		modelAndView.addObject("paperList", paperList);
		return modelAndView;
	}

	/**
	 * 添加论文
	 * 
	 * @return
	 */
	@RequestMapping("/paper/add")
	public ModelAndView paperAdd() {
		ModelAndView modelAndView = new ModelAndView("ruide/tutorMajor/paperAdd");
		return modelAndView;
	}

	@RequestMapping("/paper/doAdd")
	public ModelAndView paperDoAdd(TutorMajorAddRequest request) {
		try {
			BaseResponse response = null;
			Errors errors = request.validate();
			if (errors.hasErrors()) {
				response = new BaseResponse(ResultType.VALID_FAIL);
				response.setRetDesc(this.getErrMsg(errors));
			} else {
				response = tutorMajorService.doAdd(request, (short) 3);
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
	 * 修改论文
	 */
	@RequestMapping("/paper/edit")
	public ModelAndView paperEdit(Long id) {
		ModelAndView modelAndView = new ModelAndView("ruide/tutorMajor/paperEdit");
		modelAndView.addObject("paper", tutorMajorService.getMajorById(id));
		return modelAndView;
	}

	@RequestMapping("/paper/doEdit")
	public ModelAndView paperDoEdit(TutorMajorEditRequest request) {
		try {
			BaseResponse response = null;
			Errors errors = request.validate();
			if (errors.hasErrors()) {
				response = new BaseResponse(ResultType.VALID_FAIL);
				response.setRetDesc(this.getErrMsg(errors));
			} else {
				response = tutorMajorService.doEdit(request);
			}
			if (response.getRetCode() == 0) {
				return this.showMessage("编辑成功", "", null, true);
			} else {
				return this.showMessage("编辑失败", response.getRetDesc(), "/tutorMajor/paper/edit?id=" + request.getId(),
						true);
			}
		} catch (Exception e) {
			throw e;
		}
		
	}

	/**
	 * 刪除指定论文
	 */
	@RequestMapping("/paper/delete")
	@ResponseBody
	public String paperDelete(Long id) {
		try {
			BaseResponse response = null;
			Boolean aBoolean = tutorMajorService.delete(id);
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
	
	/**
	 * 刪除正文图片
	 * @param id
	 * @return
	 */
	@RequestMapping("/removeContentImage")
	public void removeContentImage(Long tutorMajorPaperId) {
		tutorMajorService.deleteContentImage(tutorMajorPaperId);
	}
}
