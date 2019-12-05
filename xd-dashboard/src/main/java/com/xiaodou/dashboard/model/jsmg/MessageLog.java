package com.xiaodou.dashboard.model.jsmg;

import java.sql.Timestamp;
import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

public class MessageLog {
	@GeneralField
	@Column(isMajor = true,canUpdate = true, sortBy = false)
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
	private int processResult;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String processServerName;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private int processType;
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
	

	public String getCustomTag() {
		return customTag;
	}
	public void setCustomTag(String customTag) {
		this.customTag = customTag;
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
	public Timestamp getBeginProcessTime() {
		return beginProcessTime;
	}
	public void setBeginProcessTime(Timestamp beginProcessTime) {
		this.beginProcessTime = beginProcessTime;
	}
	public Timestamp getEndProcessTime() {
		return endProcessTime;
	}
	public void setEndProcessTime(Timestamp endProcessTime) {
		this.endProcessTime = endProcessTime;
	}
	  public static void main(String[] args) {
		    MybatisXmlTool.getInstance(MessageLog.class, "message_log${input.surfix}",
		            "E:/work3/xd-dashboard/src/main/resources/conf/mybatis/jmsg/")
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

}
