package com.xiaodou.ms.web.controller.topic;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xiaodou.ms.model.topic.ForumCategoryModel;
import com.xiaodou.ms.model.topic.ForumListModel;
import com.xiaodou.ms.service.topic.TopicCategoryService;
import com.xiaodou.ms.service.topic.TopicService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.topic.ForumEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/6/25.
 */
@Controller("forumTopicController")
@RequestMapping("/forumTopic")
public class ForumTopicController extends BaseController {
	/**
	 * 话题
	 */
	@Resource
	TopicService topicService;
	
	@Resource
	TopicCategoryService topcatCategoryService;

	 /**
	  * 话题列表
	  * @param catId
	  * @return
	  */
	@RequestMapping("/list")
	  public ModelAndView courseList(Integer catId){
	    ModelAndView modelAndView = new ModelAndView("/topic/forumList");
	    if (catId==null){
	    	catId = 0;
	    }
	   
	    Page<ForumListModel> courseSubjectList =
	    		topicService.cascadeQueryCourseByCatId(catId);
	    List<ForumCategoryModel> forumCategoryList =
	    		topcatCategoryService.queryAllCategory();
	    Map<String, Object> categoryMap = topcatCategoryService.queryAllCategoryMap();
	    modelAndView.addObject("subjectList",courseSubjectList.getResult());
	    modelAndView.addObject("forumCategoryList", forumCategoryList);
	    modelAndView.addObject("categoryMap",categoryMap);
	    modelAndView.addObject("catId", catId);
	    return modelAndView;
	  }


	  /**
	   * 资源话题删除
	   * 
	   * @return
	   */
	  @RequestMapping("/delete")
	  @ResponseBody
	  public String resourceCourseDel(Integer id){
	    try {
	      BaseResponse response = null;
	      Boolean aBoolean = topicService.deleteSubject(id);
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
	   * 编辑话题状态
	   * @param id
	   * @return
	   */
	  @RequestMapping("/edit")
	  public ModelAndView resourceCourseEdit(Integer id){
	    ModelAndView modelAndView = new ModelAndView("/topic/forumEdit");
	    ForumListModel forumListModel =  topicService.findSubjectById(id);
	    modelAndView.addObject("forumListModel",forumListModel);
	    return modelAndView;
	  }

	  /**
	   * 资源话题编辑
	   * 
	   * @return
	   */
	  @RequestMapping("/doEdit")
	  public ModelAndView resourceCourseDoEdit(ForumEditRequest request,Errors errors){
	    try {
	      BaseResponse response = null;
	      errors = request.validate();
	      if (errors.hasErrors()){
	        response = new BaseResponse(ResultType.VALID_FAIL);
	        response.setRetDesc(this.getErrMsg(errors));
	      } else {
	        topicService.editSubject(request);
	        response = new BaseResponse(ResultType.SUCCESS);
	      }
	      if (response.getRetCode()==0){
	        return this.showMessage("编辑成功","","/forumTopic/edit?id="+request.getId(),true);
	      } else {
	        return this.showMessage("编辑失败",response.getRetDesc(),"/forumTopic/edit?id="+request.getId(),true);
	      }
	    } catch (Exception e){
	      throw e;
	    }
	  }
}
