package com.xiaodou.resources.response.product;

import com.xiaodou.resources.model.product.ProductItemModel;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
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

  public void setNextChapterId(Long nextChapterId) {
    this.nextChapterId = nextChapterId;
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

  public void setNextItemId(Long nextItemId) {
    this.nextItemId = nextItemId;
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

  public void setNextItemInfo(ProductItemModel nextItem) {
    if (null == nextItem) {
      this.nextChapterId = -1l;
      this.nextItemId = -1l;
    } else {
      this.nextChapterId = nextItem.getParentId();
      this.nextItemId = nextItem.getId();
    }
  }
}
