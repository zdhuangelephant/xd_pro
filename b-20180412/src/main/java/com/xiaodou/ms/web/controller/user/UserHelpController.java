package com.xiaodou.ms.web.controller.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.ms.model.user.UserHelpModel;
import com.xiaodou.ms.service.user.UserHelpService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.user.UserHelpCreateRequest;
import com.xiaodou.ms.web.request.user.UserHelpEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

@Controller("userHelpController")
@RequestMapping("/userHelp")
public class UserHelpController extends BaseController {
	
	@Resource
	UserHelpService userHelpService;
	/**
	 * 用户帮助目录
	 */
	@RequestMapping("/list")
	public ModelAndView resourceUserHelpList(){
		try {
		      ModelAndView modelAndView = new ModelAndView("/user/userHelpList");
		      List<UserHelpModel> userHelpLists = userHelpService.queryAllUserHelp();
		      modelAndView.addObject("userHelpLists",userHelpLists);
		      return modelAndView;
		    } catch (Exception e){
		      LoggerUtil.error("列表异常", e);
		      throw e;
		    }
	}
	 /**
	   * 用户帮助添加页面
	   * @return
	   */
	@RequestMapping("/add")
	  public ModelAndView resourceUserHelpAdd(){
	    ModelAndView modelAndView = new ModelAndView("/user/userHelpAdd");
	    return modelAndView;
	  }
	
	 /**
	   * 资源用户帮助添加
	   * @return
	   */
	  @RequestMapping("/doAdd")
	  public ModelAndView resourceUserHelpDoAdd(UserHelpCreateRequest request,Errors errors) throws Exception{
	    try {
	      BaseResponse response = null;
	      errors = request.validate();
	      if (errors.hasErrors()){
	        response = new BaseResponse(ResultType.VALID_FAIL);
	        response.setRetDesc(this.getErrMsg(errors));
	      } else {
	        response = userHelpService.addUserHelp(request);
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
	   * 用户帮助编辑
	   * @param categoryId
	   * @return
	   */
	  @RequestMapping("/edit")
	  public ModelAndView resourceUserHelpEdit(String categoryId){
	    ModelAndView modelAndView = new ModelAndView("/user/userHelpEdit");
	    UserHelpModel userHelpModel = userHelpService.findUserHelpById(Integer.valueOf(categoryId));
	    modelAndView.addObject("userHelpModel",userHelpModel);
	    return modelAndView;
	  }
	 
	  /**
	   * 资源用户帮助修改
	   * @return
	   */
	  @RequestMapping("/doEdit")
	  public ModelAndView resourceMerchantDoEdit(UserHelpEditRequest request,Errors errors) throws Exception{
	    try {
	      BaseResponse response = null;
	      errors = request.validate();
	      if (errors.hasErrors()){
	    	  response = new BaseResponse(ResultType.VALID_FAIL);
		        response.setRetDesc(this.getErrMsg(errors));
	        
	      } else {
	    	  response = userHelpService.editUserHelp(request);
	    	 
	      }
	      if (response.getRetCode()==0){
	        return this.showMessage("编辑成功","","/userHelp/edit?categoryId="+request.getId(),true);
	      } else {
	        return this.showMessage("编辑失败",response.getRetDesc(),"/userHelp/categoryId="+request.getId(),true);
	      }
	    } catch (Exception e){
	      LoggerUtil.error("目录编辑异常",e);
	      throw e;
	    }
	  }

	  /**
	   * 资源用户帮助删除
	   * @return
	   */
	  @RequestMapping("/delete")
	  @ResponseBody
	  public String redourceUserHelpDel(Integer catId){
	    try {
	      BaseResponse response = null;
	      Boolean aBoolean = userHelpService.deleteUserHelp(catId);
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
