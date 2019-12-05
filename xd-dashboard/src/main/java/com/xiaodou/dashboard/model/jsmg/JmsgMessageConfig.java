package com.xiaodou.dashboard.model.jsmg;

import java.util.ArrayList;
import java.util.List;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

public class JmsgMessageConfig {
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
	private String useDelayRetry;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private int delayTime;
	private int maxRetryCount;
	
	public List<JmsgMessageConsumersConfig> consumers;
	
	public JmsgMessageConfig(){
		consumers = new ArrayList<JmsgMessageConsumersConfig>();
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
	public String getUseDelayRetry() {
		return useDelayRetry;
	}
	public void setUseDelayRetry(String useDelayRetry) {
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
	    MybatisXmlTool.getInstance(JmsgMessageConfig.class, "message_config",
	            "E:/work3/xd-dashboard/src/main/resources/conf/mybatis/jmsg/")
	            .buildXml();
	}
}
