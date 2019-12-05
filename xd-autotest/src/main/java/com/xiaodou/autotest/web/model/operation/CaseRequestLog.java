package com.xiaodou.autotest.web.model.operation;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.autotest.core.ActionConstant;
import com.xiaodou.common.annotation.GeneralField;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name @see com.xiaodou.autotest.web.model.operation.RequestLog.java
 * @CopyRright (c) 2017 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年8月15日
 * @description
 * @version 1.0
 */
public class CaseRequestLog {
	/** 主键 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false, isMajor = true)
	private String id;
	/** 名称 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String name;
	/** 详情 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String caseDesc;
	/** 默认方法 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String method=ActionConstant.DEFAULT_METHOD.toString();
	/** params */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String params;
	/** 1 http 2https 3thrift 协议类型 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String protocol=ActionConstant.DEFAULT_PROTOCOL.toString();
	/** 请求url */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String url;
	/** 格式*/
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String format=ActionConstant.DEFAULT_FORMAT.toString();
	/** 前置校验条件 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String preConds;
	/** 前置设置列表 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String preSets;
	/** 测试条目列表 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String testCases;
	/** 后置设置列表 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String afterSets;
	/** 用户ID */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String userId;
	/** 上级ID */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String collectionId;
	/** 创建时间 */
	@GeneralField
	@Column(canUpdate = true, sortBy = true)
	private Timestamp createTime;
	/** 更新时间 */
	@GeneralField
	@Column(canUpdate = true, sortBy = true)
	private Timestamp updateTime;


	public String getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}

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

	public String getCaseDesc() {
		return caseDesc;
	}

	public void setCaseDesc(String caseDesc) {
		this.caseDesc = caseDesc;
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

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getPreConds() {
		return preConds;
	}

	public void setPreConds(String preConds) {
		this.preConds = preConds;
	}

	public String getPreSets() {
		return preSets;
	}

	public void setPreSets(String preSets) {
		this.preSets = preSets;
	}

	public String getTestCases() {
		return testCases;
	}

	public void setTestCases(String testCases) {
		this.testCases = testCases;
	}

	public String getAfterSets() {
		return afterSets;
	}

	public void setAfterSets(String afterSets) {
		this.afterSets = afterSets;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	@Override
	public String toString() {
		return FastJsonUtil.toJson(this);
	}

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(CaseRequestLog.class, "case_request_log",
				"E:/work3/autotest/src/main/resources/conf/mybatis/")
				.buildXml();
	}
}
