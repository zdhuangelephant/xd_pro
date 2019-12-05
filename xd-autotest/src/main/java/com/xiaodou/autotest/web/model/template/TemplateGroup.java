package com.xiaodou.autotest.web.model.template;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.annotations.Xml;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

import lombok.Data;

@Xml(tableName = "xd_autotest_template_group", outputDir = "src\\main\\resources\\conf\\mybatis\\template")
@Data
public class TemplateGroup {
	/** 主键ID **/
	@GeneralField
	@Column(isMajor = true, listValue = true, betweenScope = true, persistent = true, autoIncrement = true)
	private Long id;

	/**
	 * typeId 模版类型 -1: 未知; 0:header; 1:param; 2:precond; 3:preset; 4:testcase;
	 * 5:afterset
	 */
	@GeneralField
	@Column(canUpdate = false, listValue = true, sortBy = true)
	private Short typeId;

	/** groupName 模版名称 */
	@GeneralField
	@Column(canUpdate = true, sortBy = true)
	private String groupName;

	/** desc 参数描述 */
	@GeneralField
	@Column(canUpdate = true, sortBy = true)
	private String desc;

	/** 创建时间 **/
	@GeneralField
	@Column(canUpdate = false, sortBy = true)
	private Timestamp createTime;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(TemplateGroup.class).buildXml();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Short getTypeId() {
		return typeId;
	}

	public void setTypeId(Short typeId) {
		this.typeId = typeId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
