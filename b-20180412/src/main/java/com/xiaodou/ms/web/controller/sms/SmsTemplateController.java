package com.xiaodou.ms.web.controller.sms;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.ms.model.sms.SmsTemplateModel;
import com.xiaodou.ms.model.sms.SmsTemplateTypeModel;
import com.xiaodou.ms.service.sms.SmsTemplateService;
import com.xiaodou.ms.service.sms.SmsTemplateTypeService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.sms.TemplateCreateRequest;
import com.xiaodou.ms.web.request.sms.TemplateEditRequest;
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
@Controller("smsTemplateController")
@RequestMapping("/smsTemplate")
public class SmsTemplateController extends BaseController {
	
	@Resource
	SmsTemplateTypeService smsTemplateTypeService;
	@Resource
	SmsTemplateService smsTemplateService;

	 /**
	  * 短信模板列表
	  * @param catId
	  * @return
	  */
	@RequestMapping("/list")
	  public ModelAndView templateList(Integer catId){
	    ModelAndView modelAndView = new ModelAndView("/sms/templateList");
	    if (catId==null){
	    	catId = 0;
	    }
	    Page<SmsTemplateModel> templateList =
	    		smsTemplateService.cascadeQueryTemplateByCatId(catId);
	    Map<String, Object> templateTypeMap = smsTemplateTypeService.queryAllTemplateTypeMap();
	    List<SmsTemplateTypeModel> selectTemplateType=smsTemplateTypeService.TemplateTypeSelect(catId.toString());
	    modelAndView.addObject("templateList",templateList.getResult());
	    modelAndView.addObject("templateTypeMap",templateTypeMap);
	    modelAndView.addObject("selectTemplateType",selectTemplateType);
	    modelAndView.addObject("catId", catId);
	    return modelAndView;
	  }
	 /**
	   * 模板添加页面
	   * @return
	   */
	@RequestMapping("/add")
	  public ModelAndView resourceTemplateAdd(){
	    ModelAndView modelAndView = new ModelAndView("/sms/templateAdd");
	    List<SmsTemplateTypeModel> selectTemplateType=smsTemplateTypeService.TemplateTypeSelect(null);
	    modelAndView.addObject("selectTemplateType",selectTemplateType);
	    return modelAndView;
	  }
	
	 /**
	   * 资源模板添加
	   * @return
	   */
	  @RequestMapping("/doAdd")
	  public ModelAndView resourceTemplateDoAdd(TemplateCreateRequest request,Errors errors) throws Exception{
	    try {
	      BaseResponse response = null;
	      errors = request.validate();
	      if (errors.hasErrors()){
	        response = new BaseResponse(ResultType.VALID_FAIL);
	        response.setRetDesc(this.getErrMsg(errors));
	      } else {
	        response = smsTemplateService.addTemplate(request);
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
	   * 资源模板删除
	   * 
	   * @return
	   */
	  @RequestMapping("/delete")
	  @ResponseBody
	  public String resourceTemplateDel(Integer id){
	    try {
	      BaseResponse response = null;
	      Boolean aBoolean = smsTemplateService.deleteTemplate(id);
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
	   * 模板编辑
	   * @param id
	   * @return
	   */
	  @RequestMapping("/edit")
	  public ModelAndView resourceTemplateEdit(Integer id){
	    ModelAndView modelAndView = new ModelAndView("/sms/templateEdit");
	    SmsTemplateModel templateModel =  smsTemplateService.findTemplateById(id);
	    modelAndView.addObject("templateModel",templateModel);
	    return modelAndView;
	  }

	  /**
	   * 资源模板编辑
	   * 
	   * @return
	   */
	  @RequestMapping("/doEdit")
	  public ModelAndView resourceTemplateDoEdit(TemplateEditRequest request,Errors errors){
	    try {
	      BaseResponse response = null;
	      errors = request.validate();
	      if (errors.hasErrors()){
	        response = new BaseResponse(ResultType.VALID_FAIL);
	        response.setRetDesc(this.getErrMsg(errors));
	      } else {
	    	  smsTemplateService.editTemplate(request);
	        response = new BaseResponse(ResultType.SUCCESS);
	      }
	      if (response.getRetCode()==0){
	        return this.showMessage("编辑成功","","/smsTemplate/edit?id="+request.getId(),true);
	      } else {
	        return this.showMessage("编辑失败",response.getRetDesc(),"/smsTemplate/edit?id="+request.getId(),true);
	      }
	    } catch (Exception e){
	      throw e;
	    }
	  }
}
