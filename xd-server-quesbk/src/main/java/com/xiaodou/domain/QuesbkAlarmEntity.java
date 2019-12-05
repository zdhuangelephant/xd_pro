package com.xiaodou.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.common.util.log.model.AlarmEntityImpl;
import com.xiaodou.common.util.log.model.IAlarmEntity;

@Data
@EqualsAndHashCode(callSuper = false)
public class QuesbkAlarmEntity extends AlarmEntityImpl {
	/**
	 * 报警类型名称
	 */
	private String eventModule;
	/**
	 * 报警实例名称
	 */
	private String eventName;
	/**
	 * 报警短信内容（短信内容）
	 */
	private String messageInfo;
	/**
	 * 报警邮件信息（邮件内容）
	 */
	private String mailInfo;
	/**
	 * 获取日志报警实体
	 */
	private IAlarmEntity loggerEntity;

	public QuesbkAlarmEntity(String eventModule, String eventName,
			String messageInfo, String mailInfo, IAlarmEntity loggerEntity) {
		super();
		this.eventModule = eventModule;
		this.eventName = eventName;
		this.messageInfo = messageInfo;
		this.mailInfo = mailInfo;
		this.loggerEntity = loggerEntity;
	}

	public String getEventModule() {
		return eventModule;
	}

	public void setEventModule(String eventModule) {
		this.eventModule = eventModule;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getMessageInfo() {
		return messageInfo;
	}

	public void setMessageInfo(String messageInfo) {
		this.messageInfo = messageInfo;
	}

	public String getMailInfo() {
		return mailInfo;
	}

	public void setMailInfo(String mailInfo) {
		this.mailInfo = mailInfo;
	}

	public IAlarmEntity getLoggerEntity() {
		return loggerEntity;
	}

	public void setLoggerEntity(IAlarmEntity loggerEntity) {
		this.loggerEntity = loggerEntity;
	}

}
