package com.xiaodou.domain.product;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.domain.BaseEntity;
import com.xiaodou.util.QuesbkUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class QuesbkExamType extends BaseEntity {
  @Column(isMajor = true, betweenScope = true, autoIncrement = true, persistent = true, sortBy = true, listValue = true)
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
  
  
  public static void main(String[] args) {
    MybatisXmlTool
        .getInstance(QuesbkExamType.class, "xd_quesbk_exam_type_name",
            "F:/xdworks/xd-server-quesbk-b20180102/src/main/resources/conf/mybatis/product/")
        .buildXml();;
  }
}