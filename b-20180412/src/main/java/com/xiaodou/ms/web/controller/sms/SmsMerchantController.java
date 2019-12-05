package com.xiaodou.ms.web.controller.sms;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.ms.model.sms.SmsMerchantModel;
import com.xiaodou.ms.service.sms.SmsMerchantService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.sms.MerchantCreateRequest;
import com.xiaodou.ms.web.request.sms.MerchantEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

/**
 * Created by zyp on 15/6/25.
 */
@Controller("SmsMerchantController")
@RequestMapping("/smsMerchant")
public class SmsMerchantController extends BaseController {
	@Resource
	SmsMerchantService smsMerchantService;
	
	/**
	 * 供应商目录
	 */
	@RequestMapping("/list")
	public ModelAndView resourceMerchant(){
		try {
		      ModelAndView modelAndView = new ModelAndView("/sms/merchantList");
		      List<SmsMerchantModel> merchantLists = smsMerchantService.queryAllMerchant();
		      modelAndView.addObject("merchantLists",merchantLists);
		      return modelAndView;
		    } catch (Exception e){
		      LoggerUtil.error("列表异常", e);
		      throw e;
		    }
	}
	 /**
	   * 供应商添加页面
	   * @return
	   */
	@RequestMapping("/add")
	  public ModelAndView resourceMerchantAdd(){
	    ModelAndView modelAndView = new ModelAndView("/sms/merchantAdd");
	    return modelAndView;
	  }
	
	 /**
	   * 资源供应商添加
	   * @return
	   */
	  @RequestMapping("/doAdd")
	  public ModelAndView resourceCategoryDoAdd(MerchantCreateRequest request,Errors errors) throws Exception{
	    try {
	      BaseResponse response = null;
	      errors = request.validate();
	      if (errors.hasErrors()){
	        response = new BaseResponse(ResultType.VALID_FAIL);
	        response.setRetDesc(this.getErrMsg(errors));
	      } else {
	        response = smsMerchantService.addMerchant(request);
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
	   * 供应商编辑
	   * @param categoryId
	   * @return
	   */
	  @RequestMapping("/edit")
	  public ModelAndView resourceMerchantEdit(Long categoryId){
	    ModelAndView modelAndView = new ModelAndView("/sms/merchantEdit");
	    SmsMerchantModel smsMerchantModel = smsMerchantService.findMerchantById(categoryId);
	    modelAndView.addObject("smsMerchantModel",smsMerchantModel);
	    return modelAndView;
	  }
	 
	  /**
	   * 资源供应商修改
	   * @return
	   */
	  @RequestMapping("/doEdit")
	  public ModelAndView resourceMerchantDoEdit(MerchantEditRequest request,Errors errors) throws Exception{
	    try {
	      BaseResponse response = null;
	      errors = request.validate();
	      if (errors.hasErrors()){
	    	  response = new BaseResponse(ResultType.VALID_FAIL);
		        response.setRetDesc(this.getErrMsg(errors));
	        
	      } else {
	    	  response = smsMerchantService.editMerchant(request);
	    	 
	      }
	      if (response.getRetCode()==0){
	        return this.showMessage("编辑成功","","/smsMerchant/edit?categoryId="+request.getId(),true);
	      } else {
	        return this.showMessage("编辑失败",response.getRetDesc(),"/smsMerchant/categoryId="+request.getId(),true);
	      }
	    } catch (Exception e){
	      LoggerUtil.error("目录编辑异常",e);
	      throw e;
	    }
	  }

	  /**
	   * 资源供应商删除
	   * @return
	   */
	  @RequestMapping("/delete")
	  @ResponseBody
	  public String redourceMerchantDel(Integer catId){
	    try {
	      BaseResponse response = null;
	      Boolean aBoolean = smsMerchantService.deleteMerchant(catId);
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
