package com.xiaodou.ms.web.request.product;

import com.xiaodou.ms.web.request.BaseRequest;

/**
 * @name RegionCreateRequest 
 * CopyRright (c) 2018 by <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年4月12日
 * @description TODO
 * @version 1.0
 */
public class RegionCreateRequest  extends BaseRequest  {
 
  private String module;

  private String moduleName;

  private String detail="";
  
  private Integer listOrder=0;

  private Integer firstChoice;
  
  private Integer showStatus;
  
  private String ruleId;

  public String getRuleId() {
    return ruleId;
  }

  public void setRuleId(String ruleId) {
    this.ruleId = ruleId;
  }

  public Integer getFirstChoice() {
    return firstChoice;
  }

  public void setFirstChoice(Integer firstChoice) {
    this.firstChoice = firstChoice;
  }
  
  public Integer getShowStatus() {
    return showStatus;
  }
  
  public void setShowStatus(Integer showStatus) {
    this.showStatus = showStatus;
  }

  public Integer getListOrder() {
    return listOrder;
  }

  public void setListOrder(Integer listOrder) {
    this.listOrder = listOrder;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getModuleName() {
    return moduleName;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }
}
