package com.xiaodou.ms.model.knowledge;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.annotations.Xml;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

@Data
@Xml(tableName = "xd_resource_fetch_account", outputDir = "src\\main\\resources\\conf\\mybatis\\knowledge")
public class ForumAccount {
  @Column(isMajor = true, autoIncrement = true, persistent = true, sortBy = true, listValue = true)
  private Integer id;
  private Short platform;
  private String signature;
  private Short state;
  private Timestamp createTime;
  
  public static void main(String[] args) {
    MybatisXmlTool.getInstance(ForumAccount.class, "xd_resource_fetch_account").buildXml();
  }

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public Short getPlatform() {
	return platform;
}

public void setPlatform(Short platform) {
	this.platform = platform;
}

public String getSignature() {
	return signature;
}

public void setSignature(String signature) {
	this.signature = signature;
}

public Short getState() {
	return state;
}

public void setState(Short state) {
	this.state = state;
}

public Timestamp getCreateTime() {
	return createTime;
}

public void setCreateTime(Timestamp createTime) {
	this.createTime = createTime;
}
  
}
