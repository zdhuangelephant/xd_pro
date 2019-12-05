package com.xiaodou.course.vo.product;

import java.util.List;

/**
 * Created by zyp on 15/8/9.
 */
public class Chapter {

  // 章节ID
  private Integer chapterId;

  // 章节名称
  private String chapterName;

  // 重要度
  private Integer importance;

  // 是否收费
  private Integer free;

  // 子章节
  private List<ChildChapter> childList;

  // 章节别名
  private String chapterIdAlias;

  // 资源列表
  private List<ChapterResource> resourceList;

  // 题目数
  private Integer questionNum;

  public Integer getQuestionNum() {
    return questionNum;
  }

  public void setQuestionNum(Integer questionNum) {
    this.questionNum = questionNum;
  }

  public List<ChapterResource> getResourceList() {
    return resourceList;
  }

  public void setResourceList(List<ChapterResource> resourceList) {
    this.resourceList = resourceList;
  }

  public List<ChildChapter> getChildList() {
    return childList;
  }

  public void setChildList(List<ChildChapter> childList) {
    this.childList = childList;
  }

  public String getChapterIdAlias() {
    return chapterIdAlias;
  }

  public void setChapterIdAlias(String chapterIdAlias) {
    this.chapterIdAlias = chapterIdAlias;
  }

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

}
