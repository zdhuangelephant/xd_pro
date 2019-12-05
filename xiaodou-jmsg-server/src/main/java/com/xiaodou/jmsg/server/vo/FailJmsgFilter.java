package com.xiaodou.jmsg.server.vo;


public class FailJmsgFilter{
	private int urlFailCount=0;
	private long firstFailTime=0L;
	private String state="-1";
	public int getUrlFailCount() {
		return urlFailCount;
	}
	public void AddUrlFailCount() {
		urlFailCount++;
	}
	public void setUrlFailCount(int urlFailCount) {
		this.urlFailCount = urlFailCount;
	}
	public long getFirstFailTime() {
		return firstFailTime;
	}
	public void setFirstFailTime(long firstFailTime) {
		this.firstFailTime = firstFailTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
