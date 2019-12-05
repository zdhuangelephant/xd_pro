package com.xiaodou.rdonline.web.controller.student;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.rdonline.constants.Constants;
import com.xiaodou.rdonline.domain.student.StudentModel;
import com.xiaodou.rdonline.domain.tutormajor.TutorMajorModel;
import com.xiaodou.rdonline.service.student.StudentService;
import com.xiaodou.rdonline.service.tutormajor.TutorMajorService;
import com.xiaodou.rdonline.util.RdUtil;

/**
 * @author zdh:
 * @date 2017年6月8日
 *
 */
@Controller("studentController")
@RequestMapping("/xygw")
public class StudentController {
	@Resource
	StudentService studentService;

	@Resource
	TutorMajorService tutorMajorService;

	/**
	 * 学员感悟详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "/{stuId}.html")
	public ModelAndView thinkDesc(@PathVariable Long stuId) {
		ModelAndView modelAndView = new ModelAndView("stu/stuDetail");
			if (stuId != null) {
				StudentModel student = studentService.findStudentById(stuId);
				if (null == student) {
					modelAndView.addObject("msg", Constants.MSG);
					return modelAndView;
				}
				TutorMajorModel tutorMajor = tutorMajorService.getTutorMajorPaperById(student.getMajorId());
				student.setTutorMajorModel(tutorMajor);
				if (student.getReadQuantity() == null) {
					student.setReadQuantity(1L);
				} else {
					student.setReadQuantity(student.getReadQuantity() + 1);
				}
				modelAndView.addObject("student", student);
				studentService.updateStudent(student);
				student.setHtmlContent(RdUtil.getHtmlContent(student.getContent()));

				// 学员的感悟列表
				List<StudentModel> stuList = studentService.findStudentList(stuId, 1, Constants.CEBIANLAN);
				for (StudentModel stu : stuList) {
					TutorMajorModel major = tutorMajorService.getTutorMajorPaperById(stu.getMajorId());
					stu.setTutorMajorModel(major);
				}
				modelAndView.addObject("stuList", stuList);
				return modelAndView;
			}
			return null;
	}
}
