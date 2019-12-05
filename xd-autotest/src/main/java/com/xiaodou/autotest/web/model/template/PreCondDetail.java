package com.xiaodou.autotest.web.model.template;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.annotations.Xml;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.autotest.core.ActionEnum.ConditionScope;

import lombok.Data;

@Xml(tableName = "xd_autotest_template_precond", outputDir = "src\\main\\resources\\conf\\mybatis\\template")
@Data
public class PreCondDetail {
	/** id 主键id */
	@Column(isMajor = true, betweenScope = true, persistent = true)
	private Long id;
	/** 外键ID **/
	@Column(canUpdate = true, sortBy = true, listValue = true)
	private Long groupId;
	/** key 键 */
	private String key;
	/** condition 条件 */
	private String condition; // ActionConstant.DEFAULT_CONDITION
	/** targetValue 目标值 */
	private String targetValue;
	/** 创建时间 **/
	@Column(canUpdate = false, sortBy = true)
	private Timestamp createTime;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(PreCondDetail.class).buildXml();
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

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
}
