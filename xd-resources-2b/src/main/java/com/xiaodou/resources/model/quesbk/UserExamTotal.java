package com.xiaodou.resources.model.quesbk;

import java.util.Set;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.model.BaseEntity;
import com.xiaodou.resources.model.product.CourseProductChapter;
import com.xiaodou.resources.util.QuesbkUtil;


public class UserExamTotal extends BaseEntity {
  private Long id;

  private String userId;

  private Long courseId;

  private Integer totalQues = 0;

  private Integer avgTotal = 0;

  private Integer totalRank = 0;

  private Integer rightQues = 0;

  private Integer avgRight = 0;

  private Integer rightRank = 0;

  private Double score = 0d;

  private String rightPercent;

  private String avgRightPerc;

  private String rightPercRank;

  private String chapterScore;

  private String tag;

  @JSONField(serialize = false)
  private Double totalScore = 0d;
  @JSONField(serialize = false)
  private Integer totalCount = 0;
  @JSONField(serialize = false)
  private Integer comleteCount = 0;
  @JSONField(serialize = false)
  private Set<Long> unCompleteSet = Sets.newHashSet();
  private UserChapterRecord chapterRecord = null;

  public UserChapterRecord getChapterRecord() {
    return chapterRecord;
  }

  public void setChapterRecord(UserChapterRecord chapterRecord) {
    this.chapterRecord = chapterRecord;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId == null ? null : userId.trim();
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

  public String getRightPercRank() {
    return rightPercRank;
  }

  public void setRightPercRank(String rightPercRank) {
    this.rightPercRank = rightPercRank == null ? null : rightPercRank.trim();
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

  public static class ChapterScore {
    public ChapterScore() {}

    public ChapterScore(CourseProductChapter chapter) {
      if (null != chapter.getId()) this.chapterId = chapter.getId().toString();
      this.chapterName = chapter.getName();
      this.chapterIndex = chapter.getChapterId();
      this.chapterScore = String.valueOf(0);
      this.listOrder = chapter.getListOrder();
    }

    public ChapterScore(String chapterId, String chapterIndex, String chapterName,
        String chapterScore) {
      this.chapterId = chapterId;
      this.chapterIndex = chapterIndex;
      this.chapterName = chapterName;
      this.chapterScore = chapterScore;
    }

    private String chapterId = String.valueOf(0L);
    private String chapterIndex = StringUtils.EMPTY;
    private String chapterName = StringUtils.EMPTY;
    private String chapterScore = QuesBaseConstant.D_FORMAT.format(0.00);
    private Long listOrder = -1l;

    private Double totalScore = 0d;
    private Integer totalCount = 0;
    @JSONField(serialize = false)
    private Integer comleteCount = 0;
    @JSONField(serialize = false)
    private Set<Long> unCompleteSet = Sets.newHashSet();

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

    public void setChapterScore(String chapterScore) {
      this.chapterScore = chapterScore;
    }

    public void setChapterScore(Double chapterScore) {
      setChapterScore(QuesBaseConstant.D_FORMAT.format(chapterScore));
    }

    public Double caculateScore() {
      if (null != totalScore && null != totalCount && totalCount > 0) {
        setChapterScore(totalScore / totalCount);
      }
      return Double.valueOf(getChapterScore());
    }

    public void addTotalScore(Double score) {
      if (null != score) this.totalScore += score;
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
  }
}
