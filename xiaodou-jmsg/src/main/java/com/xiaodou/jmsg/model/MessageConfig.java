package com.xiaodou.jmsg.model;

import java.util.ArrayList;
import java.util.List;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;
/**
 * @name @see com.xiaodou.jmsg.model.MessageConfig.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description 消息config
 * @version 1.0
 */
public class MessageConfig {
	/** id 主键 */
	@Column(isMajor = true)
	@GeneralField
	private String id;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String messageName;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String exchangeName;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String priority;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private boolean useDelayRetry;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private int delayTime;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private int maxRetryCount;
	
	public List<MessageConsumersConfig> consumers;
	
	public MessageConfig(){
		consumers = new ArrayList<MessageConsumersConfig>();
	}
	
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
	public boolean isUseDelayRetry() {
		return useDelayRetry;
	}
	public void setUseDelayRetry(boolean useDelayRetry) {
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
	public static void main(String[] args) {
	    MybatisXmlTool.getInstance(MessageConfig.class, "message_config",
	            "E:/work1/xiaodou-jmsg/src/main/resources/conf/mybatis/")
	            .buildXml();
	}

}
