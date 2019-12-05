package com.xiaodou.ms.web.controller.course;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.ms.model.course.CourseCategoryModel;
import com.xiaodou.ms.service.course.CourseCategoryService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.course.CategoryCreateRequest;
import com.xiaodou.ms.web.request.course.CategoryEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by zyp on 15/6/25.
 */
@Controller("courseCategoryController")
@RequestMapping("/courseCategory")
public class CourseCategoryController extends BaseController {

  @Resource
  CourseCategoryService courseCategoryService;

  /**
   * 资源目录
   * @return
   */
  @RequestMapping("/list")
  public ModelAndView resourceCategory(){
    try {
      ModelAndView modelAndView = new ModelAndView("/course/categoryList");
      String tableTree = courseCategoryService.categoryTableTree(0L);
      modelAndView.addObject("tableTree",tableTree);
      return modelAndView;
    } catch (Exception e){
      LoggerUtil.error("列表异常", e);
      throw e;
    }
  }

  /**
   * 目录添加页面
   * @param parentId
   * @return
   */
  @RequestMapping("/add")
  public ModelAndView resourceCategoryAdd(String parentId){
    ModelAndView modelAndView = new ModelAndView("/course/categoryAdd");
    String selectTree = courseCategoryService.categorySelectTree(0L, parentId);
    modelAndView.addObject("selectTree",selectTree);
    return modelAndView;
  }

  /**
   * 目录编辑
   * @param categoryId
   * @return
   */
  @RequestMapping("/edit")
  public ModelAndView resourceCategoryEdit(Long categoryId){
    ModelAndView modelAndView = new ModelAndView("/course/categoryEdit");
    CourseCategoryModel courseCategory = courseCategoryService.findCategoryById(categoryId);
    String selectTree = courseCategoryService.categorySelectTree(0L, courseCategory.getParentId().toString());
    modelAndView.addObject("selectTree",selectTree);
    modelAndView.addObject("courseCategory",courseCategory);
    return modelAndView;
  }

  /**
   * 资源目录添加
   * @return
   */
  @RequestMapping("/doAdd")
  public ModelAndView resourceCategoryDoAdd(CategoryCreateRequest request,Errors errors) throws Exception{
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()){
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        response = courseCategoryService.addCategory(request);
      }
      if (response.getRetCode()==0){
        return this.showMessage("添加成功","",null,true);
      } else {
        return this.showMessage("添加失败",response.getRetDesc(),null,true);
      }
    } catch (Exception e){
      LoggerUtil.error("目录创建异常",e);
      throw e;
    }
  }

  /**
   * 资源目录修改
   * @return
   */
  @RequestMapping("/doEdit")
  public ModelAndView resourceCategoryDoEdit(CategoryEditRequest request,Errors errors) throws Exception{
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()){
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        response = courseCategoryService.editCategory(request);
      }
      if (response.getRetCode()==0){
        return this.showMessage("添加成功","","/courseCategory/edit?categoryId="+request.getId(),true);
      } else {
        return this.showMessage("添加失败",response.getRetDesc(),"/courseCategory/edit?categoryId="+request.getId(),true);
      }
    } catch (Exception e){
      LoggerUtil.error("目录编辑异常",e);
      throw e;
    }
  }

  /**
   * 资源目录删除
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public String redourceCategoryDel(Integer catId){
    try {
      BaseResponse response = null;
      Boolean aBoolean = courseCategoryService.deleteCategory(catId);
      if (aBoolean){
        response = new BaseResponse(ResultType.SUCCESS);
      } else {
        response = new BaseResponse(ResultType.SYS_FAIL);
      }
      return JSON.toJSONString(response);
    } catch (Exception e){
      LoggerUtil.error("目录删除异常",e);
      throw e;
    }
  }

}
