package com.xiaodou.jmsgauto.model;

import java.sql.Timestamp;
import java.util.Date;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;
/**
 * @name @see com.xiaodou.jmsg.server.model.MessageLog.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description 消息日志
 * @version 1.0
 */
public class MessageLog {
	/** id 主键 */
	@Column(isMajor = true)
	@GeneralField
	private String messageId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String contextId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String customTag;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String messageName;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Timestamp messageSendTime;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Timestamp messageReceiveTime;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private int processResult;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String processServerName;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String processServerIp;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private int processType;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String consumerFullName;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private int processTimeSpan;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String processLog;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Timestamp beginProcessTime;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Timestamp endProcessTime;	
	private String surfix;
	public String getCustomTag() {
		return customTag;
	}
	public void setCustomTag(String customTag) {
		this.customTag = customTag;
	}
	public String getMessageName() {
		return messageName;
	}
	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}
	public Date getMessageSendTime() {
		return messageSendTime;
	}
	public void setMessageSendTime(Timestamp messageSendTime) {
		this.messageSendTime = messageSendTime;
	}
	public Date getMessageReceiveTime() {
		return messageReceiveTime;
	}
	public void setMessageReceiveTime(Timestamp messageReceiveTime) {
		this.messageReceiveTime = messageReceiveTime;
	}
	public int getProcessResult() {
		return processResult;
	}
	public void setProcessResult(int processResult) {
		this.processResult = processResult;
	}
	public String getProcessServerName() {
		return processServerName;
	}
	public void setProcessServerName(String processServerName) {
		this.processServerName = processServerName;
	}
	public int getProcessType() {
		return processType;
	}
	public void setProcessType(int processType) {
		this.processType = processType;
	}
	public String getConsumerFullName() {
		return consumerFullName;
	}
	public void setConsumerFullName(String consumerFullName) {
		this.consumerFullName = consumerFullName;
	}
	public int getProcessTimeSpan() {
		return processTimeSpan;
	}
	public void setProcessTimeSpan(int processTimeSpan) {
		this.processTimeSpan = processTimeSpan;
	}
	public String getProcessLog() {
		return processLog;
	}
	public void setProcessLog(String processLog) {
		this.processLog = processLog;
	}
	public Date getBeginProcessTime() {
		return beginProcessTime;
	}
	public void setBeginProcessTime(Timestamp beginProcessTime) {
		this.beginProcessTime = beginProcessTime;
	}
	public Date getEndProcessTime() {
		return endProcessTime;
	}
	public void setEndProcessTime(Timestamp endProcessTime) {
		this.endProcessTime = endProcessTime;
	}
	public static void main(String[] args) {
	    MybatisXmlTool.getInstance(MessageLog.class, "message_log${input.surfix}",
	            "E:/work1/xiaodou-jmsg-server/src/main/resources/conf/mybatis/")
	            .buildXml();
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getContextId() {
		return contextId;
	}
	public void setContextId(String contextId) {
		this.contextId = contextId;
	}
	public String getProcessServerIp() {
		return processServerIp;
	}
	public void setProcessServerIp(String processServerIp) {
		this.processServerIp = processServerIp;
	}
	public String getSurfix() {
		return surfix;
	}
	public void setSurfix(String surfix) {
		this.surfix = surfix;
	}
}
