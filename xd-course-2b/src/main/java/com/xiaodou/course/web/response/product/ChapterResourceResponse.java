package com.xiaodou.course.web.response.product;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.course.model.product.ProductItemModel;
import com.xiaodou.course.vo.product.MicroVideo;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.course.web.response.product.ChapterResourceResponse.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月7日
 * @description 章节资源接口响应
 * @version 1.0
 */
public class ChapterResourceResponse extends BaseResponse {

  public ChapterResourceResponse(ResultType type) {
    super(type);
  }

  public ChapterResourceResponse(ProductResType productResType) {
    super(productResType);
  }

  /** courseId 课程ID */
  private String courseId;
  /** chapterId 章ID */
  private String chapterId;
  /** nextChapterId 下一节所属章ID */
  private Long nextChapterId;
  /** itemId 节ID */
  private String itemId;
  /** nextItemId 下一节ID */
  private Long nextItemId;
  /** courseName 课程名称 */
  private String courseName;
  /** chapterName 章名称 */
  private String chapterName;
  /** itemName 节名称 */
  private String itemName;
  /** resourceUrl 资源路径 */
  private String resourceUrl;
  /** resourceType 资源类型 */
  private Integer resourceType;
  /** resourceContent 资源描述 */
  private String resourceContent;

  /** previousChapterId 上一章id */
  private Long previousChapterId;
  /** previousItemId 上一节id */
  private Long previousItemId;

  /** webUrl 提供web端使用 */
  private String webUrl;

  // 视频封面
  private List<MicroVideo> points = Lists.newArrayList();
  /** itemDuration 预留字段，本节总时长 */
  private String itemDuration;


  public String getWebUrl() {

    return webUrl;
  }

  public void setWebUrl(String webUrl) {
    this.webUrl = webUrl;
  }

  public Long getPreviousChapterId() {
    return previousChapterId;
  }

  public void setPreviousChapterId(Long previousChapterId) {
    this.previousChapterId = previousChapterId;
  }

  public Long getPreviousItemId() {
    return previousItemId;
  }

  public void setPreviousItemId(Long previousItemId) {
    this.previousItemId = previousItemId;
  }

  public void setNextChapterId(Long nextChapterId) {
    this.nextChapterId = nextChapterId;
  }

  public void setNextItemId(Long nextItemId) {
    this.nextItemId = nextItemId;
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

  
  public Long getNextChapterId() {
    return nextChapterId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

 

  public Long getNextItemId() {
    return nextItemId;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public String getChapterName() {
    return chapterName;
  }

  public void setChapterName(String chapterName) {
    this.chapterName = chapterName;
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

  public Integer getResourceType() {
    return resourceType;
  }

  public void setResourceType(Integer resourceType) {
    this.resourceType = resourceType;
  }

  public String getResourceContent() {
    return resourceContent;
  }

  public void setResourceContent(String resourceContent) {
    this.resourceContent = resourceContent;
  }

  public void setNextItemInfo(ProductItemModel nextItem) {
    if (null == nextItem) {
      this.nextChapterId = -1L;
      this.nextItemId = -1L;
    } else {
      this.nextChapterId = nextItem.getParentId();
      this.nextItemId = nextItem.getId();
    }
  }

  public void setPreviousItemInfo(ProductItemModel previousItem) {
    if (null == previousItem) {
      this.previousChapterId = -1L;
      this.previousItemId = -1L;
    } else {
      this.previousChapterId = previousItem.getParentId();
      this.previousItemId = previousItem.getId();
    }
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
