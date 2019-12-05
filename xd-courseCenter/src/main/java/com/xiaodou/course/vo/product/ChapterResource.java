package com.xiaodou.course.vo.product;

/**
 * Created by zyp on 15/8/9.
 */
public class ChapterResource {

  // 资源Id
  private Integer resourceId;

  // 名称
  private String resourceName;

  // 类型
  private Integer resourceType;

  // 是否收费
  private Integer free;

  public Integer getFree() {
    return free;
  }

  public void setFree(Integer free) {
    this.free = free;
  }

  public Integer getResourceId() {
    return resourceId;
  }

  public void setResourceId(Integer resourceId) {
    this.resourceId = resourceId;
  }

  public String getResourceName() {
    return resourceName;
  }

  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }

  public Integer getResourceType() {
    return resourceType;
  }

  public void setResourceType(Integer resourceType) {
    this.resourceType = resourceType;
  }
}
