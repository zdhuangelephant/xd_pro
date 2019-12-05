package com.xiaodou.ucerCenter.agent.model;


public class NoticeModel {

  private Integer id;

  private Short type;

  private String title;

  private String content;

  private String createTime;

  private String updateUser;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUpdateUser() {
    return updateUser;
  }

  public void setUpdateUser(String updateUser) {
    this.updateUser = updateUser;
  }

  public Short getType() {
    return type;
  }

  public void setType(Short type) {
    this.type = type;
  }

  public String getCreateTime() {
    return createTime;
    //return DateUtil.relativeDateFormat(Timestamp.valueOf(createTime));
    // return TimeUtil.format_yyyy_MM_dd_HH_mm_ss(TimeUtil.parse_yyyy_MM_dd_HH_mm_ss(createTime));
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
