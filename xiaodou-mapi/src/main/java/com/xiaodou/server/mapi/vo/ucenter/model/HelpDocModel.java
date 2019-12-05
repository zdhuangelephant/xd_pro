package com.xiaodou.server.mapi.vo.ucenter.model;

import com.xiaodou.server.mapi.util.TimeUtil;

public class HelpDocModel {
  private Integer id;
  private Integer helpId;
  private short type;// 类型 1 内部页面 2 外链页面
  private String title;
  private String content;
  private String createTime;
  private String module;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getHelpId() {
    return helpId;
  }

  public void setHelpId(Integer helpId) {
    this.helpId = helpId;
  }

  public short getType() {
    return type;
  }

  public void setType(short type) {
    this.type = type;
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

  public String getCreateTime() {
    return TimeUtil.format_yyyy_MM_dd_HH_mm_ss(TimeUtil.parse_yyyy_MM_dd_HH_mm_ss(createTime));
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  @Override
  public String toString() {
    return "HelpDocModel [id=" + id + ", title=" + title + ", content=" + content + ", createTime="
        + createTime + ", module=" + module + "]";
  }

}
