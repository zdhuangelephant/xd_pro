package com.xiaodou.dashboard.model.jsmg;

import java.sql.Timestamp;
import java.util.Map;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

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
	private String enable;
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

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
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
	    MybatisXmlTool.getInstance(ServerQueueSetting.class, "message_server_node",
	            "E:/work3/xd-dashboard/src/main/resources/conf/mybatis/jmsg/")
	            .buildXml();
	}

}
