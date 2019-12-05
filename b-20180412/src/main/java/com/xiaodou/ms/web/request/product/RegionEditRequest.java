package com.xiaodou.ms.web.request.product;

import com.xiaodou.ms.web.request.BaseRequest;

public class RegionEditRequest extends BaseRequest{

  private Long id;
 
  private String module;

  private String moduleName;

  private String detail="";
  
  private String ruleId;
  
  private Integer listOrder=0;
  
  private Integer firstChoice;
  
  private Integer showStatus;

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
  
  public String getRuleId() {
    return ruleId;
  }

  public void setRuleId(String ruleId) {
    this.ruleId = ruleId;
  }
}
