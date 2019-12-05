package com.xiaodou.jmsgauto.model;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;
/**
 * @name @see com.xiaodou.jmsg.server.model.MessageQueueSetting.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description queue信息
 * @version 1.0
 */
public class MessageQueueSetting {
	/** id 主键 */
	@Column(isMajor = true)
	@GeneralField
	private String id;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String queueName;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private int qos;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private int parallelCount;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Timestamp updateTime;
	
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public int getQos() {
		return qos;
	}
	public void setQos(int qos) {
		this.qos = qos;
	}
	public int getParallelCount() {
		return parallelCount;
	}
	public void setParallelCount(int parallelCount) {
		this.parallelCount = parallelCount;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public static void main(String[] args) {
	    MybatisXmlTool.getInstance(MessageQueueSetting.class, "message_queue_setting",
	            "E:/work1/xiaodou-jmsg-server/src/main/resources/conf/mybatis/")
	            .buildXml();
	}
	
}
