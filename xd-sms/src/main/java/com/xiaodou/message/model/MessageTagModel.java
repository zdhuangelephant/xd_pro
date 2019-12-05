package com.xiaodou.message.model;

import java.sql.Timestamp;

public class MessageTagModel {
  //主键id
  private Long id;
  //标识名称
  private String tagName;
  //状态
  private Integer status;
  //创建时间
  private Timestamp createTime;
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getTagName() {
    return tagName;
  }
  public void setTagName(String tagName) {
    this.tagName = tagName;
  }
  public Integer getStatus() {
    return status;
  }
  public void setStatus(Integer status) {
    this.status = status;
  }
  public Timestamp getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }
  @Override
  public String toString() {
    return "MessageTagModel [id=" + id + ", tagName=" + tagName + ", status=" + status
        + ", createTime=" + createTime + "]";
  }
}
