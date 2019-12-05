package com.xiaodou.autotest.web.model.template;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.annotations.Xml;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

@Xml(tableName = "xd_autotest_template_testcase", outputDir = "src\\main\\resources\\conf\\mybatis\\template")
@Data
public class TestCaseDetail {
	/** id 主键id */
	@Column(isMajor = true, betweenScope = true, persistent = true)
	private Long id;
	/** 外键ID **/
	@Column(canUpdate = true, sortBy = true, listValue = true)
	private Long groupId;
	/** name 条目名 */
	private String name; // ActionConstant.DEFAULT_TEST_CASE_NAME
	/** testKey 测试键 */
	private String testKey;
	/** dataType 数据类型 */
	private String dataType; // DataType.sTring
	/** testValue 测试值 */
	private String testValue;

	/** 创建时间 **/
	@Column(canUpdate = false, sortBy = true)
	private Timestamp createTime;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(TestCaseDetail.class).buildXml();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTestKey() {
		return testKey;
	}

	public void setTestKey(String testKey) {
		this.testKey = testKey;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getTestValue() {
		return testValue;
	}

	public void setTestValue(String testValue) {
		this.testValue = testValue;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
}
