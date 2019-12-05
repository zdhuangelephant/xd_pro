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
import com.xiaodou.ms.model.ruide.StudentModel;
import com.xiaodou.ms.model.ruide.TutorMajorModel;
import com.xiaodou.ms.service.ruide.StudentService;
import com.xiaodou.ms.service.ruide.TutorMajorService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.ruide.ActivityAddRequest;
import com.xiaodou.ms.web.request.ruide.StudentAddRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

@Controller("StudentController")
@RequestMapping("/student")
public class StudentController extends BaseController{
	@Resource
	StudentService studentService;
	
	@Resource
	TutorMajorService tutorMajorService;
	
	/**
	 * 学员列表
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView studentList(String authorName) {
		if (StringUtils.isNotBlank(authorName)) {
			try {
				authorName = URLDecoder.decode(new String(Base64Utils.decode(authorName)), "utf8");
			} catch (UnsupportedEncodingException e) {
				authorName = StringUtils.EMPTY;
			}
		}
		ModelAndView modelAndView = new ModelAndView("ruide/student/stuList");
		List<StudentModel> studentList = studentService.queryByCond(authorName);
		for (StudentModel student : studentList) {
			TutorMajorModel profession = tutorMajorService.getMajorById(student.getMajorId());
			student.setTutorMajorModel(profession);
		}
		modelAndView.addObject("studentList", studentList);
		modelAndView.addObject("authorName", authorName);
		return modelAndView;
	}
	
	
	/**
	 * 添加活动
	 * @return
	 */
	@RequestMapping("/add")
	public ModelAndView studentAdd() {
		ModelAndView modelAndView = new ModelAndView("ruide/student/stuAdd");
		modelAndView.addObject("action", "doAdd");
		List<TutorMajorModel> professionList = tutorMajorService.getAllTeacherAndMajor((short) 2);
		modelAndView.addObject("professionList", professionList);
		return modelAndView;
	}

	@RequestMapping("/doAdd")
	@ResponseBody
	public String studentDoAdd(StudentAddRequest request,Errors errors) {
		
		try {
		      BaseResponse response = null;
		      errors = request.validate();
		      if (errors.hasErrors()) {
		        response = new BaseResponse(ResultType.VALID_FAIL);
		        response.setRetDesc(this.getErrMsg(errors));
		      } else {
		        response = studentService.doAdd(request);
		      }
		      return FastJsonUtil.toJson(response);
		    } catch (Exception e) {
		      LoggerUtil.error("活动记录录入失败", e);
		      throw e;
		    }
	}

	/**
	 * 修改活动
	 */
	@RequestMapping("/edit")
	public ModelAndView activityEdit(Long id) {
		ModelAndView modelAndView = new ModelAndView("ruide/student/stuEdit");
		modelAndView.addObject("student", studentService.getStudentById(id));
		modelAndView.addObject("action", "doEdit");
		List<TutorMajorModel> professionList = tutorMajorService.getAllTeacherAndMajor((short) 2);
		modelAndView.addObject("professionList", professionList);
		return modelAndView;
	}

	@RequestMapping("/doEdit")
	public ModelAndView studentDoEdit(StudentAddRequest request) {
		try {
			BaseResponse response = null;
			Errors errors = request.validate();
			if (errors.hasErrors()) {
				response = new BaseResponse(ResultType.VALID_FAIL);
				response.setRetDesc(this.getErrMsg(errors));
			} else {
				response = studentService.doEdit(request);
			}
			if (response.getRetCode() == 0) {
				return this.showMessage("编辑成功", "", null, true);
			} else {
				return this.showMessage("编辑失败", response.getRetDesc(), "/student/edit?id=" + request.getId(),
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
	public String studentDelete(Long id) {
		try {
		      BaseResponse response = null;
		      Boolean aBoolean = studentService.delete(id);
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
	public void removeContentImage(Long studentId) {
		studentService.deleteContentImage(studentId);
	}
}
