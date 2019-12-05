package com.xiaodou.st.dataclean.model.domain.raw;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

/**
 * @name TransferUserExamTotalData
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月28日
 * @description 用户总得分
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RawDataExamTotalModel extends RawTransferField {
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

	// 专业码值
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String typeCode;

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
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer totalQues = 0;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer totalRank = 0;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer rightQues = 0;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer rightRank;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String rightPercent;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Double score;

	/* 章 */
	private String chapterNodeList;
	/* 卷子 */
	private String finalExamNodeList;
	/* 章平均分 */
	private Double avgChapterScore;
	/* 期末成绩平均分 */
	private Double finalExamScore;
	/* 阶段测评成绩 */
	private Double stageEvaluationScore;
	/* 任务分数 */
	private Double missionFinishScore;
	/*查漏补缺分数*/
	private Double supplementScore;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(RawDataExamTotalModel.class,
				"xd_raw_data_exam_total",
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

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
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

	public Integer getTotalQues() {
		return totalQues;
	}

	public void setTotalQues(Integer totalQues) {
		this.totalQues = totalQues;
	}

	public Integer getTotalRank() {
		return totalRank;
	}

	public void setTotalRank(Integer totalRank) {
		this.totalRank = totalRank;
	}

	public Integer getRightQues() {
		return rightQues;
	}

	public void setRightQues(Integer rightQues) {
		this.rightQues = rightQues;
	}

	public Integer getRightRank() {
		return rightRank;
	}

	public void setRightRank(Integer rightRank) {
		this.rightRank = rightRank;
	}

	public String getRightPercent() {
		return rightPercent;
	}

	public void setRightPercent(String rightPercent) {
		this.rightPercent = rightPercent;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getChapterNodeList() {
		return chapterNodeList;
	}

	public void setChapterNodeList(String chapterNodeList) {
		this.chapterNodeList = chapterNodeList;
	}

	public String getFinalExamNodeList() {
		return finalExamNodeList;
	}

	public void setFinalExamNodeList(String finalExamNodeList) {
		this.finalExamNodeList = finalExamNodeList;
	}

	public Double getAvgChapterScore() {
		return avgChapterScore;
	}

	public void setAvgChapterScore(Double avgChapterScore) {
		this.avgChapterScore = avgChapterScore;
	}

	public Double getFinalExamScore() {
		return finalExamScore;
	}

	public void setFinalExamScore(Double finalExamScore) {
		this.finalExamScore = finalExamScore;
	}

	public Double getStageEvaluationScore() {
		return stageEvaluationScore;
	}

	public void setStageEvaluationScore(Double stageEvaluationScore) {
		this.stageEvaluationScore = stageEvaluationScore;
	}

	public Double getMissionFinishScore() {
		return missionFinishScore;
	}

	public void setMissionFinishScore(Double missionFinishScore) {
		this.missionFinishScore = missionFinishScore;
	}

	public Double getSupplementScore() {
		return supplementScore;
	}

	public void setSupplementScore(Double supplementScore) {
		this.supplementScore = supplementScore;
	}
	
}
