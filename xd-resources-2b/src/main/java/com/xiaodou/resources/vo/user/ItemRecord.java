package com.xiaodou.resources.vo.user;

import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;


public class ItemRecord {
  private String itemId;// 节ID
  private String itemName;// 节名称
  private String itemIndex;// 节序号 eg:"第一节"
  private String starLevel; // 星级 0 0颗 1 一星 2 两星 3 三星
  private String score; // 得分
  private List<String> topUserList = Lists.newArrayList();
  private Integer completeCount = 0;
  private String pictureUrl;

  public ItemRecord(UserChapterRecordVo vo) {
    this.itemId = vo.getChapterId().toString();
    this.itemName = vo.getChapterName();
    this.itemIndex = vo.getChapterIndex();
    this.starLevel = vo.getStarLevel().toString();
    this.score = vo.getScore().toString();
    this.completeCount = vo.getCompleteCount();
    if (StringUtils.isJsonNotBlank(vo.getTopUserList()))
      this.topUserList =
          FastJsonUtil.fromJsons(vo.getTopUserList(), new TypeReference<List<String>>() {});
    this.pictureUrl = vo.getPictureUrl();
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

}
