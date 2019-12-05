package com.xiaodou.dashboard.model.crontmonitor;

import java.sql.Timestamp;
import java.util.UUID;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.annotations.Xml;

/**
 * @name @see com.xiaodou.crontmonitor.model.MonitorApiLog.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月18日
 * @description 监控项日式
 * @version 1.0
 */
@Data
@Xml(tableName = "xd_cront_monitor_api_log", outputDir = "")
public class MonitorApiLog {
	public MonitorApiLog() {
		this.id = UUID.randomUUID().toString();
	}

	@Column(isMajor = true)
	/** id 主键ID */
	private String id;
	/** apiId apiId */
	private String apiId;
	/** result 执行结果 */
	private String result;
	/** message 异常消息 */
	private String message;
	@Column(betweenScope = true)
	/** createTime 创建时间 */
	private Timestamp createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
