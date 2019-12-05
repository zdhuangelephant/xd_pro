package com.xiaodou.course.model.user;

import java.sql.Timestamp;

/**
 * Created by zyp on 15/8/23.
 */
public class UserLearnStaticsModel {

  // 主键
  private Integer id;

  // 用户Id
  private Integer userId;

  // 比例
  private Integer ratio;

  // 产品Id
  private Integer productId;

  // 当前进度
  private Integer currentItem;

  // 当前进度
  private String currentItemName;

  // 更新时间
  private Timestamp updateTime;

  // 章节号
  private Integer chapterId;

  // 章节名称
  private String chapterName;

  public Integer getChapterId() {
    return chapterId;
  }

  public void setChapterId(Integer chapterId) {
    this.chapterId = chapterId;
  }

  public String getChapterName() {
    return chapterName;
  }

  public void setChapterName(String chapterName) {
    this.chapterName = chapterName;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getRatio() {
    return ratio;
  }

  public void setRatio(Integer ratio) {
    this.ratio = ratio;
  }

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public Integer getCurrentItem() {
    return currentItem;
  }

  public void setCurrentItem(Integer currentItem) {
    this.currentItem = currentItem;
  }

  public String getCurrentItemName() {
    return currentItemName;
  }

  public void setCurrentItemName(String currentItemName) {
    this.currentItemName = currentItemName;
  }

  public Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
  }
}
