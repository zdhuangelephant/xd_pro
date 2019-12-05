package com.xiaodou.autotest.web.model.template;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.annotations.Xml;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

@Xml(tableName = "xd_autotest_template_afterset", outputDir = "src\\main\\resources\\conf\\mybatis\\template")
@Data
public class AfterSetDetail {
	/** id 主键id */
	@Column(isMajor = true, betweenScope = true, persistent = true)
	private Long id;
	/** 外键ID **/
	@Column(canUpdate = true, sortBy = true, listValue = true)
	private Long groupId;
	/** resultkey 结果集中键值 */
	private String resultkey;
	/** dataType 数据类型 */
	private String dataType; // DataType.sTring
	/** mappingKey 目标映射键值 */
	private String mappingKey;

	/** 创建时间 **/
	@Column(canUpdate = false, sortBy = true)
	private Timestamp createTime;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(AfterSetDetail.class).buildXml();
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

	public String getResultkey() {
		return resultkey;
	}

	public void setResultkey(String resultkey) {
		this.resultkey = resultkey;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getMappingKey() {
		return mappingKey;
	}

	public void setMappingKey(String mappingKey) {
		this.mappingKey = mappingKey;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
}
