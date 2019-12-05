package com.xiaodou.ms.web.controller.topic;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.ms.model.topic.ForumCategoryModel;
import com.xiaodou.ms.service.topic.TopicCategoryService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.topic.ForumCategoryCreateRequest;
import com.xiaodou.ms.web.request.topic.ForumCategoryEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

/**
 * Created by zyp on 15/6/25.
 */
@Controller("forumCategoryController")
@RequestMapping("/forumCategory")
public class ForumCategoryController extends BaseController {
	@Resource
	TopicCategoryService topicCategoryService;

	  /**
	   * 话题分类列表
	   * @return
	   */
	@RequestMapping("/list")
	  public ModelAndView resourceCategory(Long moudle){
	    try {
	      ModelAndView modelAndView = new ModelAndView("/topic/categoryList");
	      List<ForumCategoryModel> categoryLists = topicCategoryService.queryCategoryByMoudle(moudle);
	      modelAndView.addObject("categoryLists",categoryLists);
	      return modelAndView;
	    } catch (Exception e){
	      LoggerUtil.error("列表异常", e);
	      throw e;
	    }
	  }
	 
	  /**
	   * 话题分类添加页面
	   * @param moudle
	   * @return
	   */
	@RequestMapping("/add")
	  public ModelAndView resourceCategoryAdd(Integer moudle){
	    ModelAndView modelAndView = new ModelAndView("/topic/categoryAdd");
	    return modelAndView;
	  }
	
	  /**
	   * 资源目录添加
	   * @return
	   */
	  @RequestMapping("/doAdd")
	  public ModelAndView resourceCategoryDoAdd(ForumCategoryCreateRequest request,Errors errors) throws Exception{
	    try {
	      BaseResponse response = null;
	      errors = request.validate();
	      if (errors.hasErrors()){
	        response = new BaseResponse(ResultType.VALID_FAIL);
	        response.setRetDesc(this.getErrMsg(errors));
	      } else {
	        response = topicCategoryService.addCategory(request);
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
	   * 目录编辑
	   * @param categoryId
	   * @return
	   */
	  @RequestMapping("/edit")
	  public ModelAndView resourceCategoryEdit(Long categoryId){
	    ModelAndView modelAndView = new ModelAndView("/topic/categoryEdit");
	    ForumCategoryModel forumCategory = topicCategoryService.findCategoryById(categoryId);
	    modelAndView.addObject("forumCategory",forumCategory);
	    return modelAndView;
	  }
	 
	  /**
	   * 资源目录修改
	   * @return
	   */
	  @RequestMapping("/doEdit")
	  public ModelAndView resourceCategoryDoEdit(ForumCategoryEditRequest request,Errors errors) throws Exception{
	    try {
	      BaseResponse response = null;
	      errors = request.validate();
	      if (errors.hasErrors()){
	    	  response = new BaseResponse(ResultType.VALID_FAIL);
		        response.setRetDesc(this.getErrMsg(errors));
	        
	      } else {
	    	  response = topicCategoryService.editCategory(request);
	    	 
	      }
	      if (response.getRetCode()==0){
	        return this.showMessage("编辑成功","","/forumCategory/edit?categoryId="+request.getId(),true);
	      } else {
	        return this.showMessage("编辑失败",response.getRetDesc(),"/forumCategory/categoryId="+request.getId(),true);
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
	      Boolean aBoolean = topicCategoryService.deleteCategory(catId);
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
