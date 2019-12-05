package com.xiaodou.rdonline.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.rdonline.domain.majorCategory.MajorCategoryModel;
import com.xiaodou.rdonline.domain.student.StudentModel;
import com.xiaodou.rdonline.domain.tutormajor.TutorMajorModel;
import com.xiaodou.rdonline.service.majorcategory.MajorCategoryService;
import com.xiaodou.rdonline.service.student.StudentService;
import com.xiaodou.rdonline.service.tutormajor.TutorMajorService;

/**
 * @author zdh:
 * @date 2017年6月8日
 *
 */
@Controller("indexController")
@RequestMapping("/")
public class IndexController {

	@Resource
	private StudentService studentService;

	@Resource
	TutorMajorService tutorMajorService;

	@Resource
	MajorCategoryService majorCategoryService;

	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView("/index");
		List<StudentModel> studentList = studentService.findStudentList(0L, 1, 8);
		for (StudentModel student : studentList) {
			TutorMajorModel major = tutorMajorService.getTutorMajorPaperById(student.getMajorId());
			student.setTutorMajorModel(major);
		}
		model.addObject("studentList", studentList);

		// 首页4个导师
		List<TutorMajorModel> tutorList = tutorMajorService.findTutorMajorList((short) 1, 1, 4);
		model.addObject("tutorList", tutorList);

		// 专业类别
		List<MajorCategoryModel> majorCategoryList = majorCategoryService.findMajorCategoryList(1, 3);
		model.addObject("majorCategoryList", majorCategoryList);

		return model;
	}
}
