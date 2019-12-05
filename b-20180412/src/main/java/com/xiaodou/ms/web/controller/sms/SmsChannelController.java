package com.xiaodou.ms.web.controller.sms;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.ms.model.sms.SmsChannelModel;
import com.xiaodou.ms.model.sms.SmsMerchantModel;
import com.xiaodou.ms.service.sms.SmsChannelService;
import com.xiaodou.ms.service.sms.SmsMerchantService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.sms.ChannelCreateRequest;
import com.xiaodou.ms.web.request.sms.ChannelEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/6/25.
 */
@Controller("smsChannelController")
@RequestMapping("/smsChannel")
public class SmsChannelController extends BaseController {
	
	/**
	 * 话题
	 */
	@Resource
	SmsChannelService smsChannelService;
	
	@Resource
	SmsMerchantService smsMerchantService;


	 /**
	  * 通道列表
	  * @param catId
	  * @return
	  */
	@RequestMapping("/list")
	  public ModelAndView channelList(Integer catId){
	    ModelAndView modelAndView = new ModelAndView("/sms/channelList");
	    if (catId==null){
	    	catId = 0;
	    }
	    Page<SmsChannelModel> channelList =
	    		smsChannelService.cascadeQueryChannelByCatId(catId);
	    Map<String, Object> channelMap = smsMerchantService.queryAllMerchantMap();
	    List<SmsMerchantModel> selectMerchant=smsMerchantService.merchantSelect(catId.toString());
	    modelAndView.addObject("channelList",channelList.getResult());
	    modelAndView.addObject("channelMap",channelMap);
	    modelAndView.addObject("selectMerchant", selectMerchant);
	    modelAndView.addObject("catId", catId);
	    return modelAndView;
	  }
	 /**
	   * 通道添加页面
	   * @return
	   */
	@RequestMapping("/add")
	  public ModelAndView resourceChannelAdd(){
	    ModelAndView modelAndView = new ModelAndView("/sms/channelAdd");
	    List<SmsMerchantModel> selectMerchant=smsMerchantService.merchantSelect(null);
	    modelAndView.addObject("selectMerchant", selectMerchant);
	    return modelAndView;
	  }
	
	 /**
	   * 资源供应商添加
	   * @return
	   */
	  @RequestMapping("/doAdd")
	  public ModelAndView resourceChannelDoAdd(ChannelCreateRequest request,Errors errors) throws Exception{
	    try {
	      BaseResponse response = null;
	      errors = request.validate();
	      if (errors.hasErrors()){
	        response = new BaseResponse(ResultType.VALID_FAIL);
	        response.setRetDesc(this.getErrMsg(errors));
	      } else {
	        response = smsChannelService.addChannel(request);
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
	   * 资源通道删除
	   * 
	   * @return
	   */
	  @RequestMapping("/delete")
	  @ResponseBody
	  public String resourceChannelDel(Integer id){
	    try {
	      BaseResponse response = null;
	      Boolean aBoolean = smsChannelService.deleteChannel(id);
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
	   * 通道编辑
	   * @param id
	   * @return
	   */
	  @RequestMapping("/edit")
	  public ModelAndView resourceChannelEdit(Integer id){
	    ModelAndView modelAndView = new ModelAndView("/sms/channelEdit");
	    SmsChannelModel channelModel =  smsChannelService.findChannelById(id);
	    modelAndView.addObject("channelModel",channelModel);
	    return modelAndView;
	  }

	  /**
	   * 资源通道编辑
	   * 
	   * @return
	   */
	  @RequestMapping("/doEdit")
	  public ModelAndView resourceChannelDoEdit(ChannelEditRequest request,Errors errors){
	    try {
	      BaseResponse response = null;
	      errors = request.validate();
	      if (errors.hasErrors()){
	        response = new BaseResponse(ResultType.VALID_FAIL);
	        response.setRetDesc(this.getErrMsg(errors));
	      } else {
	        smsChannelService.editChannel(request);
	        response = new BaseResponse(ResultType.SUCCESS);
	      }
	      if (response.getRetCode()==0){
	        return this.showMessage("编辑成功","","/smsChannel/edit?id="+request.getId(),true);
	      } else {
	        return this.showMessage("编辑失败",response.getRetDesc(),"/smsChannel/edit?id="+request.getId(),true);
	      }
	    } catch (Exception e){
	      throw e;
	    }
	  }
}
