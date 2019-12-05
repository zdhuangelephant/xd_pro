package com.xiaodou.ms.web.controller.sms;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.ms.model.sms.SmsChannelModel;
import com.xiaodou.ms.model.sms.SmsTemplateTypeModel;
import com.xiaodou.ms.service.sms.SmsChannelService;
import com.xiaodou.ms.service.sms.SmsTemplateTypeService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.sms.TemplateTypeCreateRequest;
import com.xiaodou.ms.web.request.sms.TemplateTypeEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zyp on 15/6/25.
 */
@Controller("smsTemplateTypeController")
@RequestMapping("/smsTemplateType")
public class SmsTemplateTypeController extends BaseController {
	
	@Resource
	SmsTemplateTypeService smsTemplateTypeService;
	@Resource
	SmsChannelService smsChannelService;

	 /**
	  * 模板类型列表
	  * @param catId
	  * @return
	  */
	@RequestMapping("/list")
	  public ModelAndView channelList(Integer catId){
	    ModelAndView modelAndView = new ModelAndView("/sms/templateTypeList");
	    if (catId==null){
	    	catId = 0;
	    }
	    Page<SmsTemplateTypeModel> templateTypeList =
	    		smsTemplateTypeService.cascadeQueryTemplateTypeByCatId(String.valueOf(catId));
	    Map<String, Object> channelMap = smsChannelService.queryAllChannelMap();
	    List<SmsChannelModel> selectChannel=smsChannelService.channelSelect(catId.toString());
	    modelAndView.addObject("templateTypeList",templateTypeList.getResult());
	    modelAndView.addObject("channelMap",channelMap);
	    modelAndView.addObject("selectChannel", selectChannel);
	    modelAndView.addObject("catId", catId);
	    return modelAndView;
	  }
	 /**
	   * 模板类型添加页面
	   * @return
	   */
	@RequestMapping("/add")
	  public ModelAndView resourceTemplateTypeAdd(){
	    ModelAndView modelAndView = new ModelAndView("/sms/templateTypeAdd");
	    List<SmsChannelModel> selectChannel=smsChannelService.channelSelect(null);
	    modelAndView.addObject("selectChannel", selectChannel);
	    return modelAndView;
	  }
	
	 /**
	   * 资源模板类型添加
	   * @return
	   */
	  @RequestMapping("/doAdd")
	  public ModelAndView resourceTemplateTypeDoAdd(TemplateTypeCreateRequest request,Errors errors) throws Exception{
	    try {
	      BaseResponse response = null;
	      errors = request.validate();
	      if (errors.hasErrors()){
	        response = new BaseResponse(ResultType.VALID_FAIL);
	        response.setRetDesc(this.getErrMsg(errors));
	      } else {
	        response = smsTemplateTypeService.addTemplateType(request);
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
	   * 资源模板类型删除
	   * 
	   * @return
	   */
	  @RequestMapping("/delete")
	  @ResponseBody
	  public String resourceTemplateTypeDel(Integer id){
	    try {
	      BaseResponse response = null;
	      Boolean aBoolean = smsTemplateTypeService.deleteTemplateType(id);
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
	   * 模板类型编辑
	   * @param id
	   * @return
	   */
	  @RequestMapping("/edit")
	  public ModelAndView resourceTemplateTypeEdit(Integer id){
	    ModelAndView modelAndView = new ModelAndView("/sms/templateTypeEdit");
	    SmsTemplateTypeModel templateTypeModel =  smsTemplateTypeService.findTemplateTypeById(id);
	    modelAndView.addObject("templateTypeModel",templateTypeModel);
	    return modelAndView;
	  }

	  /**
	   * 资源模板类型编辑
	   * 
	   * @return
	   */
	  @RequestMapping("/doEdit")
	  public ModelAndView resourceTemplateTypeDoEdit(TemplateTypeEditRequest request,Errors errors){
	    try {
	      BaseResponse response = null;
	      errors = request.validate();
	      if (errors.hasErrors()){
	        response = new BaseResponse(ResultType.VALID_FAIL);
	        response.setRetDesc(this.getErrMsg(errors));
	      } else {
	        smsTemplateTypeService.editTemplateType(request);
	        response = new BaseResponse(ResultType.SUCCESS);
	      }
	      if (response.getRetCode()==0){
	        return this.showMessage("编辑成功","","/smsTemplateType/edit?id="+request.getId(),true);
	      } else {
	        return this.showMessage("编辑失败",response.getRetDesc(),"/smsTemplateType/edit?id="+request.getId(),true);
	      }
	    } catch (Exception e){
	      throw e;
	    }
	  }
}
