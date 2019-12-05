package com.xiaodou.st.dataclean.model.domain.raw;

import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

/**
 * @name @see
 *       com.xiaodou.st.dataclean.model.domain.raw.RawDataFinishAllMissionModel
 *       .java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月5日
 * @description 完成全部任务数据模型
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RawDataFinishAllMissionModel extends RawTransferField {
	// id
	@Column(isMajor = true)
	@GeneralField
	private Integer id;

	// appId
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String moduleId;

	// 用户Id
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String userId;

	// 专业Id
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String productCategoryId;

	// 产品Id
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String productId;

	// 学生Id
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer studentId;

	/** taughtUnitId 自考办ID */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer taughtUnitId;

	// 主考院校Id
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer chiefUnitId;

	// 试点单位Id
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer pilotUnitId;

	/** createTime 创建时间 */
	@GeneralField
	@Column(betweenScope = true)
	private Timestamp createTime;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(RawDataFinishAllMissionModel.class,
				"xd_raw_data_finish_all_mission",
				"src/main/resources/conf/mybatis/raw/").buildXml();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getTaughtUnitId() {
		return taughtUnitId;
	}

	public void setTaughtUnitId(Integer taughtUnitId) {
		this.taughtUnitId = taughtUnitId;
	}

	public Integer getChiefUnitId() {
		return chiefUnitId;
	}

	public void setChiefUnitId(Integer chiefUnitId) {
		this.chiefUnitId = chiefUnitId;
	}

	public Integer getPilotUnitId() {
		return pilotUnitId;
	}

	public void setPilotUnitId(Integer pilotUnitId) {
		this.pilotUnitId = pilotUnitId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
