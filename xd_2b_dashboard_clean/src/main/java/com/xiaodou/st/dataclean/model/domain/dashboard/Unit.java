package com.xiaodou.st.dataclean.model.domain.dashboard;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * @name @see com.xiaodou.st.dataclean.model.domain.dashboard.Unit.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月31日
 * @description 单位模型
 * @version 1.0
 */
@Data
public class Unit {

	@Column(isMajor = true)
	private Integer id;
	private String unitName;
	private String unitPortrait;
	private Short role;
	private Timestamp createTime;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(Unit.class, "xd_dashboard_unit",
				"src/main/resources/conf/mybatis/dashboard/").buildXml();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitPortrait() {
		return unitPortrait;
	}

	public void setUnitPortrait(String unitPortrait) {
		this.unitPortrait = unitPortrait;
	}

	public Short getRole() {
		return role;
	}

	public void setRole(Short role) {
		this.role = role;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
