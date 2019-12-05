package com.xiaodou.st.dataclean.model.domain.raw;

import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.st.dataclean.constant.Constant;

/**
 * @name @see
 *       com.xiaodou.st.dataclean.model.domain.raw.RawDataFaceRecognition.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月31日
 * @description 人脸识别记录
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RawDataFaceRecognitionModel extends RawTransferField {
	/** id 主键ID */
	@Column(isMajor = true, autoIncrement = true)
	private Integer id;
	/** testId 试卷ID */
	@Column(canUpdate = false)
	private String testId;
	/** userId 用户ID */
	@Column(canUpdate = false)
	private String userId;
	/** studentId 学生ID */
	@Column(canUpdate = false)
	private Integer studentId;
	/** catName 专业名称 */
	@Column(canUpdate = false)
	private String catName;
	/** productName 产品名称 */
	@Column(canUpdate = false)
	private String productName;
	/** taughtUnitId 自考办ID */
	@Column(canUpdate = false)
	private Integer taughtUnitId;
	// 主考院校Id
	@Column(canUpdate = false)
	private Integer chiefUnitId;
	// 试点单位Id
	@Column(canUpdate = false)
	private Integer pilotUnitId;
	/** testPoint 测验点名称 */
	@Column(canUpdate = false)
	private String testPoint;
	/** collectTime 记录时间 */
	@Column(canUpdate = false)
	private Timestamp collectTime;
	/** collectPortrait 头像地址 */
	@Column(canUpdate = false)
	private String collectPortrait;
	/** similarity 相似度 */
	@Column(canUpdate = false)
	private Double similarity;
	/** result 比较结果 */
	@Column(canUpdate = false)
	private Short result = Constant.IS_NOT_SELF;
	/** deviceId 登录设备ID */
	private String deviceId;
	/** createTime 创建时间 */
	@Column(canUpdate = false, betweenScope = true)
	private Timestamp createTime;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(RawDataFaceRecognitionModel.class,
				"xd_raw_data_face_recognition",
				"src/main/resources/conf/mybatis/raw").buildXml();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getTestPoint() {
		return testPoint;
	}

	public void setTestPoint(String testPoint) {
		this.testPoint = testPoint;
	}

	public Timestamp getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Timestamp collectTime) {
		this.collectTime = collectTime;
	}

	public String getCollectPortrait() {
		return collectPortrait;
	}

	public void setCollectPortrait(String collectPortrait) {
		this.collectPortrait = collectPortrait;
	}

	public Double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(Double similarity) {
		this.similarity = similarity;
	}

	public Short getResult() {
		return result;
	}

	public void setResult(Short result) {
		this.result = result;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
