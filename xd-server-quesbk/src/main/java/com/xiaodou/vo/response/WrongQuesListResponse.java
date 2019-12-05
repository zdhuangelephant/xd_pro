package com.xiaodou.vo.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.constant.ResultType;

public class WrongQuesListResponse extends BaseResponse {

  public WrongQuesListResponse(ResultType type) {
    super(type);
  }

  /**
   * 章信息列表
   */
  private List<ChapterInfo> courseItemList = Lists.newArrayList();

  public List<ChapterInfo> getCourseItemList() {
    return courseItemList;
  }

  public void setCourseItemList(List<ChapterInfo> courseItemList) {
    this.courseItemList = courseItemList;
  }

  /**
   * 章节信息
   * 
   * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
   */
  public static class ChapterInfo {
    /**
     * 章节ID
     */
    private Long chapterId;
    /**
     * 章节名称
     */
    private String chapterName;
    /** listOrder 排序字段 */
    private Long listOrder;
    /**
     * 问题数量
     */
    private int quesCount = 0;
    /**
     * 未掌握问题数量
     */
    private int uncatchQuesCount = 0;
    /**
     * 待强化问题数量
     */
    private int catchingQuesCount = 0;
    /**
     * 已消灭问题数量
     */
    private int catchedQuesCount = 0;
    private int goodQuesCount = 0;// 好题数量
    private int unknownQuesCount = 0;// 我不会的问题数量
    private int needMemeryQuesCount = 0; // 需要记忆问题数量

    public Long getChapterId() {
      return chapterId;
    }

    public void setChapterId(Long chapterId) {
      this.chapterId = chapterId;
    }

    public String getChapterName() {
      return chapterName;
    }

    public void setChapterName(String chapterName) {
      this.chapterName = chapterName;
    }

    public int getQuesCount() {
      return quesCount;
    }

    public void setQuesCount(int quesCount) {
      this.quesCount = quesCount;
    }

    public void addQuesCount(int quesCount) {
      this.quesCount += quesCount;
    }

    public Long getListOrder() {
      return listOrder;
    }

    public void setListOrder(Long listOrder) {
      this.listOrder = listOrder;
    }

    public int getUncatchQuesCount() {
      return uncatchQuesCount;
    }

    public void setUncatchQuesCount(int uncatchQuesCount) {
      this.uncatchQuesCount = uncatchQuesCount;
    }

    public int getCatchingQuesCount() {
      return catchingQuesCount;
    }

    public void setCatchingQuesCount(int catchingQuesCount) {
      this.catchingQuesCount = catchingQuesCount;
    }

    public int getCatchedQuesCount() {
      return catchedQuesCount;
    }

    public void setCatchedQuesCount(int catchedQuesCount) {
      this.catchedQuesCount = catchedQuesCount;
    }

    public int getGoodQuesCount() {
      return goodQuesCount;
    }

    public void setGoodQuesCount(int goodQuesCount) {
      this.goodQuesCount = goodQuesCount;
    }

    public int getUnknownQuesCount() {
      return unknownQuesCount;
    }

    public void setUnknownQuesCount(int unknownQuesCount) {
      this.unknownQuesCount = unknownQuesCount;
    }

    public int getNeedMemeryQuesCount() {
      return needMemeryQuesCount;
    }

    public void setNeedMemeryQuesCount(int needMemeryQuesCount) {
      this.needMemeryQuesCount = needMemeryQuesCount;
    }

    /*
     * public List<KeyPoint> getKeyPointList() { return keyPointList; }
     * 
     * public void setKeyPointList(List<KeyPoint> keyPointList) { this.keyPointList = keyPointList;
     * }
     */

    public ItemInfo transfer2Item() {
      ItemInfo item = new ItemInfo();
      item.setItemId(this.getChapterId());
      item.setItemName(this.getChapterName());
      item.setQuesCount(this.getQuesCount());
      return item;
    }
  }

  /**
   * 节信息
   * 
   * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
   */
  public static class ItemInfo {
    /**
     * 考点列表
     */
    private List<KeyPoint> keyPointList = Lists.newArrayList();
    /**
     * 节名称
     */
    private String itemName;
    /**
     * 节ID
     */
    private Long itemId;
    /**
     * 问题数量
     */
    private int quesCount = 0;

    public String getItemName() {
      return itemName;
    }

    public void setItemName(String itemName) {
      this.itemName = itemName;
    }

    public Long getItemId() {
      return itemId;
    }

    public void setItemId(Long itemId) {
      this.itemId = itemId;
    }

    public int getQuesCount() {
      return quesCount;
    }

    public void setQuesCount(int quesCount) {
      this.quesCount = quesCount;
    }

    public void addQuesCount(int quesCount) {
      this.quesCount += quesCount;
    }

    public List<KeyPoint> getKeyPointList() {
      return keyPointList;
    }

    public void setKeyPointList(List<KeyPoint> keyPointList) {
      this.keyPointList = keyPointList;
    }
  }

  /**
   * 考点
   */
  public static class KeyPoint {
    /**
     * 考点名称
     */
    private String keyPointName;
    /**
     * 考点ID
     */
    private String keyPointId;
    /**
     * 问题数量
     */
    private int quesCount = 0;
    /**
     * 未掌握问题数量
     */
    private int uncatchQuesCount = 0;
    /**
     * 待强化问题数量
     */
    private int catchingQuesCount = 0;
    /**
     * 已消灭问题数量
     */
    private int catchedQuesCount = 0;

    public String getKeyPointName() {
      return keyPointName;
    }

    public void setKeyPointName(String keyPointName) {
      this.keyPointName = keyPointName;
    }

    public String getKeyPointId() {
      return keyPointId;
    }

    public void setKeyPointId(String keyPointId) {
      this.keyPointId = keyPointId;
    }

    public int getQuesCount() {
      return quesCount;
    }

    public void setQuesCount(int quesCount) {
      this.quesCount = quesCount;
    }

    public void addQuesCount(int quesCount) {
      this.quesCount += quesCount;
    }

    public int getUncatchQuesCount() {
      return uncatchQuesCount;
    }

    public void setUncatchQuesCount(int uncatchQuesCount) {
      this.uncatchQuesCount = uncatchQuesCount;
    }

    public int getCatchingQuesCount() {
      return catchingQuesCount;
    }

    public void setCatchingQuesCount(int catchingQuesCount) {
      this.catchingQuesCount = catchingQuesCount;
    }

    public int getCatchedQuesCount() {
      return catchedQuesCount;
    }

    public void setCatchedQuesCount(int catchedQuesCount) {
      this.catchedQuesCount = catchedQuesCount;
    }
  }
}
