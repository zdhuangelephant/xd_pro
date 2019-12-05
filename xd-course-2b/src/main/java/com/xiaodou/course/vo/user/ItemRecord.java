package com.xiaodou.course.vo.user;

import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.MathUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.constant.Constant;
import com.xiaodou.course.model.product.FinalExamModel;

public class ItemRecord {
  private String itemId;// 节ID
  private String itemName;// 节名称
  private String itemIndex;// 节序号 eg:"第一节"
  private String starLevel; // 星级 0 0颗 1 一星 2 两星 3 三星
  private String score; // 得分
  private List<String> topUserList = Lists.newArrayList();
  private Integer completeCount = 0;
  private String pictureUrl;
  private String itemType = "1";// 0 章 1节 2期末测试 3章总结精讲
  private String status = "0";
  private String deductPoint = "0";
  private String lock = "0";// 0无锁，1加锁

  private String progress;
  private String resourceType;

  public ItemRecord() {}

  public ItemRecord(UserChapterRecordVo vo) {
    this.itemId = vo.getChapterId().toString();
    this.itemName = vo.getChapterName();
    this.itemIndex = vo.getChapterIndex();
    this.starLevel = vo.getStarLevel().toString();
    this.score = MathUtil.getIntStringValue(vo.getScore().toString());
    this.completeCount = vo.getCompleteCount();
    if (StringUtils.isJsonNotBlank(vo.getTopUserList())) this.topUserList =
        FastJsonUtil.fromJsons(vo.getTopUserList(), new TypeReference<List<String>>() {});
    this.pictureUrl = vo.getPictureUrl();
    this.status = vo.getStatus().toString();
    this.deductPoint = "0";
    this.resourceType = vo.getResourceType() + "";
    this.progress = vo.getProgress();
  }

  public ItemRecord(FinalExamModel r) {
    this.itemId = r.getId().toString();
    this.itemName = r.getExamName();
    this.itemIndex = "";
    this.pictureUrl = "";
    if (r.getScore() != null) {
      if (Double.valueOf(r.getScore()) >= 0 && Double.valueOf(r.getScore()) < 60) {
        this.starLevel = "0";
      } else if (Double.valueOf(r.getScore()) >= 60 && (Double.valueOf(r.getScore()) < 80)) {
        this.starLevel = "1";
      } else if (Double.valueOf(r.getScore()) >= 80 && (Double.valueOf(r.getScore()) < 100)) {
        this.starLevel = "2";
      } else if ((Double.valueOf(r.getScore()) == 100)) {
        this.starLevel = "3";
      }
      this.score = MathUtil.getIntStringValue(r.getScore().toString());
    } else {
      this.starLevel = "0";
      this.score = "0";
    }

    this.status = r.getStatus().toString();
    this.deductPoint = Constant.FINAL_SCORE.toString();
    this.itemType = "2";
  }


  public ItemRecord(UserChapterRecordVo vo, String itemType) {
    this.itemId = vo.getChapterId().toString();
    // this.itemName = "习题解析"; modified by zdhuang at 2018-6-21 16:36:54
    this.itemName = vo.getChapterName();
    this.itemIndex = "章总结";
    this.starLevel = vo.getStarLevel().toString();
    this.score = MathUtil.getIntStringValue(vo.getScore().toString());
    this.completeCount = vo.getCompleteCount();
    if (StringUtils.isJsonNotBlank(vo.getTopUserList())) this.topUserList =
        FastJsonUtil.fromJsons(vo.getTopUserList(), new TypeReference<List<String>>() {});
    this.pictureUrl = vo.getPictureUrl();
    this.itemType = itemType;
    this.status = vo.getStatus().toString();
    this.deductPoint = Constant.CHAPTER_SCORE.toString();
//    this.resourceType = vo.getResourceType().toString();
//    this.progress = vo.getProgress();
  }

  public ItemRecord(ChapterRecord chapter, String itemType) {
    this.itemId = chapter.getChapterId().toString();
    this.itemName = "习题解析";
    this.itemIndex = "章总结";
    this.starLevel = chapter.getStarLevel().toString();
    this.score = MathUtil.getIntStringValue(chapter.getScore().toString());
    this.completeCount = chapter.getCompleteCount();
    this.topUserList = chapter.getTopUserList();
    this.pictureUrl = chapter.getPictureUrl();
    this.itemType = itemType;
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

  public String getItemIndex() {
    return itemIndex;
  }

  public void setItemIndex(String itemIndex) {
    this.itemIndex = itemIndex;
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

  public List<String> getTopUserList() {
    return topUserList;
  }

  public void setTopUserList(List<String> topUserList) {
    this.topUserList = topUserList;
  }

  public Integer getCompleteCount() {
    return completeCount;
  }

  public void setCompleteCount(Integer completeCount) {
    this.completeCount = completeCount;
  }

  public String getPictureUrl() {
    return pictureUrl;
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

  public String getItemType() {
    return itemType;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDeductPoint() {
    return deductPoint;
  }

  public void setDeductPoint(String deductPoint) {
    this.deductPoint = deductPoint;
  }

  public String getLock() {
    return lock;
  }

  public void setLock(String lock) {
    this.lock = lock;
  }

  public String getProgress() {
    return progress;
  }

  public void setProgress(String progress) {
    this.progress = progress;
  }

  public String getResourceType() {
    return resourceType;
  }

  public void setResourceType(String resourceType) {
    this.resourceType = resourceType;
  }

}
