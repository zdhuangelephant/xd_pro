package com.xiaodou.resources.model.product;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.common.annotation.BaseField;
import com.xiaodou.common.annotation.GeneralField;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name ProductModel CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年11月11日
 * @description 产品模型
 * @version 1.0
 */
public class ProductModel {

  // 主键Id
  @BaseField
  @GeneralField
  @Column(isMajor = true, canUpdate = false)
  private Long id;

  // 产品名
  @BaseField
  @GeneralField
  @Column(canUpdate = false)
  private String name;

  // 简介
  @BaseField
  @GeneralField
  @Column(canUpdate = false)
  private String briefInfo;

  // 详情
  @BaseField
  @GeneralField
  @Column(canUpdate = false)
  private String detail;

  // 图片地址
  @BaseField
  @GeneralField
  @Column(canUpdate = false)
  private String imageUrl;

  // 当前报名人数
  @BaseField
  @GeneralField
  @Column
  private Integer currApplyCount;

  // 报名人数上限
  @BaseField
  @GeneralField
  @Column(canUpdate = false)
  private Integer totalApplyCount;

  // 开始申请时间
  @BaseField
  @GeneralField
  @Column(canUpdate = false, betweenScope = true)
  private Timestamp beginApplyTime;

  // 申请结束时间
  @BaseField
  @GeneralField
  @Column(canUpdate = false, betweenScope = true)
  private Timestamp endApplyTime;

  // 原价
  @BaseField
  @GeneralField
  @Column(canUpdate = false)
  private BigDecimal originalAmount;

  // 优惠价
  @BaseField
  @GeneralField
  @Column(canUpdate = false)
  private BigDecimal payAmount;

  // 创建时间
  @BaseField
  @GeneralField
  @Column(canUpdate = false, betweenScope = true)
  private Timestamp createTime;

  // 更新时间
  @BaseField
  @GeneralField
  @Column(canUpdate = false, betweenScope = true)
  private Timestamp updateTime;

  // misc 杂项
  @BaseField
  @GeneralField
  @Column(canUpdate = false)
  private String misc;

  // 是否显示
  @BaseField
  @GeneralField
  @Column(canUpdate = false)
  private Integer showStatus;

  // 点赞数
  @BaseField
  @GeneralField
  @Column
  private Integer praiseCount;

  // 题库设置
  @BaseField
  @GeneralField
  @Column(canUpdate = false)
  private String questionBankSetting;

  // 资源产品ID
  @BaseField
  @GeneralField
  @Column(canUpdate = false)
  private Integer resourceSubject;

  /** shareUrl 分享url */
  @BaseField
  @GeneralField
  @Column(canUpdate = false)
  private String shareUrl;

  /** score 得分 */
  @BaseField
  @GeneralField
  private Double score;

  /** bgImgUrl 介绍背景图 */
  @BaseField
  @GeneralField
  @Column(canUpdate = false)
  private String bgImgUrl;

  /** userId 管理员ID */
  @BaseField
  @GeneralField
  @Column(canUpdate = false)
  private Integer userId;

  /** typeCode 类型 */
  @BaseField
  @GeneralField
  private String typeCode;

  /** talkWeight 单元讨论权重 */
  @BaseField
  @GeneralField
  @Column(canUpdate = false)
  private Integer talkWeight;

  /** taskWeight 单元作业权重 */
  @BaseField
  @GeneralField
  @Column(canUpdate = false)
  private Integer taskWeight;

  /** examWeight 单元测验权重 */
  @BaseField
  @GeneralField
  @Column(canUpdate = false)
  private Integer examWeight;

  /** finalWeight 期末考试权重 */
  @BaseField
  @GeneralField
  @Column(canUpdate = false)
  private Integer finalWeight;
  
  public Integer getResourceSubject() {
    return resourceSubject;
  }

  public void setResourceSubject(Integer resourceSubject) {
    this.resourceSubject = resourceSubject;
  }

  public Integer getTalkWeight() {
    return talkWeight;
  }

  public void setTalkWeight(Integer talkWeight) {
    this.talkWeight = talkWeight;
  }

  public Integer getTaskWeight() {
    return taskWeight;
  }

  public void setTaskWeight(Integer taskWeight) {
    this.taskWeight = taskWeight;
  }

  public Integer getExamWeight() {
    return examWeight;
  }

  public void setExamWeight(Integer examWeight) {
    this.examWeight = examWeight;
  }

  public Integer getFinalWeight() {
    return finalWeight;
  }

  public void setFinalWeight(Integer finalWeight) {
    this.finalWeight = finalWeight;
  }

  public String getQuestionBankSetting() {
    return questionBankSetting;
  }

  public void setQuestionBankSetting(String questionBankSetting) {
    this.questionBankSetting = questionBankSetting;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getShareUrl() {
    return shareUrl;
  }

  public void setShareUrl(String shareUrl) {
    this.shareUrl = shareUrl;
  }

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    this.score = score;
  }

  public String getBgImgUrl() {
    return bgImgUrl;
  }

  public void setBgImgUrl(String bgImgUrl) {
    this.bgImgUrl = bgImgUrl;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }

  /**
   * 获取组卷规则框架 总分数/题型构成/比重信息
   * 
   * @return
   */
  public QuesRuleDetail getQuesRuleDetail() {
    if (StringUtils.isJsonNotBlank(questionBankSetting)) {
      return FastJsonUtil.fromJson(questionBankSetting, QuesRuleDetail.class);
    }
    return null;
  }

  /**
   * @name @see com.xiaodou.domain.QuesRuleDetail.java
   * @CopyRright (c) 2015 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年8月9日
   * @description 组卷规则框架
   * @version 1.0
   */
  public static class QuesRuleDetail {
    /** totalScore 总分数 */
    private Integer totalScore;
    /**
     * typeList 组卷问题类型细则 问题类型/问题分值/问题数量/排列顺序
     */
    private List<QuesTypeDetail> typeList;

    public Integer getTotalScore() {
      return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
      this.totalScore = totalScore;
    }

    public List<QuesTypeDetail> getTypeList() {
      return typeList;
    }

    public void setTypeList(List<QuesTypeDetail> typeList) {
      this.typeList = typeList;
    }

  }

  /**
   * @name @see com.xiaodou.domain.QuesTypeDetail.java
   * @CopyRright (c) 2015 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年8月9日
   * @description 问题类型细节
   * @version 1.0
   */
  /** answerType 问题类型码 */
  public static class QuesTypeDetail {
    private Short answerType;
    /** id 问题类型ID */
    private Integer id;
    /** listOrder 排序字段 */
    private Integer listOrder;
    /** questionNum 问题数量 */
    private Integer questionNum;
    /** score 问题分值 */
    private Double score;
    /** typeName 问题类型名称 */
    private String typeName;

    public Short getAnswerType() {
      return answerType;
    }

    public void setAnswerType(Short answerType) {
      this.answerType = answerType;
    }

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public Integer getListOrder() {
      return listOrder;
    }

    public void setListOrder(Integer listOrder) {
      this.listOrder = listOrder;
    }

    public Integer getQuestionNum() {
      return questionNum;
    }

    public void setQuestionNum(Integer questionNum) {
      this.questionNum = questionNum;
    }

    public Double getScore() {
      return score;
    }

    public void setScore(Double score) {
      this.score = score;
    }

    public String getTypeName() {
      return typeName;
    }

    public void setTypeName(String typeName) {
      this.typeName = typeName;
    }
  }
}