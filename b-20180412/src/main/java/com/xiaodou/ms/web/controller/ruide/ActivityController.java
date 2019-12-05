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
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.model.knowledge.ForumClassify;
import com.xiaodou.ms.model.knowledge.ForumModel;
import com.xiaodou.ms.model.knowledge.ForumStatus;
import com.xiaodou.ms.model.knowledge.ForumType;
import com.xiaodou.ms.model.major.MajorCourseInfo;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.ms.model.ruide.ActivityModel;
import com.xiaodou.ms.model.ruide.TutorMajorModel;
import com.xiaodou.ms.service.ruide.ActivityService;
import com.xiaodou.ms.service.ruide.TutorMajorService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.ruide.ActivityAddRequest;
import com.xiaodou.ms.web.request.ruide.TutorMajorAddRequest;
import com.xiaodou.ms.web.request.ruide.TutorMajorEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;

@Controller("ActivityController")
@RequestMapping("/activity")
public class ActivityController extends BaseController {
	@Resource
	ActivityService activityService;

	@Resource
	TutorMajorService tutorMajorService;

	/**
	 * 活动列表
	 */
	@RequestMapping("/list")
	public ModelAndView activityList(String title) {
		if (StringUtils.isNotBlank(title)) {
			try {
				title = URLDecoder.decode(new String(Base64Utils.decode(title)), "utf8");
			} catch (UnsupportedEncodingException e) {
				title = StringUtils.EMPTY;
			}
		}
		ModelAndView modelAndView = new ModelAndView("ruide/activity/actList");
		List<ActivityModel> activityList = activityService.queryByCond(title);
		for (ActivityModel activity : activityList) {
			TutorMajorModel teacher = tutorMajorService.getTeacherById(activity.getTutorId());
			activity.setTutorMajorModel(teacher);
		}
		modelAndView.addObject("activityList", activityList);
		modelAndView.addObject("title", title);
		return modelAndView;
	}

	/**
	 * 添加活动
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	public ModelAndView activityAdd() {
		ModelAndView modelAndView = new ModelAndView("ruide/activity/actAdd");
		modelAndView.addObject("action", "doAdd");
		List<TutorMajorModel> teacherList = tutorMajorService.getAllTeacherAndMajor((short) 1);
		modelAndView.addObject("teacherList", teacherList);
		return modelAndView;
	}

	@RequestMapping("/doAdd")
	public ModelAndView activityDoAdd(ActivityAddRequest request) {
		try {
			BaseResponse response = null;
			Errors errors = request.validate();
			if (errors.hasErrors()) {
				response = new BaseResponse(ResultType.VALID_FAIL);
				response.setRetDesc(this.getErrMsg(errors));
			} else {
				response = activityService.doAdd(request);
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
		ModelAndView modelAndView = new ModelAndView("ruide/activity/actEdit");
		modelAndView.addObject("activity", activityService.getActivityById(id));
		modelAndView.addObject("action", "doEdit");
		List<TutorMajorModel> teacherList = tutorMajorService.getAllTeacherAndMajor((short) 1);
		modelAndView.addObject("teacherList", teacherList);
		return modelAndView;
	}

	
	@RequestMapping("/doEdit")
	public ModelAndView professionDoEdit(ActivityAddRequest request) {
		try {
			BaseResponse response = null;
			Errors errors = request.validate();
			if (errors.hasErrors()) {
				response = new BaseResponse(ResultType.VALID_FAIL);
				response.setRetDesc(this.getErrMsg(errors));
			} else {
				response = activityService.doEdit(request);
			}
			if (response.getRetCode() == 0) {
				return this.showMessage("编辑成功", "", null, true);
			} else {
				return this.showMessage("编辑失败", response.getRetDesc(),
						"/activity/edit?id=" + request.getId(), true);
			}
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * 刪除活动
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String activityDelete(Long id) {
		try {
		      BaseResponse response = null;
		      Boolean aBoolean = activityService.delete(id);
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
	public void removeContentImage(Long activityId) {
		activityService.deleteContentImage(activityId);
	}

}
