package com.xiaodou.mooccrawler.web.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseRequest;

/**
 * Created by zyp on 15/4/19.
 */
public class CategoryCreateRequest extends BaseRequest {

  // 课程分类父ID
  private Integer parentId = 0;

  // 课程分类名称
  @NotEmpty
  private String name;

  // 课程分类详细介绍
  private String detail;

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }
}
