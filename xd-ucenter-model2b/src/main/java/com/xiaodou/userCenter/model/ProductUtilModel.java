package com.xiaodou.userCenter.model;

import java.sql.Timestamp;

import com.xiaodou.common.annotation.BaseField;
import com.xiaodou.common.annotation.GeneralField;

/**
 * Created by zyp on 15/6/26.
 */
public class ProductUtilModel {

  // 主键Id
  @BaseField
  @GeneralField
  private Integer id;

  // 栏目Id
  @BaseField
  private Integer productCategoryId;

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
  private Timestamp beginApplyTime;

  // 申请结束时间
  @BaseField
  private Timestamp endApplyTime;

  // 原价
  @BaseField
  @GeneralField
  private Integer originalAmount;

  // 优惠价
  @BaseField
  @GeneralField
  private Integer payAmount;

  // 创建时间
  @BaseField
  @GeneralField
  private Timestamp createTime;

  // 更新时间
  @BaseField
  private Timestamp updateTime;

  // misc 杂项
  @BaseField
  private String misc;

  // 是否显示
  @BaseField
  @GeneralField
  private Integer showStatus;

  // 点赞数
  @BaseField
  @GeneralField
  private Integer praiseCount;

  // 栏目名称(专业)
  @BaseField
  @GeneralField
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
  private Integer resourceSubject;

  // 分享url
  @BaseField
  @GeneralField
  private String shareUrl;

  @BaseField
  @GeneralField
  private String courseCode;// 课程代码
  @BaseField
  @GeneralField
  private String courseQuality;// 课程性质
  @BaseField
  @GeneralField
  private String courseCredit;// 课程学分
  @BaseField
  @GeneralField
  private String courseCheckType;// 课程考核方式
  @BaseField
  @GeneralField
  private String examDateType;// 课程考期类型 0 上半年 1 下半年 2 整年
  @BaseField
  @GeneralField
  private String examDetail;// 近期考试安排详情(与考试院一致)

  @BaseField
  @GeneralField
  private String major;// 专业名称
  @BaseField
  @GeneralField
  private String majorCode; // 专业码值
  @BaseField
  @GeneralField
  private String score;// 得分
  @BaseField
  @GeneralField
  private String myExamDate;// 我的考试期（1610）每条数据有且最多只存在两个选择项（**04/**10）
  @BaseField
  @GeneralField
  private String examDateStatus;// 1近期2其它考期(用户自定义)


  public String getExamDateStatus() {
    return examDateStatus;
  }

  public void setExamDateStatus(String examDateStatus) {
    this.examDateStatus = examDateStatus;
  }

  public String getMyExamDate() {
    return myExamDate;
  }

  public void setMyExamDate(String myExamDate) {
    this.myExamDate = myExamDate;
  }

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public String getExamDetail() {
    return examDetail;
  }

  public void setExamDetail(String examDetail) {
    this.examDetail = examDetail;
  }

  public String getExamDateType() {
    return examDateType;
  }

  public void setExamDateType(String examDateType) {
    this.examDateType = examDateType;
  }

  public String getMajor() {
    return major;
  }

  public void setMajor(String major) {
    this.major = major;
  }

  public String getMajorCode() {
    return majorCode;
  }

  public void setMajorCode(String majorCode) {
    this.majorCode = majorCode;
  }

  public String getShareUrl() {
    return shareUrl;
  }

  public void setShareUrl(String shareUrl) {
    this.shareUrl = shareUrl;
  }

  public Integer getResourceSubject() {
    return resourceSubject;
  }

  public void setResourceSubject(Integer resourceSubject) {
    this.resourceSubject = resourceSubject;
  }

  public String getQuestionBankSetting() {
    return questionBankSetting;
  }

  public void setQuestionBankSetting(String questionBankSetting) {
    this.questionBankSetting = questionBankSetting;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getProductCategoryId() {
    return productCategoryId;
  }

  public void setProductCategoryId(Integer productCategoryId) {
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

  public Integer getOriginalAmount() {
    return originalAmount;
  }

  public void setOriginalAmount(Integer originalAmount) {
    this.originalAmount = originalAmount;
  }

  public Integer getPayAmount() {
    return payAmount;
  }

  public void setPayAmount(Integer payAmount) {
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

  public String getMisc() {
    return misc;
  }

  public void setMisc(String misc) {
    this.misc = misc;
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

  public String getCourseCredit() {
    return courseCredit;
  }

  public void setCourseCredit(String courseCredit) {
    this.courseCredit = courseCredit;
  }

  public String getCourseCheckType() {
    return courseCheckType;
  }

  public void setCourseCheckType(String courseCheckType) {
    this.courseCheckType = courseCheckType;
  }
}
