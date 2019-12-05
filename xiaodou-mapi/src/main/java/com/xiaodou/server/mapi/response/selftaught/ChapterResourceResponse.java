package com.xiaodou.server.mapi.response.selftaught;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.vo.product.MicroVideo;
import com.xiaodou.server.mapi.vo.response.Comment;
import com.xiaodou.summer.vo.out.ResultType;

public class ChapterResourceResponse extends BaseResponse {
  public ChapterResourceResponse() {}

  public ChapterResourceResponse(ResultType type) {
    super(type);
  }

  private String courseId;
  private String chapterId;
  private String nextChapterId;
  private String itemId;
  private String nextItemId;
  private String itemName;
  private String resourceUrl;
  private String resourceType;
  private String resourceContent;
  private List<Comment> list = Lists.newArrayList();
  /** previousChapterId 上一章id */
  private String previousChapterId;
  /** previousItemId 上一节id */
  private String previousItemId;

  /** webUrl 提供web端使用 */
  private String webUrl;
  // 节下的考点
  private List<MicroVideo> points = Lists.newArrayList();
  /** itemDuration 预留字段，本节总时长 */
  private String itemDuration;

  public String getWebUrl() {
    return webUrl;
  }

  public void setWebUrl(String webUrl) {
    this.webUrl = webUrl;
  }

  public String getPreviousChapterId() {
    return previousChapterId;
  }

  public void setPreviousChapterId(String previousChapterId) {
    this.previousChapterId = previousChapterId;
  }

  public String getPreviousItemId() {
    return previousItemId;
  }

  public void setPreviousItemId(String previousItemId) {
    this.previousItemId = previousItemId;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getChapterId() {
    return chapterId;
  }

  public void setChapterId(String chapterId) {
    this.chapterId = chapterId;
  }

  public String getNextChapterId() {
    return nextChapterId;
  }

  public void setNextChapterId(String nextChapterId) {
    this.nextChapterId = nextChapterId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getNextItemId() {
    return nextItemId;
  }

  public void setNextItemId(String nextItemId) {
    this.nextItemId = nextItemId;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public String getResourceUrl() {
    return resourceUrl;
  }

  public void setResourceUrl(String resourceUrl) {
    this.resourceUrl = resourceUrl;
  }

  public String getResourceType() {
    return resourceType;
  }

  public void setResourceType(String resourceType) {
    this.resourceType = resourceType;
  }

  public String getResourceContent() {
    return resourceContent;
  }

  public void setResourceContent(String resourceContent) {
    this.resourceContent = resourceContent;
  }

  public List<Comment> getList() {
    return list;
  }

  public void setList(List<Comment> list) {
    this.list = list;
  }

  public List<MicroVideo> getPoints() {
    return points;
  }

  public void setPoints(List<MicroVideo> points) {
    this.points = points;
  }

  public String getItemDuration() {
    return itemDuration;
  }

  public void setItemDuration(String itemDuration) {
    this.itemDuration = itemDuration;
  }




}
