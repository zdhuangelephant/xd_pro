package com.xiaodou.domain.behavior;

import java.util.Set;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.domain.BaseEntity;
import com.xiaodou.domain.product.CourseProductItem;
import com.xiaodou.util.QuesbkUtil;


public class UserExamTotal extends BaseEntity {
  private Long id;

  private String userId;

  private String module;

  private String majorId;

  private Long courseId;

  private Integer totalQues;

  private Integer avgTotal;

  private Integer totalRank;

  private Integer rightQues;

  private Integer avgRight;

  private Integer rightRank;

  private Double score;

  private String rightPercent;

  private String avgRightPerc;

  private Integer rightPercRank;

  private String chapterScore;

  // private Double avgChapterScore = 0d;

  private Integer itemTotalCount = 0;

  private String tag;
  @JSONField(serialize = false)
  private Double allChapterScore = 0d;
  @JSONField(serialize = false)
  private Double totalScore = 0d;
  @JSONField(serialize = false)
  private Integer totalCount = 0;
  @JSONField(serialize = false)
  private Integer comleteCount = 0;
  @JSONField(serialize = false)
  private Set<Long> unCompleteSet = Sets.newHashSet();

  // private UserChapterRecord chapterRecord = null;

  // private Double finalExamScore= 0d;
  // private Double MissionFinishScore= 0d;
  // private Double supplementScore=0d;
  // public UserChapterRecord getChapterRecord() {
  // return chapterRecord;
  // }
  //
  // public void setChapterRecord(UserChapterRecord chapterRecord) {
  // this.chapterRecord = chapterRecord;
  // }

  public Double getTotalScore() {
    return totalScore;
  }

  public String getMajorId() {
    return majorId;
  }

  public void setMajorId(String majorId) {
    this.majorId = majorId;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId == null ? null : userId.trim();
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public Integer getTotalQues() {
    return totalQues;
  }

  public void setTotalQues(Integer totalQues) {
    this.totalQues = totalQues == null ? 0 : totalQues;
  }

  public Integer getAvgTotal() {
    return avgTotal;
  }

  public void setAvgTotal(Integer avgTotal) {
    this.avgTotal = avgTotal == null ? 0 : avgTotal;
  }

  public Integer getTotalRank() {
    return totalRank;
  }

  public void setTotalRank(Integer totalRank) {
    this.totalRank = totalRank == null ? 0 : totalRank;
  }

  public Integer getRightQues() {
    return rightQues;
  }

  public void setRightQues(Integer rightQues) {
    this.rightQues = rightQues == null ? 0 : rightQues;
  }

  public Integer getAvgRight() {
    return avgRight;
  }

  public void setAvgRight(Integer avgRight) {
    this.avgRight = avgRight == null ? 0 : avgRight;
  }

  public Integer getRightRank() {
    return rightRank;
  }

  public void setRightRank(Integer rightRank) {
    this.rightRank = rightRank == null ? 0 : rightRank;
  }

  public String getRightPercent() {
    return rightPercent;
  }

  public void setRightPercent(String rightPercent) {
    this.rightPercent = rightPercent;
  }

  public String getAvgRightPerc() {
    return avgRightPerc;
  }

  public void setAvgRightPerc(String avgRightPerc) {
    this.avgRightPerc = avgRightPerc == null ? null : avgRightPerc.trim();
  }

  public Integer getRightPercRank() {
    return rightPercRank;
  }

  public void setRightPercRank(Integer rightPercRank) {
    this.rightPercRank = rightPercRank;
  }

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    this.score = score;
  }

  public String getChapterScore() {
    return chapterScore;
  }

  public void setChapterScore(String chapterScore) {
    this.chapterScore = chapterScore == null ? null : chapterScore.trim();
  }

  public void setCourseId(String courseId) {
    if (StringUtils.isNotBlank(courseId)) {
      setCourseId(Long.parseLong(QuesbkUtil.trim(courseId)));
    }
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public Double caculateScore() {
    if (null != totalScore && null != totalCount && totalCount > 0) {
      this.score = totalScore / totalCount;
    }
    return getScore();

  }

  public void addTotalScore(Double score) {
    if (null != score) this.totalScore += score;
  }

  public void addTotalChapterScore(Double score) {
    if (null != score) this.allChapterScore += score;
  }

  public void addScore(Double score) {
    if (null != score) this.score += score;
  }

  public void addTotalCount(Integer count) {
    if (null != count) this.totalCount += count;
  }

  public void addCompeletCount(Integer count) {
    if (null != count) this.comleteCount += count;
  }

  public void addUnCompleteSet(Long itemId) {
    if (null != itemId) this.unCompleteSet.add(itemId);
  }

  public boolean isComplete() {
    return this.comleteCount == this.totalCount && unCompleteSet.isEmpty()
        && unCompleteSet.size() == 0;
  }

  public Double getAllChapterScore() {
    return allChapterScore;
  }

  public void setAllChapterScore(Double allChapterScore) {
    this.allChapterScore = allChapterScore;
  }

  public Integer getItemTotalCount() {
    return itemTotalCount;
  }

  public void setItemTotalCount(Integer itemTotalCount) {
    this.itemTotalCount = itemTotalCount;
  }

  public void addItemTotalCount() {
    this.itemTotalCount += 1;
  }

  // public Double getFinalExamScore() {
  // return finalExamScore;
  // }
  //
  // public void setFinalExamScore(Double finalExamScore) {
  // this.finalExamScore = finalExamScore;
  // }

  // public Double getAvgChapterScore() {
  // return avgChapterScore;
  // }
  //
  // public void setAvgChapterScore(Double avgChapterScore) {
  // this.avgChapterScore = avgChapterScore;
  // }

  // public Double getMissionFinishScore() {
  // return MissionFinishScore;
  // }
  //
  // public void setMissionFinishScore(Double missionFinishScore) {
  // MissionFinishScore = missionFinishScore;
  // }
  //
  // public Double getSupplementScore() {
  // return supplementScore;
  // }
  //
  // public void setSupplementScore(Double supplementScore) {
  // this.supplementScore = supplementScore;
  // }

  public static class ChapterScore {
    public ChapterScore() {}

    public ChapterScore(CourseProductItem chapter) {
      if (null != chapter.getId()) this.chapterId = chapter.getId().toString();
      this.chapterName = chapter.getName();
      this.chapterIndex = chapter.getChapterId();
      this.chapterScore = String.valueOf(0);
      this.listOrder = chapter.getListOrder();
      this.weight = chapter.getWeight();
    }

    public ChapterScore(String chapterId, String chapterIndex, String chapterName,
        Double chapterSummaryScore, Double weight) {
      this.chapterId = chapterId;
      this.chapterIndex = chapterIndex;
      this.chapterName = chapterName;
      this.chapterScore = QuesBaseConstant.D_FORMAT.format(chapterSummaryScore);
      this.chapterSummaryScore = chapterSummaryScore;
      this.weight = weight;
    }

    private String chapterId = String.valueOf(0L);
    private String chapterIndex = StringUtils.EMPTY;
    private String chapterName = StringUtils.EMPTY;
    private String chapterScore = QuesBaseConstant.D_FORMAT.format(0.00);
    private Double chapterSummaryScore = 0.00d;
    private Long listOrder = -1l;
    private Double weight;

    public Long getListOrder() {
      return listOrder;
    }

    public void setListOrder(Long listOrder) {
      this.listOrder = listOrder;
    }

    public String getChapterId() {
      return chapterId;
    }

    public void setChapterId(String chapterId) {
      this.chapterId = chapterId;
    }

    public String getChapterIndex() {
      return chapterIndex;
    }

    public void setChapterIndex(String chapterIndex) {
      this.chapterIndex = chapterIndex;
    }

    public String getChapterName() {
      return chapterName;
    }

    public void setChapterName(String chapterName) {
      this.chapterName = chapterName;
    }

    public String getChapterScore() {
      return chapterScore;
    }

    public Double getChapterSummaryScore() {
      return chapterSummaryScore;
    }

    public void setChapterSummaryScore(Double chapterSummaryScore) {
      this.chapterSummaryScore = chapterSummaryScore;
      this.chapterScore = QuesBaseConstant.D_FORMAT.format(chapterSummaryScore);
    }

    public Double getWeight() {
      return weight;
    }

    public void setWeight(Double weight) {
      this.weight = weight;
    }


  }
}
