package com.xiaodou.oms.statemachine.engine.instance;

import java.util.HashMap;
import java.util.Map;

public class StateMachineProductLineConf {

  private String name;
  private String code;
  private String stateMachineInstance;
  private String stateInstance;
  private String eventInstance;
  private Map<String, TransitionConf> transitionConfMap = new HashMap<String, TransitionConf>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getStateMachineInstance() {
    return stateMachineInstance;
  }

  public void setStateMachineInstance(String stateMachineInstance) {
    this.stateMachineInstance = stateMachineInstance;
  }

  public String getStateInstance() {
    return stateInstance;
  }

  public void setStateInstance(String stateInstance) {
    this.stateInstance = stateInstance;
  }

  public String getEventInstance() {
    return eventInstance;
  }

  public void setEventInstance(String eventInstance) {
    this.eventInstance = eventInstance;
  }

  public Map<String, TransitionConf> getTransitionConfMap() {
    return transitionConfMap;
  }

  public void setTransitionConfMap(Map<String, TransitionConf> transitionConfMap) {
    this.transitionConfMap = transitionConfMap;
  }

}
