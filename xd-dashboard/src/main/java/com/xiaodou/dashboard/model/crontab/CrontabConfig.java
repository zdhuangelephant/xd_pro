package com.xiaodou.dashboard.model.crontab;

import java.sql.Timestamp;

import lombok.Data;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * @name @see com.xiaodou.dashboard.model.crontab.CrontabConfig.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月3日
 * @description 任务定义模型类
 * @version 1.0
 */
@Data
public class CrontabConfig {
	/** id 主键ID */
	@Column(isMajor = true, autoIncrement = true)
	@JSONField(name = "configId")
	private Integer id;
	/** businessCode 业务模块 */
	@Column(listValue = true, canUpdate = false)
	private String businessCode;
	/** crontExpression 调度策略 */
	private String crontExpression;
	/** crontProtocol 调度协议 */
	private String crontProtocol;
	/** crontTarget 调度地址 */
	private String crontTarget;
	/** crontTimeOut 整体超时 */
	private Integer crontTimeOut;
	/** crontRetryTime 失败重试 */
	private Short crontRetryTime;
	/** protocolRetryTimes 协议重试次数 */
	private Integer protocolRetryTimes;
	/** protocolTimeOut 协议超时时间 */
	private Integer protocolTimeOut;
	/** protocolStructCheck 协议格式检查 */
	private Integer protocolStructCheck;
	/** protocolConfig 协议配置项信息 */
	private String protocolConfig;
	/** version 版本号 */
	private String version;
	/** inUse 启用状态 */
	private Short inUse;
	/** createTime 创建时间 */
	@Column(betweenScope = true, canUpdate = false)
	private Timestamp createTime;
	/** updateTime 更新时间 */
	@Column(betweenScope = true)
	private Timestamp updateTime;
	/** userGroup 所属用户组 */
	private Integer userGroup;
	/** owner 创建者 */
	@Column(canUpdate = false)
	private Integer owner;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(CrontabConfig.class, "xd_crontab_config",
				"src/main/resources/conf/mybatis/crontab").buildXml();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getCrontExpression() {
		return crontExpression;
	}

	public void setCrontExpression(String crontExpression) {
		this.crontExpression = crontExpression;
	}

	public String getCrontProtocol() {
		return crontProtocol;
	}

	public void setCrontProtocol(String crontProtocol) {
		this.crontProtocol = crontProtocol;
	}

	public String getCrontTarget() {
		return crontTarget;
	}

	public void setCrontTarget(String crontTarget) {
		this.crontTarget = crontTarget;
	}

	public Integer getCrontTimeOut() {
		return crontTimeOut;
	}

	public void setCrontTimeOut(Integer crontTimeOut) {
		this.crontTimeOut = crontTimeOut;
	}

	public Short getCrontRetryTime() {
		return crontRetryTime;
	}

	public void setCrontRetryTime(Short crontRetryTime) {
		this.crontRetryTime = crontRetryTime;
	}

	public Integer getProtocolRetryTimes() {
		return protocolRetryTimes;
	}

	public void setProtocolRetryTimes(Integer protocolRetryTimes) {
		this.protocolRetryTimes = protocolRetryTimes;
	}

	public Integer getProtocolTimeOut() {
		return protocolTimeOut;
	}

	public void setProtocolTimeOut(Integer protocolTimeOut) {
		this.protocolTimeOut = protocolTimeOut;
	}

	public String getProtocolConfig() {
		return protocolConfig;
	}

	public void setProtocolConfig(String protocolConfig) {
		this.protocolConfig = protocolConfig;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Short getInUse() {
		return inUse;
	}

	public void setInUse(Short inUse) {
		this.inUse = inUse;
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

	public Integer getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(Integer userGroup) {
		this.userGroup = userGroup;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public Integer getProtocolStructCheck() {
		return protocolStructCheck;
	}

	public void setProtocolStructCheck(Integer protocolStructCheck) {
		this.protocolStructCheck = protocolStructCheck;
	}

}
