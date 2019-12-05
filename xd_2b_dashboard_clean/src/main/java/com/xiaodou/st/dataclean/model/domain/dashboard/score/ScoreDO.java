package com.xiaodou.st.dataclean.model.domain.dashboard.score;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataExamTotalModel;

/**
 * 
 * @name ScoreDO CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年3月31日
 * @description 不同维度下的。所有学生成绩统计
 * @version 1.0
 */
@Data
public class ScoreDO {
  /* id 主键id */
  @Column(isMajor = true, autoIncrement = true, canUpdate = false, persistent = true, sortBy = true)
  private Long id;
  /* 试点单位id */
  private Integer pilotUnitId;
  /* 试点单位名称 */
  private String pilotUnitName;
  /* 专业id */
  private Integer catId;
  /* 专业代码 */
  private String catCode;
  /* 专业名称 */
  private String catName;
  /* 所在班级id */
  private Long classId;
  /* 班级名称 */
  private String className;
  /* 课程id */
  private Long productId;
  /* 课程代码 */
  private String productCode;
  /* 课程名称 */
  private String productName;
  /* 课程开始时间 */
  private Date beginTime;
  /* 课程结束时间 */
  private Date endTime;
  /* 学生id */
  private Integer studentId;
  /* 姓名 */
  private String studentName;
  /* 准考证号 */
  private String admissionCardCode;
  /* 手机号 */
  private Long telephone;
  /* 头像 */
  private String studentPortrait;
  /* 成绩 */
  private Double score;
  /* 考期 */
  private String examDate;
  /* 登录管理员所在单位角色类型 */
  private Short roleType;
  /* 登录管理员所在单位id */
  private Integer unitId;
  @Column(canUpdate = false, sortBy = true)
  private Timestamp createTime;
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
  /* 查漏补缺分数 */
  private Double supplementScore;

  public static void main(String[] args) {
    MybatisXmlTool
        .getInstance(ScoreDO.class, "xd_dashboard_score",
            "D:/work/workspace_xd/xd_2b_dashboard_clean/src/main/resources/conf/mybatis/dashboard/score/")
        .buildXml();
  }

  public static ScoreDO getInstance(RawDataExamTotalModel rdet) {
    if (null == rdet) return null;
    ScoreDO scoreDO = new ScoreDO();
    if (null != rdet.getStudent()) {
      scoreDO.setAdmissionCardCode(rdet.getStudent().getAdmissionCardCode());
      scoreDO.setClassId(rdet.getStudent().getClassId());
      scoreDO.setClassName(rdet.getStudent().getClassName());
      scoreDO.setStudentId(rdet.getStudent().getId());
      scoreDO.setStudentName(rdet.getStudent().getRealName());
      scoreDO.setStudentPortrait(rdet.getStudent().getPortrait());
      scoreDO.setTelephone(rdet.getStudent().getTelephone());
    }
    if (null != rdet.getCategoryModel()) {
      scoreDO.setCatCode(rdet.getCategoryModel().getTypeCode());
      scoreDO.setCatId(rdet.getCategoryModel().getId());
      scoreDO.setCatName(rdet.getCategoryModel().getName());
    }
    if (null != rdet.getProductModel()) {
      scoreDO.setBeginTime(rdet.getProductModel().getBeginApplyTime());
      scoreDO.setEndTime(rdet.getProductModel().getEndApplyTime());
      scoreDO.setExamDate(rdet.getProductModel().getExamDate());
      scoreDO.setProductCode(rdet.getProductModel().getCourseCode());
      scoreDO.setProductId(rdet.getProductModel().getId());
      scoreDO.setProductName(rdet.getProductModel().getName());
    }
    scoreDO.setPilotUnitId(rdet.getPilotUnitId());
    if (null != rdet.getPioltUnit()) scoreDO.setPilotUnitName(rdet.getPioltUnit().getUnitName());
    scoreDO.setScore(rdet.getScore());
    scoreDO.setChapterNodeList(rdet.getChapterNodeList());
    scoreDO.setFinalExamNodeList(rdet.getFinalExamNodeList());
    scoreDO.setAvgChapterScore(rdet.getAvgChapterScore());
    scoreDO.setFinalExamScore(rdet.getFinalExamScore());
    scoreDO.setStageEvaluationScore(rdet.getStageEvaluationScore());
    scoreDO.setMissionFinishScore(rdet.getMissionFinishScore());
    scoreDO.setSupplementScore(rdet.getSupplementScore());
    return scoreDO;
  }

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Integer getPilotUnitId() {
	return pilotUnitId;
}

public void setPilotUnitId(Integer pilotUnitId) {
	this.pilotUnitId = pilotUnitId;
}

public String getPilotUnitName() {
	return pilotUnitName;
}

public void setPilotUnitName(String pilotUnitName) {
	this.pilotUnitName = pilotUnitName;
}

public Integer getCatId() {
	return catId;
}

public void setCatId(Integer catId) {
	this.catId = catId;
}

public String getCatCode() {
	return catCode;
}

public void setCatCode(String catCode) {
	this.catCode = catCode;
}

public String getCatName() {
	return catName;
}

public void setCatName(String catName) {
	this.catName = catName;
}

public Long getClassId() {
	return classId;
}

public void setClassId(Long classId) {
	this.classId = classId;
}

public String getClassName() {
	return className;
}

public void setClassName(String className) {
	this.className = className;
}

public Long getProductId() {
	return productId;
}

public void setProductId(Long productId) {
	this.productId = productId;
}

public String getProductCode() {
	return productCode;
}

public void setProductCode(String productCode) {
	this.productCode = productCode;
}

public String getProductName() {
	return productName;
}

public void setProductName(String productName) {
	this.productName = productName;
}

public Date getBeginTime() {
	return beginTime;
}

public void setBeginTime(Date beginTime) {
	this.beginTime = beginTime;
}

public Date getEndTime() {
	return endTime;
}

public void setEndTime(Date endTime) {
	this.endTime = endTime;
}

public Integer getStudentId() {
	return studentId;
}

public void setStudentId(Integer studentId) {
	this.studentId = studentId;
}

public String getStudentName() {
	return studentName;
}

public void setStudentName(String studentName) {
	this.studentName = studentName;
}

public String getAdmissionCardCode() {
	return admissionCardCode;
}

public void setAdmissionCardCode(String admissionCardCode) {
	this.admissionCardCode = admissionCardCode;
}

public Long getTelephone() {
	return telephone;
}

public void setTelephone(Long telephone) {
	this.telephone = telephone;
}

public String getStudentPortrait() {
	return studentPortrait;
}

public void setStudentPortrait(String studentPortrait) {
	this.studentPortrait = studentPortrait;
}

public Double getScore() {
	return score;
}

public void setScore(Double score) {
	this.score = score;
}

public String getExamDate() {
	return examDate;
}

public void setExamDate(String examDate) {
	this.examDate = examDate;
}

public Short getRoleType() {
	return roleType;
}

public void setRoleType(Short roleType) {
	this.roleType = roleType;
}

public Integer getUnitId() {
	return unitId;
}

public void setUnitId(Integer unitId) {
	this.unitId = unitId;
}

public Timestamp getCreateTime() {
	return createTime;
}

public void setCreateTime(Timestamp createTime) {
	this.createTime = createTime;
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
