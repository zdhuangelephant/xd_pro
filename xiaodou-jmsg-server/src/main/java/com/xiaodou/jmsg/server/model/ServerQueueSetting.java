package com.xiaodou.jmsg.server.model;

import java.sql.Timestamp;
import java.util.Map;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;
/**
 * @name @see com.xiaodou.jmsg.server.model.ServerQueueSetting.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description serverName检测表
 * @version 1.0
 */
public class ServerQueueSetting {
	/** id 主键 */
	@Column(isMajor = true)
	@GeneralField
	private String id;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String serverName;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private boolean isEnable;
	private Map<String, MessageQueueSetting> queueSettings;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Timestamp updateTime;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private int groupId;

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	
	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Map<String, MessageQueueSetting> getQueueSettings() {
		return queueSettings;
	}

	public void setQueueSettings(Map<String, MessageQueueSetting> queueSettings) {
		this.queueSettings = queueSettings;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static void main(String[] args) {
		MybatisXmlTool
				.getInstance(ServerQueueSetting.class, "message_server_node",
						"E:/work1/xiaodou-jmsg-server/src/main/resources/conf/mybatis/")
				.buildXml();
	}
}
