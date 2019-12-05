package com.xiaodou.rdonline.web.controller.common;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiaodou.rdonline.domain.activity.ActivityModel;
import com.xiaodou.rdonline.domain.student.StudentModel;
import com.xiaodou.rdonline.domain.tutormajor.TutorMajorModel;
import com.xiaodou.rdonline.service.activity.ActivityService;
import com.xiaodou.rdonline.service.student.StudentService;
import com.xiaodou.rdonline.service.tutormajor.TutorMajorService;
import com.xiaodou.rdonline.web.response.BaseResponse;
import com.xiaodou.rdonline.web.response.ResultType;

/**
 * @author zdh:
 * @date 2017年6月10日
 *
 */
@Controller("thumbsController")
@RequestMapping("/thumbs")
public class ThumbsController {

	@Resource
	TutorMajorService tutorMajorService;

	@Resource
	ActivityService activityService;

	@Resource
	StudentService studentService;

	@RequestMapping("/upThumbs")
	@ResponseBody
	public String doThumbs(Long id, Short typeId, Integer oper) {
		// typeId 1:tutor, 2:activity, 3:student
		switch (typeId) {
		case (short) 1:
			TutorMajorModel tutorMajorModel = tutorMajorService.getTutorMajorPaperById(id);
			if (tutorMajorModel.getThumbNums() <= 100) {
				tutorMajorModel.setThumbNums(100L);
			}
			if (oper != 1) {
				tutorMajorModel.setThumbNums(tutorMajorModel.getThumbNums() - 1L);
			} else {
				tutorMajorModel.setThumbNums(tutorMajorModel.getThumbNums() + 1L);
			}
			Boolean res = tutorMajorService.updateTutorMajorModel(tutorMajorModel);
			if (res) {
				return JSON.toJSONString(new BaseResponse(ResultType.SUCCESS));
			} else {
				return JSON.toJSONString(new BaseResponse(ResultType.SYS_FAIL));
			}
		case (short) 2:
			ActivityModel ativityModel = activityService.findActivityById(id);
			if (ativityModel.getThumbNums() <= 100) {
				ativityModel.setThumbNums(100L);
			}
			if (oper != 1) {
				ativityModel.setThumbNums(ativityModel.getThumbNums() - 1L);
			} else {
				ativityModel.setThumbNums(ativityModel.getThumbNums() + 1L);
			}
			Boolean value = activityService.updateActivity(ativityModel);
			if (value) {
				return JSON.toJSONString(new BaseResponse(ResultType.SUCCESS));
			} else {
				return JSON.toJSONString(new BaseResponse(ResultType.SYS_FAIL));
			}
		case (short) 3:
			StudentModel studentModel = studentService.findStudentById(id);
			if (studentModel.getThumbNums() <= 100L) {
				studentModel.setThumbNums(100L);
			}
			if (oper != 1) {
				studentModel.setThumbNums(studentModel.getThumbNums() - 1L);
			} else {
				studentModel.setThumbNums(studentModel.getThumbNums() + 1L);
			}
			Boolean isSuccess = studentService.updateStudent(studentModel);
			if (isSuccess) {
				return JSON.toJSONString(new BaseResponse(ResultType.SUCCESS));
			} else {
				return JSON.toJSONString(new BaseResponse(ResultType.SYS_FAIL));
			}
		}
		return null;
	}
}
