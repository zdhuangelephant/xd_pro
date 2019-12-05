package com.xiaodou.ms.web.controller.course;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xiaodou.ms.model.course.CourseSubjectModel;
import com.xiaodou.ms.service.course.CourseCategoryService;
import com.xiaodou.ms.service.course.CourseSubjectService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.course.ResourceCourseCreateRequest;
import com.xiaodou.ms.web.request.course.ResourceCourseEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/4/19.
 */

@Controller("courseController")
@RequestMapping("/course")
public class CourseController extends BaseController {

  /**
   * 课程
   */
  @Resource
  CourseSubjectService courseSubjectService;

  @Resource
  CourseCategoryService courseCategoryService;

  /**
   * 课程列表
   * @return
   */
  @RequestMapping("/list")
  public ModelAndView courseList(Long catId){
    ModelAndView modelAndView = new ModelAndView("/course/subjectList");
    if (catId==null){
      catId = 0L;
    }
    Page<CourseSubjectModel> courseSubjectList =
      courseSubjectService.cascadeQueryCourseByCatId(catId);
    String selectTree = courseCategoryService.categorySelectTree(0L, catId.toString());
    modelAndView.addObject("subjectList",courseSubjectList.getResult());
    modelAndView.addObject("selectTree",selectTree);
    modelAndView.addObject("catId",catId);
    return modelAndView;
  }

  /**
   * 增加课程界面
   * @return
   */
  @RequestMapping("add")
  public ModelAndView resourceCourseAdd(){
    ModelAndView modelAndView = new ModelAndView("/course/subjectAdd");
    String selectTree = courseCategoryService.categorySelectTree(0L, null);
    modelAndView.addObject("selectTree",selectTree);
    return modelAndView;
  }

  /**
   * 资源课程添加
   * 
   * @return
   */
  @RequestMapping("/doAdd")
  public ModelAndView resourceCourseDoAdd(ResourceCourseCreateRequest request,Errors errors) throws Exception{
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()){
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        response = courseSubjectService.addSubject(request);
      }
      if (response.getRetCode()==0){
        return this.showMessage("添加成功","",null,true);
      } else {
        return this.showMessage("添加失败",response.getRetDesc(),null,true);
      }
    } catch (Exception e){
      throw e;
    }
  }

  /**
   * 资源课程删除
   * 
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public String resourceCourseDel(Integer id){
    try {
      BaseResponse response = null;
      Boolean aBoolean = courseSubjectService.deleteSubject(id);
      if (aBoolean){
        response = new BaseResponse(ResultType.SUCCESS);
      } else {
        response = new BaseResponse(ResultType.SYS_FAIL);
      }
      return JSON.toJSONString(response);
    } catch (Exception e){
      throw e;
    }
  }

  /**
   * 编辑
   * @param id
   * @return
   */
  @RequestMapping("/edit")
  public ModelAndView resourceCourseEdit(Long id){
    ModelAndView modelAndView = new ModelAndView("/course/subjectEdit");
    CourseSubjectModel courseSubject =  courseSubjectService.findSubjectById(id);
    modelAndView.addObject("courseSubject",courseSubject);
    String selectTree =
      courseCategoryService.categorySelectTree(0L, courseSubject.getCategoryId().toString());
    modelAndView.addObject("selectTree",selectTree);
    return modelAndView;
  }

  /**
   * 资源课程编辑
   * 
   * @return
   */
  @RequestMapping("/doEdit")
  public ModelAndView resourceCourseDoEdit(ResourceCourseEditRequest request,Errors errors){
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()){
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        courseSubjectService.editSubject(request);
        response = new BaseResponse(ResultType.SUCCESS);
      }
      if (response.getRetCode()==0){
        return this.showMessage("编辑成功","","/course/edit?id="+request.getId(),true);
      } else {
        return this.showMessage("编辑失败",response.getRetDesc(),"/course/edit?id="+request.getId(),true);
      }
    } catch (Exception e){
      throw e;
    }
  }

}
