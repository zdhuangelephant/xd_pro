package com.xiaodou.ms.web.controller.topic;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.ms.model.topic.ForumListModel;
import com.xiaodou.ms.service.topic.TopicCategoryService;
import com.xiaodou.ms.service.topic.TopicService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.topic.ForumEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;


@Controller("forumCloseController")
@RequestMapping("/forumClose")
public class ForumCloseController extends BaseController {
	/**
	 * 话题关闭
	 */
	@Resource
	TopicService topicService;
	
	@Resource
	TopicCategoryService topcatCategoryService;

	 /**
	  * 关闭的话题列表
	  * @param catId
	  * @return
	  */
	@RequestMapping("/list")
	  public ModelAndView courseList(Integer catId,Integer status){
	    ModelAndView modelAndView = new ModelAndView("/topic/closeForum");
	    if (status==null){
	    	status = 0;
	    }
	    if (catId==null){
	    	catId = 0;
	    }
	    Page<ForumListModel> courseSubjectList =
	    		topicService.cascadeQueryCourseByStatus(catId,status);
	    Map<String, Object> categoryMap = topcatCategoryService.queryAllCategoryMap();
	    modelAndView.addObject("subjectList",courseSubjectList.getResult());
	    modelAndView.addObject("categoryMap",categoryMap);
	    modelAndView.addObject("catId", catId);
	    modelAndView.addObject("status", status);
	    return modelAndView;
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
