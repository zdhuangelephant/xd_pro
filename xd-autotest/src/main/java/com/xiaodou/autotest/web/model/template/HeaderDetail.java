package com.xiaodou.autotest.web.model.template;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.annotations.Xml;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

@Xml(tableName = "xd_autotest_template_header", outputDir = "src\\main\\resources\\conf\\mybatis\\template")
@Data
public class HeaderDetail {
	/** id 主键id */
	@Column(isMajor = true, betweenScope = true, persistent = true)
	private Long id;
	/** 外键ID **/
	@Column(canUpdate = true, sortBy = true, listValue = true)
	private Long groupId;
	/** 模版键 **/
	@Column(canUpdate = true, sortBy = true)
	private String key;
	/** 模版值 **/
	@Column(canUpdate = true, sortBy = true)
	private String value;
	/** 创建时间 **/
	@Column(canUpdate = false, sortBy = true)
	private Timestamp createTime;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(HeaderDetail.class,
				"xd_autotest_template_header").buildXml();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
