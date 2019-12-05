package com.xiaodou.ms.web.controller.exam;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.ms.model.course.CourseSubjectModel;
import com.xiaodou.ms.model.exam.QuestionBankQuestionResourceModel;
import com.xiaodou.ms.service.course.CourseSubjectService;
import com.xiaodou.ms.service.exam.QuestionBankQuestionResourceService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * Created by zyp on 15/8/6.
 */
@Controller("questionResourceController")
@RequestMapping("/questionResource")
public class QuestionResourceController  extends BaseController{

  @Resource
  QuestionBankQuestionResourceService questionBankQuestionResourceService;
  
  @Resource
  CourseSubjectService courseSubjectService;

  /**
   * 列表
   * @return
   */
  @RequestMapping("/list")
  public ModelAndView list(){
    ModelAndView modelAndView  = new ModelAndView("/exam/resourceList");
    
    List<QuestionBankQuestionResourceModel>  resourceList =
      questionBankQuestionResourceService.resourceList();
    
    modelAndView.addObject("resourceList",resourceList);
    return modelAndView;
  }

  /**
   * 添加
   * @return
   */
  @RequestMapping("/add")
  public ModelAndView add(){
    ModelAndView modelAndView  = new ModelAndView("/exam/resourceAdd");
  //modified by zdh at 23/5
    List<CourseSubjectModel> result = courseSubjectService.queryAllCourse().getResult();
    // 下拉框 ： 添加试题的关联课程
    modelAndView.addObject("allCourses", result);
    
    return modelAndView;
  }

  /**
   * 添加
   * @param name
   * @param detail
   * @return
   */
  @RequestMapping("/doAdd")
  public ModelAndView doAdd(String name,String detail,Integer courseId){
    QuestionBankQuestionResourceModel resourceModel = new QuestionBankQuestionResourceModel();
    resourceModel.setName(name);
    resourceModel.setDetail(detail);
    
    //modified by zdh at 23/5
    resourceModel.setCourseId(courseId);
    
    resourceModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    questionBankQuestionResourceService.addResource(resourceModel);
    return this.showMessage("成功",null,null,true);
  }

  /**
   * 编辑
   * @param resourceId
   * @return
   */
  @RequestMapping("/edit")
  public ModelAndView edit(Integer resourceId){
	  
    ModelAndView modelAndView  = new ModelAndView("/exam/resourceEdit");
    
    QuestionBankQuestionResourceModel resourceModel 
    	=questionBankQuestionResourceService.findResource(resourceId);
    //modified by zdh at 23/5
    List<CourseSubjectModel> result = courseSubjectService.queryAllCourse().getResult();
    // 下拉框 ： 添加试题的关联课程
    modelAndView.addObject("allCourses", result);
    
    modelAndView.addObject("resourceModel",resourceModel);
    return modelAndView;
  }

  /**
   * 编辑
   * @param id
   * @param name
   * @param detail
   * @return
   */
  @RequestMapping("/doEdit")
  public ModelAndView doEdit(Integer id,String name,String detail,Integer courseId){
    QuestionBankQuestionResourceModel resourceModel = new QuestionBankQuestionResourceModel();
    resourceModel.setId(id);
    resourceModel.setName(name);
    resourceModel.setDetail(detail);
    //modified by zdh at 23/5
    resourceModel.setCourseId(courseId);
    questionBankQuestionResourceService.editResource(resourceModel);
    return this.showMessage("成功",null,"/questionResource/edit?resourceId="+id,true);
  }

  /**
   * 删除
   * @param resourceId
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public String delete(Integer resourceId){
    questionBankQuestionResourceService.deleteResource(resourceId);
    return new ResultInfo(ResultType.SUCCESS).toString0();
  }

}
