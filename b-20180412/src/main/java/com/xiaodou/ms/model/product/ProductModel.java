package com.xiaodou.ms.model.product;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.common.annotation.BaseField;
import com.xiaodou.common.annotation.GeneralField;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.constants.XdmsConstant;
import com.xiaodou.ms.model.major.MajorCourseInfo;

import lombok.Data;

/**
 * Created by zyp on 15/6/26.
 */
@Data
public class ProductModel {
  // 主键Id
  @Column(isMajor = true)
  @BaseField
  @GeneralField
  private Long id;

  // 栏目Id
  // @BaseField
  private Long productCategoryId;

  // 产品名
  @BaseField
  @GeneralField
  private String name;

  // 简介
  @BaseField
  @GeneralField
  private String briefInfo;

  // 详情
  @BaseField
  @GeneralField
  private String detail;

  // 图片地址
  @BaseField
  @GeneralField
  private String imageUrl;

  // 当前报名人数
  @BaseField
  @GeneralField
  private Integer currApplyCount;

  // 报名人数上线
  @BaseField
  @GeneralField
  private Integer totalApplyCount;

  // 开始申请时间
  @BaseField
  @GeneralField
  private Timestamp beginApplyTime;

  // 申请结束时间
  @BaseField
  @GeneralField
  private Timestamp endApplyTime;

  // 原价
  @BaseField
  @GeneralField
  private BigDecimal originalAmount;

  // 优惠价
  @BaseField
  @GeneralField
  private BigDecimal payAmount;

  // 创建时间
  @BaseField
  @GeneralField
  private Timestamp createTime;

  // 更新时间
  @BaseField
  @GeneralField
  private Timestamp updateTime;

  // misc 杂项
  @BaseField
  @GeneralField
  private String misc;

  // 是否显示
  @BaseField
  @GeneralField
  private Integer showStatus;

  // 点赞数
  @BaseField
  @GeneralField
  private Integer praiseCount;

  // 栏目名称
  @BaseField
  private String categoryName;

  // 产品类型
  @BaseField
  @GeneralField
  private Integer type;

  // 题库设置
  @BaseField
  @GeneralField
  private String questionBankSetting;

  // 资源产品ID
  @BaseField
  @GeneralField
  private Long resourceSubject;

  private String resourceSubjectName;

  // 分析地址
  @BaseField
  @GeneralField
  private String shareUrl;

  /** courseCode 课程代码 */
  @BaseField
  @GeneralField
  private String courseCode;

  /** courseQuality 课程性质 */
  @BaseField
  @GeneralField
  private String courseQuality;

  /** courseCredit 课程学分 */
  @BaseField
  @GeneralField
  private Long courseCredit;

  /** courseCheckType 课程考核方式 */
  @BaseField
  @GeneralField
  private String courseCheckType;

  /** exam_date_type 课程考期类型 0 上半年 1 下半年 2 整年(确定是否可以移动) */
  @BaseField
  @GeneralField
  private Short examDateType;

  /** examDetail 近期考试安排详情(与考试院一致) */
  @BaseField
  @GeneralField
  private String examDetail;

  /** relationState 关系状态 */
  @BaseField
  private Integer relationState;

  /** courseName 课程名称 */
  @GeneralField
  private String courseName;

  /** courseInfo 课程信息 */
  @GeneralField
  private String courseInfo;

  /** relationTime 关系创建时间 */
  @BaseField
  private Timestamp relationTime;

  /** courseInfo 新手课程与模块ID的对应号,0是一般课程 */
  @GeneralField
  private String moduleCourse;

  /** 地域编码 */
  @GeneralField
  private String module;

  /** 地域名称 */
  @GeneralField
  private String moduleName;


  /** 计分规则Id */
  @GeneralField
  private String ruleId;

  /* examDate 课程考期 */
  @GeneralField
  private String examDate;

  private MajorCourseInfo majorCourseInfo;


  public String getShowExamDateType() {
    return this.examDateType == null
        ? XdmsConstant.EXAM_DATE_TYPE_UNKNOWN
        : XdmsConstant.EXAM_DATE_TYPE[this.examDateType];
  }

  public void setMisc(ProductMiscInfo misc) {
    if (null != misc) this.misc = FastJsonUtil.toJson(misc);
  }

  public void setMisc(String misc) {
    this.misc = misc;
  }

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Long getProductCategoryId() {
	return productCategoryId;
}

public void setProductCategoryId(Long productCategoryId) {
	this.productCategoryId = productCategoryId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getBriefInfo() {
	return briefInfo;
}

public void setBriefInfo(String briefInfo) {
	this.briefInfo = briefInfo;
}

public String getDetail() {
	return detail;
}

public void setDetail(String detail) {
	this.detail = detail;
}

public String getImageUrl() {
	return imageUrl;
}

public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}

public Integer getCurrApplyCount() {
	return currApplyCount;
}

public void setCurrApplyCount(Integer currApplyCount) {
	this.currApplyCount = currApplyCount;
}

public Integer getTotalApplyCount() {
	return totalApplyCount;
}

public void setTotalApplyCount(Integer totalApplyCount) {
	this.totalApplyCount = totalApplyCount;
}

public Timestamp getBeginApplyTime() {
	return beginApplyTime;
}

public void setBeginApplyTime(Timestamp beginApplyTime) {
	this.beginApplyTime = beginApplyTime;
}

public Timestamp getEndApplyTime() {
	return endApplyTime;
}

public void setEndApplyTime(Timestamp endApplyTime) {
	this.endApplyTime = endApplyTime;
}

public BigDecimal getOriginalAmount() {
	return originalAmount;
}

public void setOriginalAmount(BigDecimal originalAmount) {
	this.originalAmount = originalAmount;
}

public BigDecimal getPayAmount() {
	return payAmount;
}

public void setPayAmount(BigDecimal payAmount) {
	this.payAmount = payAmount;
}

public Timestamp getCreateTime() {
	return createTime;
}

public void setCreateTime(Timestamp createTime) {
	this.createTime = createTime;
}

public Timestamp getUpdateTime() {
	return updateTime;
}

public void setUpdateTime(Timestamp updateTime) {
	this.updateTime = updateTime;
}

public Integer getShowStatus() {
	return showStatus;
}

public void setShowStatus(Integer showStatus) {
	this.showStatus = showStatus;
}

public Integer getPraiseCount() {
	return praiseCount;
}

public void setPraiseCount(Integer praiseCount) {
	this.praiseCount = praiseCount;
}

public String getCategoryName() {
	return categoryName;
}

public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
}

public Integer getType() {
	return type;
}

public void setType(Integer type) {
	this.type = type;
}

public String getQuestionBankSetting() {
	return questionBankSetting;
}

public void setQuestionBankSetting(String questionBankSetting) {
	this.questionBankSetting = questionBankSetting;
}

public Long getResourceSubject() {
	return resourceSubject;
}

public void setResourceSubject(Long resourceSubject) {
	this.resourceSubject = resourceSubject;
}

public String getResourceSubjectName() {
	return resourceSubjectName;
}

public void setResourceSubjectName(String resourceSubjectName) {
	this.resourceSubjectName = resourceSubjectName;
}

public String getShareUrl() {
	return shareUrl;
}

public void setShareUrl(String shareUrl) {
	this.shareUrl = shareUrl;
}

public String getCourseCode() {
	return courseCode;
}

public void setCourseCode(String courseCode) {
	this.courseCode = courseCode;
}

public String getCourseQuality() {
	return courseQuality;
}

public void setCourseQuality(String courseQuality) {
	this.courseQuality = courseQuality;
}

public Long getCourseCredit() {
	return courseCredit;
}

public void setCourseCredit(Long courseCredit) {
	this.courseCredit = courseCredit;
}

public String getCourseCheckType() {
	return courseCheckType;
}

public void setCourseCheckType(String courseCheckType) {
	this.courseCheckType = courseCheckType;
}

public Short getExamDateType() {
	return examDateType;
}

public void setExamDateType(Short examDateType) {
	this.examDateType = examDateType;
}

public String getExamDetail() {
	return examDetail;
}

public void setExamDetail(String examDetail) {
	this.examDetail = examDetail;
}

public Integer getRelationState() {
	return relationState;
}

public void setRelationState(Integer relationState) {
	this.relationState = relationState;
}

public String getCourseName() {
	return courseName;
}

public void setCourseName(String courseName) {
	this.courseName = courseName;
}

public String getCourseInfo() {
	return courseInfo;
}

public void setCourseInfo(String courseInfo) {
	this.courseInfo = courseInfo;
}

public Timestamp getRelationTime() {
	return relationTime;
}

public void setRelationTime(Timestamp relationTime) {
	this.relationTime = relationTime;
}

public String getModuleCourse() {
	return moduleCourse;
}

public void setModuleCourse(String moduleCourse) {
	this.moduleCourse = moduleCourse;
}

public String getModule() {
	return module;
}

public void setModule(String module) {
	this.module = module;
}

public String getModuleName() {
	return moduleName;
}

public void setModuleName(String moduleName) {
	this.moduleName = moduleName;
}

public String getRuleId() {
	return ruleId;
}

public void setRuleId(String ruleId) {
	this.ruleId = ruleId;
}

public String getExamDate() {
	return examDate;
}

public void setExamDate(String examDate) {
	this.examDate = examDate;
}

public MajorCourseInfo getMajorCourseInfo() {
	return majorCourseInfo;
}

public void setMajorCourseInfo(MajorCourseInfo majorCourseInfo) {
	this.majorCourseInfo = majorCourseInfo;
}

public String getMisc() {
	return misc;
}
  
}
