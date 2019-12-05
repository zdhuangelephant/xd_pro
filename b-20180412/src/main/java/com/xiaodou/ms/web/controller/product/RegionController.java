package com.xiaodou.ms.web.controller.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.ms.model.product.ProductModuleModel;
import com.xiaodou.ms.model.product.RegionModel;
import com.xiaodou.ms.model.score.ScoreRuleModel;
import com.xiaodou.ms.service.product.ProductService;
import com.xiaodou.ms.service.product.RegionService;
import com.xiaodou.ms.service.scoreRule.ScoreRuleService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.product.ProductQueryConds;
import com.xiaodou.ms.web.request.product.RegionCreateRequest;
import com.xiaodou.ms.web.request.product.RegionEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * @name RegionController 
 * CopyRright (c) 2018 by <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年4月12日
 * @description 原来的module字段 含义由模块变为地域
 * @version 1.0
 */
@Controller("regionController")
@RequestMapping("/region")
public class RegionController extends BaseController {

  @Resource
  RegionService regionService;
  
  @Resource
  ScoreRuleService scoreRuleService;
  
  @Resource
  ProductService productService;
  /**
   * 资源目录
   * 
   * @return
   */
  @RequestMapping("/list")
  public ModelAndView resourceRegion() {
    try {
      ModelAndView modelAndView = new ModelAndView("/region/regionList");
      List<RegionModel> regionList = regionService.queryAllRegion();
      ProductQueryConds req= new ProductQueryConds();
      req.setCourseCode("00000");
      Page<ProductModel> courseSubjectList = productService.queryCourseByCode(req, null, "","1");
      List<ProductModel> productList=courseSubjectList.getResult();
      for(RegionModel p: regionList){
    	  for(ProductModel pt:productList){
    		  if(p.getModule().equals(pt.getModule())){
    			  p.setCourseId(pt.getId().toString());
    		  }
    	  }
      }
      
      modelAndView.addObject("regionList", regionList);
      return modelAndView;
    } catch (Exception e) {
      LoggerUtil.error("列表异常", e);
      throw e;
    }
  }

  /**
   * 目录添加页面
   * 
   * @return
   */
  @RequestMapping("/add")
  public ModelAndView resourceRegionAdd() {
    ModelAndView modelAndView = new ModelAndView("/region/regionAdd");
    return modelAndView;
  }

  /**
   * 资源目录添加
   * 
   * @return
   */
  @RequestMapping("/doAdd")
  public ModelAndView resourceRegionDoAdd(RegionCreateRequest request, Errors errors)
      throws Exception {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
    	List<RegionModel> result = regionService.queryRegionWithModule(request.getModule());
    	if (result!=null && result.size()>0) {
            return this.showMessage("添加失败", "地域编码重复", null, true);
		}    
    	  
        String ruleName  = request.getModuleName()+"默认规则";
        String ruleDesc  = request.getModuleName()+"的"+"规则";
        ScoreRuleModel addDefaultRule = scoreRuleService.addDefaultRule(1, ruleName, ruleDesc);
        request.setRuleId(addDefaultRule.getId());
        
        RegionModel addRegion = regionService.addRegion(request);
        if(addRegion.getFirstChoice()!=null&&addRegion.getFirstChoice()==1){
        	regionService.editRegionState(addRegion.getId().toString());
        }
        if(request.getFirstChoice()!=null&&request.getFirstChoice()==1){
        	regionService.editRegionState(addRegion.getId().toString());
        }
        if (addRegion!=null) {
          response = new BaseResponse(ResultType.SUCCESS);
        }
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("添加成功", "", null, true);
      } else {
        return this.showMessage("添加失败", response.getRetDesc(), null, true);
      }
    } catch (Exception e) {
      LoggerUtil.error("目录创建异常", e);
      throw e;
    }
  }

  /**
   * 目录编辑
   * 
   * @param id
   * @return
   */
  @RequestMapping("/edit")
  public ModelAndView resourceRegionEdit(Long id) {
    ModelAndView modelAndView = new ModelAndView("/region/regionEdit");
    RegionModel region = regionService.findRegionById(id);
    modelAndView.addObject("region", region);
    return modelAndView;
  }

  /**
   * 资源目录修改
   * 
   * @return
   */
  @RequestMapping("/doEdit")
  public ModelAndView resourceRegionDoEdit(RegionEditRequest request, Errors errors)
      throws Exception {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        response = regionService.editRegion(request);
        if(request.getFirstChoice()!=null&&request.getFirstChoice()==1){
        	regionService.editRegionState(request.getId().toString());
        }
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("修改成功", "", null, true);
      } else {
        return this.showMessage("修改失败", response.getRetDesc(), null, true);
      }
    } catch (Exception e) {
      LoggerUtil.error("目录编辑异常", e);
      throw e;
    }
  }

  /**
   * 资源目录删除
   * 
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public String moduleDel(Long id) {
    try {
      BaseResponse response = null;
      Boolean aBoolean = regionService.deleteRegion(id);
      if (aBoolean) {
        response = new BaseResponse(ResultType.SUCCESS);
      } else {
        response = new BaseResponse(ResultType.SYS_FAIL);
      }
      return JSON.toJSONString(response);
    } catch (Exception e) {
      LoggerUtil.error("目录删除异常", e);
      throw e;
    }
  }
  
  /**
   * 排序
   * 
   * @return
   */
  @RequestMapping("/order")
  @ResponseBody
  public Boolean order(String orders) {
    Boolean response = false;
    String[] orderList = orders.split(";");
    for (String orderItem : orderList) {
      String[] split = orderItem.split(":");
      String id = split[0];
      if(split.length<2){
    	  
      }else{
	      Integer order = Integer.parseInt(split[1]);
	      RegionModel model = new RegionModel();
	      model.setId(Long.valueOf(id));
	      model.setListOrder(order);
	      response = regionService.editRegion(model);
      }
    }
    return response;
  }

  
  /**
   * 添加默认积分规则
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/addScoreRule")
  public BaseResponse addScoreRule(Long regionId) {
	  BaseResponse response = null;	 
	  try {
	      Boolean flag= false; 
	      if (regionId!=null) {
			 RegionModel findRegionById = regionService.findRegionById(regionId);
		      if (findRegionById !=null) {
		        String ruleName =findRegionById.getModuleName()+ "规则";
		        String ruleDesc =findRegionById.getModuleName();
		        ScoreRuleModel addDefaultRule = scoreRuleService.addDefaultRule(1, ruleName, ruleDesc);
		        findRegionById.setRuleId(addDefaultRule.getId());
		        flag =  regionService.editRegion(findRegionById);
		      }
	     }
		    if (flag) {
		    	 response = new BaseResponse(ResultType.SUCCESS); 
		    	 response.setRetDesc("添加默认规则成功");
			} else{
				response = new BaseResponse(ResultType.SYS_FAIL); 
		   	 	response.setRetDesc("添加默认失败");
			}
	    } catch (Exception e) {
	      LoggerUtil.error("添加默认规则出错", e);
	    }
    return response;
  }
  
}
