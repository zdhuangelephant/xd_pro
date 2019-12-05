package com.xiaodou.dashboard.model.crontmonitor;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.annotations.Xml;

/**
 * @name @see com.xiaodou.crontmonitor.model.MonitorApi.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月15日
 * @description 监控API数据模型
 * @version 1.0
 */
@Data
@Xml(tableName = "xd_cront_monitor_api", outputDir = "")
public class MonitorApi {
	/** uniqueId 唯一ID */
	@Column(isMajor = true)
	private String id;
	/** name 名称 */
	private String name;
	/** protocol 协议类型 */
	private String protocol;
	/** format 数据传输格式 */
	private String format;
	/** host 主机域名 */
	private String url;
	/** method 方法类型 */
	private String method;
	/** params 参数列表 */
	private String params;
	/** timeOut 超时时间 */
	private Integer timeOut;
	/** retryTime 重试次数 */
	private Integer retryTime;
	/** createTime 创建时间 */
	@Column(betweenScope = true)
	private Timestamp createTime;
	/** updateTime 更新时间 */
	@Column(betweenScope = true)
	private Timestamp updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Integer getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}

	public Integer getRetryTime() {
		return retryTime;
	}

	public void setRetryTime(Integer retryTime) {
		this.retryTime = retryTime;
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
