package com.xiaodou.jmsgauto.model.sqlite;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

public class FailMessage {

	@GeneralField
	@Column(canUpdate = true, sortBy = false,isMajor = true)
	private String customTag;
	//DefaultMessage 对象
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String message;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String uniqueUrl;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String messageState;
	@GeneralField
	@Column(canUpdate = true, sortBy = true)
	private Timestamp createTime;
	@GeneralField
	@Column(canUpdate = true, sortBy = true)
	private Timestamp updateTime;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUniqueUrl() {
		return uniqueUrl;
	}
	public void setUniqueUrl(String uniqueUrl) {
		this.uniqueUrl = uniqueUrl;
	}
	public String getCustomTag() {
		return customTag;
	}
	public void setCustomTag(String customTag) {
		this.customTag = customTag;
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
	public String getMessageState() {
		return messageState;
	}
	public void setMessageState(String messageState) {
		this.messageState = messageState;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public static void main(String[] args) {
	    MybatisXmlTool.getInstance(FailMessage.class, "fail_message",
	            "E:/work1/xd-jmsg-server/src/main/resources/conf/mybatis/")
	            .buildXml();
	}
}
