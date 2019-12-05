package com.xiaodou.course.vo.user;

import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;


public class ChapterRecord {
  private String chapterId;// 章ID
  private String chapterName;// 章名称
  private String chapterIndex;// 章序号 eg:"第一章"
  private String starLevel; // 星级 0 0颗 1 一星 2 两星 3 三星
  private String score; // 得分
  private List<String> topUserList = Lists.newArrayList();
  private Integer completeCount = 0;
  private String pictureUrl;

  private Integer learnedItemCount = 0;// 已学习节数量
  private Integer totalItemCount = 0;// 节总数量
  private List<ItemRecord> itemList = Lists.newArrayList();// 节列表

  /** relationItem 关联id update:2018-02-28 */
  private Integer relationItem;

  public ChapterRecord(UserChapterRecordVo chapterRecordVo) {
    this.chapterId = chapterRecordVo.getChapterId().toString();
    this.chapterName = chapterRecordVo.getChapterName();
    this.chapterIndex = chapterRecordVo.getChapterIndex();
    this.setStarLevel(chapterRecordVo.getStarLevel().toString());
    this.setScore(chapterRecordVo.getScore().toString());
    this.setCompleteCount(chapterRecordVo.getCompleteCount());
    if (StringUtils.isJsonNotBlank(chapterRecordVo.getTopUserList()))
      this.setTopUserList(FastJsonUtil.fromJsons(chapterRecordVo.getTopUserList(),
          new TypeReference<List<String>>() {}));
    this.setPictureUrl(chapterRecordVo.getPictureUrl());
    this.setRelationItem(chapterRecordVo.getRelationItem());
  }

  public void setInfo(UserChapterRecordVo chapterRecordVo) {
    if (StringUtils.isBlank(this.chapterId))
      this.chapterId = chapterRecordVo.getChapterId().toString();
    if (StringUtils.isBlank(this.chapterName)) this.chapterName = chapterRecordVo.getChapterName();
    if (StringUtils.isBlank(this.chapterIndex))
      this.chapterIndex = chapterRecordVo.getChapterIndex();
    if (StringUtils.isBlank(this.starLevel))
      this.starLevel = chapterRecordVo.getStarLevel().toString();
    if (StringUtils.isBlank(this.score)) this.score = chapterRecordVo.getScore().toString();
    this.completeCount = chapterRecordVo.getCompleteCount();
    if (StringUtils.isJsonNotBlank(chapterRecordVo.getTopUserList()))
      this.topUserList =
          FastJsonUtil.fromJsons(chapterRecordVo.getTopUserList(),
              new TypeReference<List<String>>() {});
    if (StringUtils.isBlank(this.pictureUrl)) this.pictureUrl = chapterRecordVo.getPictureUrl();
    if (null == this.relationItem) {
      this.relationItem = chapterRecordVo.getRelationItem();
    }
  }

  public ChapterRecord() {}

  public String getChapterId() {
    return chapterId;
  }

  public void setChapterId(String chapterId) {
    this.chapterId = chapterId;
  }

  public Integer getLearnedItemCount() {
    return learnedItemCount;
  }

  public void setLearnedItemCount(Integer learnedItemCount) {
    this.learnedItemCount = learnedItemCount;
  }

  public void addLearnedItemCount() {
    this.learnedItemCount++;
  }

  public Integer getTotalItemCount() {
    return totalItemCount;
  }

  public void setTotalItemCount(Integer totalItemCount) {
    this.totalItemCount = totalItemCount;
  }

  public void addTotalItemCount() {
    this.totalItemCount++;
  }

  public List<ItemRecord> getItemList() {
    return itemList;
  }

  public void setItemList(List<ItemRecord> itemList) {
    this.itemList = itemList;
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

  public String getPictureUrl() {
    return pictureUrl;
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

  public String getStarLevel() {
    return starLevel;
  }

  public void setStarLevel(String starLevel) {
    this.starLevel = starLevel;
  }

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public Integer getCompleteCount() {
    return completeCount;
  }

  public void setCompleteCount(Integer completeCount) {
    this.completeCount = completeCount;
  }

  public List<String> getTopUserList() {
    return topUserList;
  }

  public void setTopUserList(List<String> topUserList) {
    this.topUserList = topUserList;
  }

  public Integer getRelationItem() {
    return relationItem;
  }

  public void setRelationItem(Integer relationItem) {
    this.relationItem = relationItem;
  }

}
