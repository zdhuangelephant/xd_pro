package com.xiaodou.resources.model.quesbk;

import com.xiaodou.resources.model.BaseEntity;

public class QuesbkQuesType extends BaseEntity {
  private Long id;

  private String typeName;

  private String status;

  private String mdesc;

  private String misc;

  private Short answerType;

  public Short getAnswerType() {
    return answerType;
  }

  public void setAnswerType(Short answerType) {
    this.answerType = answerType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName == null ? null : typeName.trim();
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
