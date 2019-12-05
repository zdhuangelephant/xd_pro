package com.xiaodou.oms.statemachine.engine.instance;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.oms.exception.ExceptionMessageProp;

public class StateMachineConf {

  private Map<String, StateMachineProductLineConf> stateMachineProductLineConfMap =
      new HashMap<String, StateMachineProductLineConf>();

  public void setStateMachineProductLineConfMap(
      Map<String, StateMachineProductLineConf> stateMachineProductLineConfMap) {
    this.stateMachineProductLineConfMap = stateMachineProductLineConfMap;
  }

  public Map<String, StateMachineProductLineConf> getStateMachineProductLineConfMap() {
    return stateMachineProductLineConfMap;
  }
  
  public void setStateMachineProductLineConf(String key, StateMachineProductLineConf productLineConf){
    if(null==stateMachineProductLineConfMap)stateMachineProductLineConfMap=Maps.newHashMap();
    if(stateMachineProductLineConfMap.containsKey(key))
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(
        "error.doc.loaddoc.statemachineinstance.same", key));
    stateMachineProductLineConfMap.put(key, productLineConf);
  }
  
  public StateMachineProductLineConf getProductLineConf(String key){
    if(null==stateMachineProductLineConfMap)return null;
    return stateMachineProductLineConfMap.get(key);
  }
  
  public boolean hasProductLineConf(String key){
    if(null==stateMachineProductLineConfMap)return false;
    return stateMachineProductLineConfMap.containsKey(key);
    
  }

}
