package com.xiaodou.autotest.web.model.jsfunction;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.annotations.Xml;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

/**
 * @name JSFunction CopyRright (c) 2018 by
 * 
 * @author <a href="mailto:huangzedong@corp.51xiaodou.com">zdhuang</a>
 * @date 2018年1月26日
 * @description TODO
 * @version 1.0
 */
@Data
@Xml(tableName = "xd_autotest_jsfuns", outputDir = "src/main/resources/conf/mybatis/jsfunction")
public class JSFunction {
	@Column(isMajor = true, canUpdate = false, autoIncrement = true, listValue = true, sortBy = true, betweenScope = true)
	private Long id;
	private String funSignature;
	private String functionBody;
	private Integer uid;
	@Column(sortBy = true, betweenScope = true)
	private Timestamp createTime;
	@Column(sortBy = true, betweenScope = true)
	private Timestamp updateTime;
	@Column(persistent = false)
	private String username;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(JSFunction.class).buildXml();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFunSignature() {
		return funSignature;
	}

	public void setFunSignature(String funSignature) {
		this.funSignature = funSignature;
	}

	public String getFunctionBody() {
		return functionBody;
	}

	public void setFunctionBody(String functionBody) {
		this.functionBody = functionBody;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
