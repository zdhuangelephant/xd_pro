package com.xiaodou.dashboard.vo.jmsg.request;

public class MessageConfigRequest {

	private String id;
	private String messageName;
	private String exchangeName;
	private String priority;
	private String useDelayRetry;
	private int delayTime;
	private int maxRetryCount;
	

	public String getMessageName() {
		return messageName;
	}
	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}
	public String getExchangeName() {
		return exchangeName;
	}
	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getUseDelayRetry() {
		return useDelayRetry;
	}
	public void setUseDelayRetry(String useDelayRetry) {
		this.useDelayRetry = useDelayRetry;
	}
	public int getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
	}
	public int getMaxRetryCount() {
		return maxRetryCount;
	}
	public void setMaxRetryCount(int maxRetryCount) {
		this.maxRetryCount = maxRetryCount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}	
	
}
