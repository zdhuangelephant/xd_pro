package com.xiaodou.st.dataclean.model.domain.raw;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name @see
 *       com.xiaodou.st.dataclean.model.domain.raw.RawDataFinishMissionModel
 *       .java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月5日
 * @description 完成任务数据模型
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RawDataFinishMissionModel extends RawTransferField {

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

	/** recordTime 记录时间 */
	@GeneralField
	@Column(canUpdate = false, sortBy = false)
	private String recordTime;

	/** createTime 创建时间 */
	@GeneralField
	@Column(betweenScope = true)
	private Timestamp createTime;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(RawDataFinishMissionModel.class,
				"xd_raw_data_finish_mission",
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

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
