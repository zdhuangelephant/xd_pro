package com.xiaodou.resources.enums.forum;

public enum DigestType {

  /* 0-说说 ，1-文章 ，2-视频 */
  TALK(0, "说说"), ACTICLE(1, "文章"), VIDEO(2, "视频");
  private Integer type;
  private String desc;

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  private DigestType(Integer type, String desc) {
    this.type = type;
    this.desc = desc;
  }


}
