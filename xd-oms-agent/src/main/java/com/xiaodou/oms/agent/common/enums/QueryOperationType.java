package com.xiaodou.oms.agent.common.enums;

/**
 * Created by zyp on 14-8-28.
 */
public enum QueryOperationType {

  WRITE("WRITE"), READ("READ"), READWRITE("READWRITE"), DEFAULT("DEFAULT");;

  private String value;

  private QueryOperationType(String value){
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
