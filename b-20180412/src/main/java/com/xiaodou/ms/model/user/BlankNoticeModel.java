package com.xiaodou.ms.model.user;


import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

@Data
public class BlankNoticeModel {
  // 标识id
  @Column(isMajor = true, betweenScope = true, persistent = true)
  private Long id;
  // 展示方式：0 每次都展示，1 只展示一次，2 不展示
  // 显示频次 0:每日首次， 1：每次启动 2：一次性
  private Short type;
  // 跳转地址 http:// app://
  private String jumpUrl;

  /*private String module;*/

  // app所属模块 // modify by zdh at 15:37:21
  /*private String moduleName;*/

  private String title;
  private String image;
  private Short isInnerLink;
  private Short isVisible;
  private Timestamp updateTime;

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(BlankNoticeModel.class, "xd_user_blank_notice",
        "E:\\xdworks\\xd-ms2b\\src\\main\\resources\\conf\\mybatis").buildXml();
  }

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Short getType() {
	return type;
}

public void setType(Short type) {
	this.type = type;
}

public String getJumpUrl() {
	return jumpUrl;
}

public void setJumpUrl(String jumpUrl) {
	this.jumpUrl = jumpUrl;
}

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public String getImage() {
	return image;
}

public void setImage(String image) {
	this.image = image;
}

public Short getIsInnerLink() {
	return isInnerLink;
}

public void setIsInnerLink(Short isInnerLink) {
	this.isInnerLink = isInnerLink;
}

public Short getIsVisible() {
	return isVisible;
}

public void setIsVisible(Short isVisible) {
	this.isVisible = isVisible;
}

public Timestamp getUpdateTime() {
	return updateTime;
}

public void setUpdateTime(Timestamp updateTime) {
	this.updateTime = updateTime;
}

}
