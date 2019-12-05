package com.xiaodou.ms.web.request.user;

import java.sql.Timestamp;

import com.xiaodou.ms.model.user.BlankNoticeModel;
import com.xiaodou.ms.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BlankNoticeRequest extends BaseRequest {
  private Long id;
  // 展示方式：0 每次都展示，1 只展示一次，2 不展示
  // 显示频次 0:每日首次， 1：每次启动 2：一次性
  @NotEmpty
  private Short type;
  // 跳转地址 http:// app://
  private String jumpUrl;
  @NotEmpty
  private String module;
  @NotEmpty
  private String title;
  private String image;
  private Short isInnerLink;
  private Short isVisible;
  private Timestamp updateTime;

  public BlankNoticeModel initModel(BlankNoticeRequest request) {
    BlankNoticeModel model = new BlankNoticeModel();
    model.setId(request.getId());
    model.setType(request.getType());
    model.setJumpUrl(request.getJumpUrl());
    model.setTitle(request.getTitle());
    model.setImage(request.getImage());
    model.setIsInnerLink(request.getIsInnerLink());
    model.setIsVisible(request.getIsVisible());
    return model;
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

public String getModule() {
	return module;
}

public void setModule(String module) {
	this.module = module;
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
