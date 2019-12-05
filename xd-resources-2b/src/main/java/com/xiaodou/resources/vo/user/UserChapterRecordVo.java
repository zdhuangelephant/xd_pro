package com.xiaodou.resources.vo.user;

import com.xiaodou.resources.constant.product.CourseConstant;


public class UserChapterRecordVo {
  private Long chapterId;
  private Long productId;
  private Long parentId;
  private Long resourceType;
  private String chapterName;
  private String chapterIndex;// eg:"第一章"
  private Short starLevel; // 星级 0 0颗 1 一星 2 两心 3 三星
  private Short score; // 闯关得分
  private Short status;// 状态 0 未解锁 1 已解锁 2 已完成（数据库针对一个用户该字段为1的值有且只能为1）
  private Integer completeCount; // 完成者数量
  private String topUserList; // top用户列表
  private String pictureUrl; // 章节图片

  public Long getChapterId() {
    return chapterId;
  }

  public void setChapterId(Long chapterId) {
    this.chapterId = chapterId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public Long getResourceType() {
    return resourceType == CourseConstant.RESOURCE_TYPE_COURSE ? parentId == 0l
        ? CourseConstant.RESOURCE_TYPE_CHAPTER
        : CourseConstant.RESOURCE_TYPE_ITEM : resourceType;
  }

  public void setResourceType(Long resourceType) {
    this.resourceType = resourceType;
  }

  public String getChapterName() {
    return chapterName;
  }

  public void setChapterName(String chapterName) {
    this.chapterName = chapterName;
  }

  public String getChapterIndex() {
    return chapterIndex;
  }

  public void setChapterIndex(String chapterIndex) {
    this.chapterIndex = chapterIndex;
  }

  public Short getStarLevel() {
    return starLevel;
  }

  public void setStarLevel(Short starLevel) {
    this.starLevel = starLevel;
  }

  public Short getScore() {
    return score;
  }

  public void setScore(Short score) {
    this.score = score;
  }

  public Short getStatus() {
    return status;
  }

  public void setStatus(Short status) {
    this.status = status;
  }

  public Integer getCompleteCount() {
    return completeCount;
  }

  public void setCompleteCount(Integer completeCount) {
    this.completeCount = completeCount;
  }

  public String getTopUserList() {
    return topUserList;
  }

  public void setTopUserList(String topUserList) {
    this.topUserList = topUserList;
  }

  public String getPictureUrl() {
    return pictureUrl;
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

  public static class RecordDetail {
    private Integer achieveStarCount;
    private Integer totalStarCount;
    private Integer challengeLevelCount;
    private Integer totalLevelCount;

    public RecordDetail() {}

    public RecordDetail(Integer achieveStarCount, Integer totalStarCount) {
      super();
      this.achieveStarCount = achieveStarCount;
      this.totalStarCount = totalStarCount;
    }

    public Integer getAchieveStarCount() {
      return achieveStarCount;
    }

    public void setAchieveStarCount(Integer achieveStarCount) {
      this.achieveStarCount = achieveStarCount;
    }

    public Integer getTotalStarCount() {
      return totalStarCount;
    }

    public void setTotalStarCount(Integer totalStarCount) {
      this.totalStarCount = totalStarCount;
    }

    public Integer getChallengeLevelCount() {
      return challengeLevelCount;
    }

    public void setChallengeLevelCount(Integer challengeLevelCount) {
      this.challengeLevelCount = challengeLevelCount;
    }

    public Integer getTotalLevelCount() {
      return totalLevelCount;
    }

    public void setTotalLevelCount(Integer totalLevelCount) {
      this.totalLevelCount = totalLevelCount;
    }
  }
}
