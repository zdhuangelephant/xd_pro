package com.xiaodou.ms.model.feedback;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

@Data
public class UserFeedBack {
  @Column(isMajor = true, betweenScope = true, persistent = true)
  private Long id;
  /* 内容 */
  private String content;
  /* 副标题 */
  private String subtitle;
  /* 创建时间 */
  @Column(canUpdate = false)
  private Timestamp createTime;
  private Timestamp updateTime;

  /* 类型描述列表 */
  @Column(canUpdate = false, sortBy = false)
  private String categoryDescs;

  /* 邮箱或者手机号 */
  @Column(canUpdate = false)
  private String number;

  /* 图片列表 */
  @Column(canUpdate = false, sortBy = false)
  private String imageUrls;

  /* 用户id */
  @Column(canUpdate = false)
  private Integer userId;
  /* 用户手机号 */
  @Column(canUpdate = false)
  private String telephone;
  /* 设备类型 */
  @Column(canUpdate = false)
  private String deviceType;

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(UserFeedBack.class, "xd_user_feedback",
        "D:/snippets/eclipseWorks/xiaodou-ms-2b/src/main/resources/conf/mybatis/").buildXml();
  }

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getContent() {
	return content;
}

public void setContent(String content) {
	this.content = content;
}

public String getSubtitle() {
	return subtitle;
}

public void setSubtitle(String subtitle) {
	this.subtitle = subtitle;
}

public Timestamp getCreateTime() {
	return createTime;
}

public void setCreateTime(Timestamp createTime) {
	this.createTime = createTime;
}

public Timestamp getUpdateTime() {
	return updateTime;
}

public void setUpdateTime(Timestamp updateTime) {
	this.updateTime = updateTime;
}

public String getCategoryDescs() {
	return categoryDescs;
}

public void setCategoryDescs(String categoryDescs) {
	this.categoryDescs = categoryDescs;
}

public String getNumber() {
	return number;
}

public void setNumber(String number) {
	this.number = number;
}

public String getImageUrls() {
	return imageUrls;
}

public void setImageUrls(String imageUrls) {
	this.imageUrls = imageUrls;
}

public Integer getUserId() {
	return userId;
}

public void setUserId(Integer userId) {
	this.userId = userId;
}

public String getTelephone() {
	return telephone;
}

public void setTelephone(String telephone) {
	this.telephone = telephone;
}

public String getDeviceType() {
	return deviceType;
}

public void setDeviceType(String deviceType) {
	this.deviceType = deviceType;
}
  
}
