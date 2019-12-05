package com.xiaodou.rdonline.web.controller.activity;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.rdonline.constants.Constants;
import com.xiaodou.rdonline.domain.activity.ActivityModel;
import com.xiaodou.rdonline.domain.tutormajor.TutorMajorModel;
import com.xiaodou.rdonline.service.activity.ActivityService;
import com.xiaodou.rdonline.service.tutormajor.TutorMajorService;
import com.xiaodou.rdonline.util.RdUtil;

/**
 * @author zdh:
 * @date 2017年6月8日
 *
 */
@Controller("activityController")
@RequestMapping("/xxhd")
public class ActivityController {
	@Resource
	ActivityService activityService;

	@Resource
	TutorMajorService tutorMajorService;

	/**
	 * 学员列表
	 * 
	 * @return
	 */
	@RequestMapping("")
	public ModelAndView activityList(Integer pageNo) {
		pageNo = null != pageNo && pageNo > 0 ? pageNo : 1;
		ModelAndView modelAndView = new ModelAndView("activity/activity");
		List<ActivityModel> activityList = activityService.findActivityList(null, 1, Constants.LOAD_MORN * pageNo);
		for (ActivityModel activity : activityList) {
			TutorMajorModel major = tutorMajorService.getTutorMajorPaperById(activity.getTutorId());
			activity.setTutorMajorModel(major);
		}
		modelAndView.addObject("activityList", activityList);

		// 文章列表
		List<TutorMajorModel> paperList = tutorMajorService.findPaperList(1, Constants.CEBIANLAN);
		modelAndView.addObject("paperList", paperList);
		modelAndView.addObject("pageNo", pageNo);
		return modelAndView;
	}

	/**
	 * 线下活动详情
	 * @param activityId
	 * @return
	 */
	@RequestMapping(value = "/{activityId}.html")
	public ModelAndView activityDetail(@PathVariable Long  activityId) {
		ModelAndView modelAndView = new ModelAndView("activity/activityDetail");
		// 文章列表
		List<TutorMajorModel> paperList = tutorMajorService.findPaperList(1, Constants.CEBIANLAN);
		modelAndView.addObject("paperList", paperList);
		if (activityId != null) {
			ActivityModel activity = activityService.findActivityById(activityId);
			if (null == activity) {
				modelAndView.addObject("msg", Constants.MSG);
				return modelAndView;
			}
			TutorMajorModel tutor = tutorMajorService.getTutorMajorPaperById(activity.getTutorId());
			activity.setTutorMajorModel(tutor);
			if (activity.getReadQuantity() == null) {
				activity.setReadQuantity(1L);
			} else {
				activity.setReadQuantity(activity.getReadQuantity() + 1);
			}
			activityService.updateActivity(activity);
			activity.setHtmlContent(RdUtil.getHtmlContent(activity.getContent()));
			modelAndView.addObject("activity", activity);
			return modelAndView;
		}
		return null;
	}
}
