package com.xiaodou.autotest.web.model.template;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.annotations.Xml;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

@Xml(tableName = "xd_autotest_template_preset", outputDir = "src\\main\\resources\\conf\\mybatis\\template")
@Data
public class PreSetDetail {
	/** id 主键id */
	@Column(isMajor = true, betweenScope = true, persistent = true)
	private Long id;
	/** 外键ID **/
	@Column(canUpdate = true, sortBy = true, listValue = true)
	private Long groupId;
	/** dataSource 数据源 */
	private String dataSource; // DataSource.Local
	/** scope 影响范围 */
	private String scope; // VarScope.part
	/** key 键 */
	private String key;
	/** dataType 数据类型 */
	private String dataType; // DataType.sTring
	/** assignment 赋值语句 */
	private String assignment;
	/** 创建时间 **/
	@Column(canUpdate = false, sortBy = true)
	private Timestamp createTime;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(PreSetDetail.class).buildXml();
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

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getAssignment() {
		return assignment;
	}

	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
}
