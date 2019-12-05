package com.xiaodou.resources.model.quesbk;

import com.xiaodou.resources.model.BaseEntity;
import com.xiaodou.resources.util.QuesbkUtil;

public class QuesbkExamType extends BaseEntity {
  private Long id;

  private Long categoryId;

  private String examTypeName;

  private String status;

  private String mdesc;

  private String misc;

  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public String getExamTypeName() {
    return examTypeName;
  }

  public void setExamTypeName(String examTypeName) {
    this.examTypeName = QuesbkUtil.trim(examTypeName);
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status == null ? null : status.trim();
  }

  public String getMdesc() {
    return mdesc;
  }

  public void setMdesc(String mdesc) {
    this.mdesc = mdesc == null ? null : mdesc.trim();
  }

  public String getMisc() {
    return misc;
  }

  public void setMisc(String misc) {
    this.misc = misc == null ? null : misc.trim();
  }
}
