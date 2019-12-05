package com.xiaodou.ms.web.controller.scoreRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.ms.enums.ScoreRuleType;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.ms.model.score.RuleInfo;
import com.xiaodou.ms.model.score.ScoreRuleModel;
import com.xiaodou.ms.service.product.ProductService;
import com.xiaodou.ms.service.scoreRule.ScoreRuleService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.product.RegionEditRequest;
import com.xiaodou.ms.web.request.scoreRule.ScoreRuleRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

/**
 * @name Controller 
 * CopyRright (c) 2018 by <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年4月16日
 * @description TODO
 * @version 1.0
 */
@Controller("scoreRuleController")
@RequestMapping("/scoreRule")
public class ScoreRuleController extends BaseController {
  @Resource
  ScoreRuleService scoreRuleService;
  
  /**
   * 产品
   */
  @Resource
  ProductService productService;
  
  /**
   * 添加活动
   * 
   * @return
   */
  @RequestMapping("/add")
  public ModelAndView scoreRuleAdd(Integer productId, Integer sort) {
    ModelAndView modelAndView = new ModelAndView("scoreRule/scoreRuleAdd");
    if (productId != null) {
      modelAndView.addObject("productId", productId);
    }
    modelAndView.addObject("sort", sort);
    return modelAndView;
  }


  /**
   * 修改活动
   */
  @RequestMapping("/edit")
  public ModelAndView scoreRuleEdit(ScoreRuleRequest request) {
    ModelAndView modelAndView = new ModelAndView("scoreRule/scoreRuleEdit");
    ScoreRuleModel scoreRule = scoreRuleService.findRuleById(request.getId());
    if (null!=scoreRule && null!= scoreRule.getRuleDetail() && scoreRule.getRuleDetail().trim().length()>0) {
      scoreRule.initModuleInfo(scoreRule.getRuleDetail());
    }else{
      scoreRule=new ScoreRuleModel(); 
    }
    modelAndView.addObject("scoreRuleType", ScoreRuleType.values());
    modelAndView.addObject("scoreRule", scoreRule);
    modelAndView.addObject("rules", scoreRule.getRuleInfoMap());
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
        response = scoreRuleService.editRule(request);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("修改成功", "", null, true);
      } else {
        return this.showMessage("修改失败", response.getRetDesc(), null, true);
      }
    } catch (Exception e) {
      LoggerUtil.error("程序异常", e);
      throw e;
    }
  }

  /**
   * 刪除规则
   * 
   * @param id
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public String scoreRuleDelete(Integer id) {
    try {
      BaseResponse response = null;
      Boolean aBoolean = scoreRuleService.deleteRule(id);
      if (aBoolean) {
        response = new BaseResponse(ResultType.SUCCESS);
      } else {
        response = new BaseResponse(ResultType.SYS_FAIL);
      }
      return JSON.toJSONString(response);
    } catch (Exception e) {
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
  public Boolean order(Long courseId,String weights,String id,String orderString,String type,String ruleDesc) {
    
    Boolean flag= false;
    ScoreRuleModel scoreRule = scoreRuleService.findRuleById(id);
    if(scoreRule==null){
        return flag;
      }
    
    if(scoreRule.getScope()==2){
      flag = setArguments(id,weights,orderString,ruleDesc);
    }
    
    if(courseId==null && type ==null){
      flag = setArguments(id,weights,orderString,ruleDesc);
    }else{
      ProductModel productModel = productService.findSubjectById(courseId);
      if (productModel!=null) {
        String ruleName =productModel.getName() + "规则";
        String defaultDesc =productModel.getBriefInfo(); 
        ScoreRuleModel addDefaultRule = scoreRuleService.addDefaultRule(2, ruleName, defaultDesc);
        flag = setArguments(addDefaultRule.getId(),weights,orderString,addDefaultRule.getRuleDesc());
        productModel.setRuleId(addDefaultRule.getId());
        flag =  productService.editSubject(productModel);
      }
    }
    return flag;
  }

  //将实现排序的方法提取出来
  private  boolean setArguments(String id,String weights,String orderString,String ruleDesc) {
    
    ScoreRuleModel scoreRule = scoreRuleService.findRuleById(id);
    Map<String, RuleInfo> ruleInfoMap = new TreeMap<String, RuleInfo>();
    
    if (null!=scoreRule && null!= scoreRule.getRuleDetail() && scoreRule.getRuleDetail().trim().length()>0) {
      scoreRule.initModuleInfo(scoreRule.getRuleDetail());
       ruleInfoMap = scoreRule.getRuleInfoMap();
    }
    
    if (weights!=null &&weights.trim().length()>0) {
      String[] weightList = weights.split(";");
      for (String weightItem : weightList) {
        String[] split = weightItem.split(":");
        String index = split[0];      
        RuleInfo ruleInfo = ruleInfoMap.get(index);
        Double weight = Double.valueOf(split[1]);
        ruleInfo.setWeight(weight);
      }
    }
    
    if (orderString!=null && orderString.trim().length()>0) {
      String[] orderList = orderString.split(";");
      for (String orderItem : orderList) {
        String[] split = orderItem.split(":");
        String index = split[0];      
        RuleInfo ruleInfo = ruleInfoMap.get(index);
        Integer order = Integer.valueOf(split[1]);
        ruleInfo.setOrder(order);
      }
    }
    
    List<RuleInfo> jsonList = new ArrayList<RuleInfo>();
    for (String key : ruleInfoMap.keySet()) {
      RuleInfo ruleInfo = ruleInfoMap.get(key);
      jsonList.add(ruleInfo);
    }
    String json = JSON.toJSONString(jsonList);  
    scoreRule.setRuleDetail(json);//ruleDetail
    scoreRule.setRuleDesc(ruleDesc);//ruleDesc
    return  scoreRuleService.editRule(scoreRule);
  }
  
  /**
   * 排序
   * 
   * @return
   */
  @RequestMapping("/orderNew")
  @ResponseBody
  public Boolean orderNew(Long courseId,String weights,String id,String orderString,String type,String ruleDesc) {
    
    Boolean flag= false;
    ProductModel productModel = productService.findSubjectById(courseId);
    ScoreRuleModel rule=scoreRuleService.findRuleById(productModel.getRuleId());
    if (productModel!=null) {
	    if(rule.getScope()==1){
	    	if(rule!=null){
		    	ScoreRuleModel s= setArgument(rule,weights,orderString,ruleDesc);
		    	productModel.setRuleId(s.getId());
		    	flag =  productService.editSubject(productModel);
	    	}
	    }
	    if(rule.getScope()==2){
	    	flag= setArguments(rule.getId(),weights,orderString,ruleDesc);
	    }
    }
    return flag;
  }

  //将实现排序的方法提取出来
  private  ScoreRuleModel setArgument(ScoreRuleModel scoreRule,String weights,String orderString,String ruleDesc) {
    
    Map<String, RuleInfo> ruleInfoMap = new TreeMap<String, RuleInfo>();
    
    if (null!=scoreRule && null!= scoreRule.getRuleDetail() && scoreRule.getRuleDetail().trim().length()>0) {
      scoreRule.initModuleInfo(scoreRule.getRuleDetail());
       ruleInfoMap = scoreRule.getRuleInfoMap();
    }
    
    if (weights!=null &&weights.trim().length()>0) {
      String[] weightList = weights.split(";");
      for (String weightItem : weightList) {
        String[] split = weightItem.split(":");
        String index = split[0];      
        RuleInfo ruleInfo = ruleInfoMap.get(index);
        Double weight = Double.valueOf(split[1]);
        ruleInfo.setWeight(weight);
      }
    }
    
    if (orderString!=null && orderString.trim().length()>0) {
      String[] orderList = orderString.split(";");
      for (String orderItem : orderList) {
        String[] split = orderItem.split(":");
        String index = split[0];      
        RuleInfo ruleInfo = ruleInfoMap.get(index);
        Integer order = Integer.valueOf(split[1]);
        ruleInfo.setOrder(order);
      }
    }
    
    List<RuleInfo> jsonList = new ArrayList<RuleInfo>();
    for (String key : ruleInfoMap.keySet()) {
      RuleInfo ruleInfo = ruleInfoMap.get(key);
      jsonList.add(ruleInfo);
    }
    String json = JSON.toJSONString(jsonList);  
    scoreRule.setId(UUID.randomUUID().toString());
    scoreRule.setRuleDetail(json);//ruleDetail
    scoreRule.setRuleDesc(ruleDesc);//ruleDesc
    return  scoreRuleService.addRule(scoreRule);
  }
}
