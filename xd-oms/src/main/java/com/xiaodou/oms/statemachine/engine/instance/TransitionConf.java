package com.xiaodou.oms.statemachine.engine.instance;

import java.util.ArrayList;
import java.util.List;

import com.xiaodou.oms.statemachine.engine.model.api.proxy.IApiProxy;


public class TransitionConf {
     private String name;
     private List<IApiProxy> preCheckList = new ArrayList<IApiProxy>();
     private List<IApiProxy> doList = new ArrayList<IApiProxy>();
     private List<IApiProxy> recordMessage = new ArrayList<IApiProxy>();
     private List<IApiProxy> message = new ArrayList<IApiProxy>();
     
	public List<IApiProxy> getRecordMessage() {
      return recordMessage;
    }

    public void setRecordMessage(List<IApiProxy> recordMessage) {
      this.recordMessage = recordMessage;
    }

  public List<IApiProxy> getMessage() {
      return message;
    }

    public void setMessage(List<IApiProxy> message) {
      this.message = message;
    }

    public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public List<IApiProxy> getPreCheckList() {
		return preCheckList;
	}
	public void setPreCheckList(List<IApiProxy> preCheckList) {
		this.preCheckList = preCheckList;
	}
	public List<IApiProxy> getDoList() {
		return doList;
	}
	public void setDoList(List<IApiProxy> doList) {
		this.doList = doList;
	}
     
}
