package com.xiaodou.resources.response.quesbk;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.constant.quesbk.ResultType;
import com.xiaodou.resources.response.BaseResponse;

public class ChapterListResponse extends BaseResponse {

  public ChapterListResponse(ResultType type) {
    super(type);
  }

  /**
   * 章节列表
   */
  private List<Chapter> courseItemList = Lists.newArrayList();

  public List<Chapter> getCourseItemList() {
    return courseItemList;
  }

  public void setCourseItemList(List<Chapter> courseItemList) {
    this.courseItemList = courseItemList;
  }

  /**
   * 章节
   * 
   * @author Administrator
   */
  public static class Chapter {
    /**
     * 章节ID
     */
    private String chapterId;
    /**
     * 章节名称
     */
    private String chapterName;
    /**
     * 练习状态:初始状态为不可以练习
     */
    private String examStatus = QuesBaseConstant.NO;

    /** importance 重要程度 星级1-10 */
    private Integer importance = 0;

    /** totalQues 总题数 */
    private Integer totalQues = 0;

    /** answeredQues 已答题数 */
    private Integer answeredQues = 0;

    /** answerPercent 已答题比例 */
    private String answerPercent = Double.toString(0d);

    /** answeredRightQues 已答正确题数 */
    private Integer answeredRightQues = 0;

    /** answerRightPercent 正确答题数比例 */
    private String answerRightPercent = Double.toString(0d);
    /**
     * 节列表
     */
    private List<ItemInfo> childList = Lists.newArrayList();

    /** listOrder 排序字段 */
    private Long listOrder = Long.MAX_VALUE;

    public String getAnswerPercent() {
      return answerPercent;
    }

    public void setAnswerPercent(Double answerPercent) {
      setAnswerPercent(QuesBaseConstant.D_FORMAT.format(answerPercent));
    }

    public void setAnswerPercent(String answerPercent) {
      this.answerPercent = answerPercent;
    }

    public String getAnswerRightPercent() {
      return answerRightPercent;
    }

    public void setAnswerRightPercent(Double answerRightPercent) {
      setAnswerRightPercent(QuesBaseConstant.D_FORMAT.format(answerRightPercent));
    }

    public void setAnswerRightPercent(String answerRightPercent) {
      this.answerRightPercent = answerRightPercent;
    }

    public Long getListOrder() {
      return listOrder;
    }

    public void setListOrder(Long listOrder) {
      this.listOrder = listOrder;
    }

    public String getImportance() {
      return importance.toString();
    }

    public void setImportance(Integer importance) {
      this.importance = importance;
    }

    public String getTotalQues() {
      return totalQues.toString();
    }

    public void setTotalQues(Integer totalQues) {
      this.totalQues = totalQues;
      if (totalQues <= 0) return;
      setExamStatus(QuesBaseConstant.YES);
      setAnswerPercent(Double.valueOf(this.answeredQues) / Double.valueOf(this.totalQues));
      setAnswerRightPercent(Double.valueOf(this.answeredRightQues) / Double.valueOf(this.totalQues));
    }

    public void addTotalQues(Integer totalQues) {
      setTotalQues(this.totalQues + totalQues);
    }

    public String getAnsweredQues() {
      return answeredQues.toString();
    }

    public void setAnsweredQues(Integer answeredQues) {
      this.answeredQues = answeredQues;
      if (this.totalQues > 0)
        setAnswerPercent(Double.valueOf(this.answeredQues) / Double.valueOf(this.totalQues));
    }

    public void addAnsweredQues(Integer answeredQues) {
      setAnsweredQues(this.answeredQues + answeredQues);
    }

    public String getAnsweredRightQues() {
      return answeredRightQues.toString();
    }

    public void setAnsweredRightQues(Integer answeredRightQues) {
      this.answeredRightQues = answeredRightQues;
      if (this.totalQues > 0)
        setAnswerRightPercent(Double.valueOf(this.answeredRightQues)
            / Double.valueOf(this.totalQues));
    }

    public void addAnsweredRightQues(Integer answeredRightQues) {
      setAnsweredRightQues(this.answeredRightQues + answeredRightQues);
    }

    public String getChapterId() {
      return chapterId;
    }

    public void setChapterId(String chapterId) {
      this.chapterId = chapterId;
    }

    public String getChapterName() {
      return chapterName;
    }

    public void setChapterName(String chapterName) {
      this.chapterName = chapterName;
    }

    public String getExamStatus() {
      return examStatus;
    }

    public void setExamStatus(String examStatus) {
      this.examStatus = examStatus;
    }

    public List<ItemInfo> getChildList() {
      return childList;
    }

    public void setChildList(List<ItemInfo> childList) {
      this.childList = childList;
    }

    public ItemInfo transfer2Item() {
      ItemInfo item = new ItemInfo();
      item.setItemId(this.getChapterId());
      item.setItemName(this.getChapterName());
      item.setExamStatus(this.getExamStatus());
      item.setImportance(this.getImportance());
      item.setTotalQues(this.getTotalQues());
      item.setAnsweredQues(this.getAnsweredQues());
      item.setAnsweredRightQues(this.getAnsweredRightQues());
      item.setAnswerPercent(this.getAnswerPercent());
      item.setAnswerRightPercent(this.getAnswerRightPercent());
      item.setExamStatus(QuesBaseConstant.NO);
      return item;
    }
  }

  /**
   * 节信息
   * 
   * @author Administrator
   */
  public static class ItemInfo {
    /**
     * 节ID
     */
    private String itemId;
    /**
     * 节名称
     */
    private String itemName;
    /**
     * 练习状态
     */
    private String examStatus;

    /** importance 重要程度 星级1-10 */
    private String importance;

    /** totalQues 总题数 */
    private String totalQues;

    /** answeredQues 已答题数 */
    private String answeredQues = Integer.toString(0);

    /** answeredRightQues 已答正确题数 */
    private String answeredRightQues = Integer.toString(0);

    /** answerPercent 已答题比例 */
    private String answerPercent = Double.toString(0d);

    /** answerRightPercent 正确答题数比例 */
    private String answerRightPercent = Double.toString(0d);

    public String getAnswerPercent() {
      return answerPercent;
    }

    public void setAnswerPercent(String answerPercent) {
      this.answerPercent = answerPercent;
    }

    public String getAnswerRightPercent() {
      return answerRightPercent;
    }

    public void setAnswerRightPercent(String answerRightPercent) {
      this.answerRightPercent = answerRightPercent;
    }

    public String getImportance() {
      return importance;
    }

    public void setImportance(String importance) {
      this.importance = importance;
    }

    public String getTotalQues() {
      return totalQues;
    }

    public void setTotalQues(String totalQues) {
      this.totalQues = totalQues;
    }

    public String getAnsweredQues() {
      return answeredQues;
    }

    public void setAnsweredQues(String answeredQues) {
      this.answeredQues = answeredQues;
    }

    public String getAnsweredRightQues() {
      return answeredRightQues;
    }

    public void setAnsweredRightQues(String answeredRightQues) {
      this.answeredRightQues = answeredRightQues;
    }

    public String getItemId() {
      return itemId;
    }

    public void setItemId(String itemId) {
      this.itemId = itemId;
    }

    public String getItemName() {
      return itemName;
    }

    public void setItemName(String itemName) {
      this.itemName = itemName;
    }

    public String getExamStatus() {
      return examStatus;
    }

    public void setExamStatus(String examStatus) {
      this.examStatus = examStatus;
    }
  }

}
