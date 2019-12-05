package com.xiaodou.rdonline.web.controller.tutormajor;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.StringUtils;
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
@Controller("tutorMajorController")
@RequestMapping("/")
public class TutorMajorController {
	@Resource
	TutorMajorService tutorMajorService;

	@Resource
	StudentService studentService;

	/**
	 * 导师列表
	 */
	@RequestMapping("/zyds")
	public ModelAndView teacherList(Integer pageNo) {
		ModelAndView modelAndView = new ModelAndView("tutor/tutor");
		pageNo = null != pageNo && pageNo > 0 ? pageNo : 1;
		// 导师列表页 默认显示10个
		List<TutorMajorModel> tutorList = tutorMajorService.findTutorMajorList((short) 1, 1,
				Constants.LOAD_MORN * pageNo);
		modelAndView.addObject("tutorList", tutorList);
		List<StudentModel> studentList = studentService.findStudentList(0L, 1, Constants.CEBIANLAN);
		for (StudentModel stu : studentList) {
			TutorMajorModel major = tutorMajorService.getTutorMajorPaperById(stu.getMajorId());
			stu.setTutorMajorModel(major);
		}
		modelAndView.addObject("studentList", studentList);
		modelAndView.addObject("pageNo", pageNo);
		return modelAndView;
	}

	/**
	 * 导师详情
	 * @param majortutorId
	 * @return
	 */
	@RequestMapping("/zyds/{majortutorId}.html")
	public ModelAndView teacherDetail(@PathVariable Long majortutorId) {
		ModelAndView modelAndView = new ModelAndView("tutor/tutorDetail");

		TutorMajorModel tutor = tutorMajorService.getTutorMajorPaperById(majortutorId);
		if (null == tutor) {
			modelAndView.addObject("msg", Constants.MSG);
			return modelAndView;
		}

		if (tutor.getReadQuantity() == null) {
			tutor.setReadQuantity(1L);
		} else {
			tutor.setReadQuantity(tutor.getReadQuantity() + 1L);
		}
		tutorMajorService.updateThumbsReadQuality(tutor);
		tutor.setHtmlContent(RdUtil.getHtmlContent(tutor.getContent()));
		modelAndView.addObject("tutor", tutor);

		// 学员感悟
		// 所有学员的感悟列表
		List<StudentModel> stuList = studentService.findStudentList(0L, 1, 5);
		for (StudentModel stu : stuList) {
			TutorMajorModel major = tutorMajorService.getTutorMajorPaperById(stu.getMajorId());
			stu.setTutorMajorModel(major);
		}
		modelAndView.addObject("stuList", stuList);
		return modelAndView;
	}

	/**
	 * 专业列表
	 */
	@RequestMapping("/zyjs")
	public ModelAndView majorList(Integer pageNo) {
		pageNo = null != pageNo && pageNo > 0 ? pageNo : 1;
		ModelAndView modelAndView = new ModelAndView("major/major");
		// 专业列表
		List<TutorMajorModel> majorList = tutorMajorService.findTutorMajorList((short) 2, 1,
				Constants.LOAD_MORN * pageNo);
		modelAndView.addObject("majorList", majorList);

		// 文章列表
		List<TutorMajorModel> paperList = tutorMajorService.findPaperList(1, Constants.CEBIANLAN);
		modelAndView.addObject("paperList", paperList);
		modelAndView.addObject("pageNo", pageNo);
		return modelAndView;
	}

	/**
	 * 专业详情
	 */
	@RequestMapping("/zyjs/{majortutorId}.html")
	public ModelAndView detailMajor(@PathVariable String majortutorId) {
		ModelAndView modelAndView = new ModelAndView("/major/majorDetail");
		TutorMajorModel majorDetail = null;
		if (StringUtils.isNotBlank(majortutorId)) {
			majorDetail = tutorMajorService.getTutorMajorPaperById(Long.parseLong(majortutorId));
			if (null == majorDetail) {
				modelAndView.addObject("msg", Constants.MSG);
				return modelAndView;
			}
			
			if (majorDetail.getReadQuantity() == null) {
				majorDetail.setReadQuantity(1L);
			} else {
				majorDetail.setReadQuantity(majorDetail.getReadQuantity() + 1L);
			}
			tutorMajorService.updateThumbsReadQuality(majorDetail);
			majorDetail.setHtmlContent(RdUtil.getHtmlContent(majorDetail.getContent()));
			modelAndView.addObject("majorDetail", majorDetail);

			// 文章列表
			List<TutorMajorModel> paperList = tutorMajorService.findPaperList(1, 5);
			modelAndView.addObject("paperList", paperList);
		}
		return modelAndView;
	}

	/**
	 * 论文详情
	 */
	@RequestMapping("/paper/{paperId}.html")
	public ModelAndView detailPaper(@PathVariable String paperId) {
		ModelAndView modelAndView = new ModelAndView("paper/paperDetail");
		TutorMajorModel paperDetail = null;
		if (StringUtils.isNotBlank(paperId)) {
			paperDetail = tutorMajorService.getTutorMajorPaperById(Long.parseLong(paperId));
			if (null == paperDetail) {
				modelAndView.addObject("msg", Constants.MSG);
				return modelAndView;
			}
			modelAndView.addObject("paperDetail", paperDetail);

			if (paperDetail.getReadQuantity() == null) {
				paperDetail.setReadQuantity(1L);
			} else {
				paperDetail.setReadQuantity(paperDetail.getReadQuantity() + 1L);
			}

			tutorMajorService.updateThumbsReadQuality(paperDetail);
			paperDetail.setHtmlContent(RdUtil.getHtmlContent(paperDetail.getContent()));

			// stuList
			List<StudentModel> stuList = studentService.findStudentList(0L, 1, 5);
			for (StudentModel stu : stuList) {
				TutorMajorModel major = tutorMajorService.getTutorMajorPaperById(stu.getMajorId());
				stu.setTutorMajorModel(major);
			}
			modelAndView.addObject("stuList", stuList);
		}
		return modelAndView;
	}

}
