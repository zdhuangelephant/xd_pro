package com.xiaodou.dashboard.vo.alarm;

import java.util.Date;

import com.xiaodou.dashboard.model.alarm.local.EventPojo;
import com.xiaodou.dashboard.request.AlarmRequestPojo;

/**
 * @name @see com.xiaodou.dashboard.vo.SendInfoVo.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年11月30日
 * @description 发送信息vo
 * @version 1.0
 */
public class SendInfoVo {

	public SendInfoVo() {
	}

	public SendInfoVo(EventPojo event, AlarmRequestPojo pojo) {
		this.event = event;
		this.pojo = pojo;
	}

	private EventPojo event;

	private AlarmRequestPojo pojo;

	private Date createDate;

	public EventPojo getEvent() {
		return event;
	}

	public void setEvent(EventPojo event) {
		this.event = event;
	}

	public AlarmRequestPojo getPojo() {
		return pojo;
	}

	public void setPojo(AlarmRequestPojo pojo) {
		this.pojo = pojo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
