package com.xiaodou.ms.web.controller.topic;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xiaodou.ms.model.topic.ForumCommentModel;
import com.xiaodou.ms.service.topic.TopicCommentService;
import com.xiaodou.ms.service.topic.TopicService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.topic.CommentCreateRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/6/25.
 */
@Controller("forumCommentController")
@RequestMapping("/forumComment")
public class ForumCommentController extends BaseController {
	/**
	 * 回帖
	 */
	@Resource
	TopicService topicService;
	
	@Resource
	TopicCommentService topicCommentService;

	 /**
	  * 回帖资源列表
	  * @param catId
	  * @return
	  */
	@RequestMapping("/list")
	  public ModelAndView courseList(Integer catId){
	    ModelAndView modelAndView = new ModelAndView("/topic/commentList");
	    if (catId==null){
	    	catId = 0;
	    }
	    Page<ForumCommentModel> forumCommentList =
	    		topicCommentService.cascadeQueryCourseByCatId(catId);
	    Map<String, Object> forumMap = topicService.queryAllForumMap();
	    modelAndView.addObject("forumCommentList",forumCommentList.getResult());
	    modelAndView.addObject("forumMap", forumMap);
	    return modelAndView;
	  }


	  /**
	   * 资源回帖删除
	   * 
	   * @return
	   */
	  @RequestMapping("/delete")
	  @ResponseBody
	  public String resourceCourseDel(Integer id){
	    try {
	      BaseResponse response = null;
	      Boolean aBoolean = topicCommentService.deleteComment(id);
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
	   * 编辑回帖状态
	   * @param id
	   * @return
	   */
	  @RequestMapping("/edit")
	  public ModelAndView commentStatusEdit(Integer id){
	    ModelAndView modelAndView = new ModelAndView("/topic/commentEdit");
	    ForumCommentModel forumCommentModel =  topicCommentService.findCommentById(id);
	    modelAndView.addObject("forumCommentModel",forumCommentModel);
	    return modelAndView;
	  }

	  /**
	   * 资源话题编辑
	   * 
	   * @return
	   */
	  @RequestMapping("/doEdit")
	  public ModelAndView commentStatusDoEdit(CommentCreateRequest request,Errors errors){
	    try {
	      BaseResponse response = null;
	      errors = request.validate();
	      if (errors.hasErrors()){
	        response = new BaseResponse(ResultType.VALID_FAIL);
	        response.setRetDesc(this.getErrMsg(errors));
	      } else {
	    	  topicCommentService.editSubject(request);
	        response = new BaseResponse(ResultType.SUCCESS);
	      }
	      if (response.getRetCode()==0){
	        return this.showMessage("编辑成功","","/forumComment/edit?id="+request.getId(),true);
	      } else {
	        return this.showMessage("编辑失败",response.getRetDesc(),"/forumComment/edit?id="+request.getId(),true);
	      }
	    } catch (Exception e){
	      throw e;
	    }
	  }
	
}
