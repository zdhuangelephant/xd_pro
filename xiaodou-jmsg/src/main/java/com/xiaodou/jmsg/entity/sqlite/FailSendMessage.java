package com.xiaodou.jmsg.entity.sqlite;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

public class FailSendMessage {

	@GeneralField
	@Column(canUpdate = true, sortBy = false,isMajor = true)
	private String customTag;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String message;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String messageName;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private boolean reSend;
	@GeneralField
	@Column(canUpdate = true, sortBy = true)
	private Timestamp createTime;
	@GeneralField
	@Column(canUpdate = true, sortBy = true)
	private Timestamp updateTime;
	
	public static void main(String[] args) {
	    MybatisXmlTool.getInstance(FailSendMessage.class, "fail_send_message",
	            "E:/work1/xd-jmsg/src/main/resources/conf/mybatis/")
	            .buildXml();
	}

	public String getCustomTag() {
		return customTag;
	}

	public void setCustomTag(String customTag) {
		this.customTag = customTag;
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	public boolean isReSend() {
		return reSend;
	}

	public void setReSend(boolean reSend) {
		this.reSend = reSend;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
}
