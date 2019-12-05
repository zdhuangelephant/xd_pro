package com.xiaodou.st.dataclean.model.domain.dashboard;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

/**
 * @name @see
 *       com.xiaodou.st.dataclean.model.domain.dashboard.ChiefUnitRelationModel
 *       .java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月31日
 * @description 主考院校与专业关系表
 * @version 1.0
 */
@Data
public class ChiefUnitRelationModel {
	// id
	@Column(isMajor = true)
	@GeneralField
	private Integer id;

	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer unitId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer catId;
	@GeneralField
	@Column(canUpdate = true, sortBy = true)
	private Timestamp createTime;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(ChiefUnitRelationModel.class,
				"xd_dashboard_chief_unit_cat_relation",
				"src/main/resources/conf/mybatis/dashboard/").buildXml();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
