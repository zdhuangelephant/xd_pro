package com.xiaodou.resources.vo.product;

import java.util.List;

/**
 * Created by zyp on 15/8/9.
 */
public class ChildChapter {

  // 章节ID
  private Integer itemId;

  // 章节名称
  private String itemName;

  // 重要度
  private Integer importance;

  // 是否收费
  private Integer free;

  // 资源列表
  private List<ChapterResource> resourceList;

  // 章节别名
  private String chapterIdAlias;

  // 题目数量
  private Integer questionNum;

  public Integer getQuestionNum() {
    return questionNum;
  }

  public void setQuestionNum(Integer questionNum) {
    this.questionNum = questionNum;
  }

  public Integer getItemId() {
    return itemId;
  }

  public void setItemId(Integer itemId) {
    this.itemId = itemId;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public String getChapterIdAlias() {
    return chapterIdAlias;
  }

  public void setChapterIdAlias(String chapterIdAlias) {
    this.chapterIdAlias = chapterIdAlias;
  }

  public Integer getImportance() {
    return importance;
  }

  public void setImportance(Integer importance) {
    this.importance = importance;
  }

  public Integer getFree() {
    return free;
  }

  public void setFree(Integer free) {
    this.free = free;
  }

  public List<ChapterResource> getResourceList() {
    return resourceList;
  }

  public void setResourceList(List<ChapterResource> resourceList) {
    this.resourceList = resourceList;
  }
}
