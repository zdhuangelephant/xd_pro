package com.xiaodou.mooccrawler.web.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseRequest;

import java.sql.Timestamp;

/**
 * Created by zyp on 15/4/19.
 */
public class CategoryEditRequest extends BaseRequest {

  // 课程分类ID
  @NotEmpty
  private Integer id;

  // 课程分类父ID
  private Integer parentId;

  // 课程分类名称
  private String name;

  // 课程分类详细介绍
  private String detail;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

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
