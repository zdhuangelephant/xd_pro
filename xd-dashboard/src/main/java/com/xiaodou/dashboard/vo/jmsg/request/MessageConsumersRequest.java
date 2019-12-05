package com.xiaodou.dashboard.vo.jmsg.request;

public class MessageConsumersRequest {
    private String id;

	private String messageName;

	private String url;

	private int timeOut;
	
	public String getMessageName() {
		return messageName;
	}
	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
