package com.xiaodou.oms.entity;

/**
 * Do something after invoking a method in some Protocol object 
 * for an inbound asynchronous notification.
 * 
 * @author Administrator
 *
 */
public class InvokeResult {
	
	String methodName; //name for the method that should be invoked sequentially following processing in Protocol.
    Object retMessage; //message returned by said Protocol method.
    
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Object getRetMessage() {
		return retMessage;
	}
	public void setRetMessage(Object retMessage) {
		this.retMessage = retMessage;
	}
    
    
}
