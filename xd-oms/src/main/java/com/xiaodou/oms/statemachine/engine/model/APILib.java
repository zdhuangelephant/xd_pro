package com.xiaodou.oms.statemachine.engine.model;

import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.oms.exception.ExceptionMessageProp;
import com.xiaodou.oms.statemachine.engine.model.api.LocalAPI;
import com.xiaodou.oms.statemachine.engine.model.api.RemoteAPI;

public class APILib {

  private Map<String, ProductLine> productLineMap;

  private OmsOrder omsOrder;

  private ParameterTemplate parameterTemplate;

  public APILib() {
    this.productLineMap = Maps.newHashMap();
    this.omsOrder = new OmsOrder();
    this.parameterTemplate = new ParameterTemplate();
  }

  public Map<String, ProductLine> getProductLineMap() {
    return productLineMap;
  }

  public void setProductLineMap(Map<String, ProductLine> productLine) {
    this.productLineMap = productLine;
  }

  public OmsOrder getOmsOrder() {
    return omsOrder;
  }

  public void setOmsOrder(OmsOrder elongOrder) {
    this.omsOrder = elongOrder;
  }

  public ParameterTemplate getParameterTemplate() {
    return parameterTemplate;
  }

  public void setParameterTemplate(ParameterTemplate parameterTemplate) {
    this.parameterTemplate = parameterTemplate;
  }

  public void registeProductLine(String name, ProductLine productLine) {
    if(null==productLineMap)this.productLineMap = Maps.newHashMap();
    if (productLineMap.containsKey(name))
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(
          "error.doc.loaddoc.productline.same", name));
    productLineMap.put(name, productLine);
  }
  
  public ProductLine getProductLine(String name){
    if(null==productLineMap)return null;
    return productLineMap.get(name);
  }
  
  public LocalAPI getLocalApi(String localApiName){
    if(null==omsOrder)return null;
    return omsOrder.getLocalAPI(localApiName);
  }
  
  public RemoteAPI getRemoteApi(String productLine, String remoteApiName){
    if(null==productLineMap)return null;
    ProductLine productLine2 = productLineMap.get(productLine);
    if(null==productLine2)return null;
    return productLine2.getRemoteAPI(remoteApiName);
  }
  
  public Template getTemplate(String templateName){
    if(null==parameterTemplate)return null;
    return parameterTemplate.getTemplate(templateName);
  }
  
  public boolean hasProductLine(String name){
    if(null==productLineMap)return false;
    return productLineMap.containsKey(name);
  }

}
