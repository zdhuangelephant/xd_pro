package com.xiaodou.resources.model.quesbk;

import java.util.UUID;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.resources.enums.quesbk.ExamType;
import com.xiaodou.resources.model.BaseEntity;



/**
 * <p>
 * 试卷类
 * </p>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @version 1.0
 * @date 2015年6月28日
 */
public class QuesbkExamPaper extends BaseEntity {
  /**
   * 主键ID
   */
  private String id;

  /**
   * 课程ID
   */
  private Long courseId;
  /** chapterId 章ID */
  private Long chapterId;
  /** itemId 节ID */
  private Long itemId;

  /**
   * 测验类型
   */
  private Long examTypeId;

  /**
   * 测验名称
   */
  private String examName;

  /**
   * 问题数量
   */
  private Integer quesNum = 0;

  /**
   * 问题ID列表
   */
  private String quesIds;

  /**
   * 描述
   */
  private String mdesc;

  /**
   * 状态
   */
  private String status = "99";

  /**
   * 难度
   */
  private String diffculty;

  public QuesbkExamPaper() {}

  public QuesbkExamPaper(ExamType examType, String productId) {
    this(examType, productId, null, null);
  }

  public QuesbkExamPaper(ExamType examType, String productId, String productChapterId,
      String productItemId) {
    id = UUID.randomUUID().toString();
    examName = examType.getName();
    mdesc = examName;
    examTypeId = Long.parseLong(examType.getCode());
    if (StringUtils.isNotBlank(productId)) courseId = Long.parseLong(productId);
    if (StringUtils.isNotBlank(productChapterId)) chapterId = Long.parseLong(productChapterId);
    if (StringUtils.isNotBlank(productItemId)) itemId = Long.parseLong(productItemId);
  }

  public Long getExamTypeId() {
    return examTypeId;
  }

  public void setExamTypeId(Long examTypeId) {
    this.examTypeId = examTypeId;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public Long getChapterId() {
    return chapterId;
  }

  public void setChapterId(Long chapterId) {
    this.chapterId = chapterId;
  }

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public Integer getQuesNum() {
    return quesNum;
  }

  public void setQuesNum(Integer quesNum) {
    this.quesNum = quesNum;
  }

  public String getExamName() {
    return examName;
  }

  public void setExamName(String examName) {
    this.examName = examName == null ? null : examName.trim();
  }

  public String getQuesIds() {
    return quesIds;
  }

  public void setQuesIds(String quesIds) {
    this.quesIds = quesIds == null ? null : quesIds.trim();
  }

  public String getMdesc() {
    return mdesc;
  }

  public void setMdesc(String mdesc) {
    this.mdesc = mdesc == null ? null : mdesc.trim();
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status == null ? null : status.trim();
  }

  public String getDiffculty() {
    return diffculty;
  }

  public void setDiffculty(String diffculty) {
    this.diffculty = diffculty == null ? null : diffculty.trim();
  }
}
