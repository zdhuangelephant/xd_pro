package com.xiaodou.ms.model.product;

import java.sql.Timestamp;

/**
 * Created by zyp on 15/8/18.
 */
public class ModuleSlideModel {

  // id
  private Integer id;

  // 模块Id
  private Integer moduleId;

  // 图片地址
  private String imageUrl;

  // 连接地址
  private String linkUrl;

  // 描述
  private String description;

  // 排序
  private Integer listOrder;
  
  // 模块名称
  private String moduleName;

  // 创建时间
  private Timestamp createTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getModuleId() {
    return moduleId;
  }

  public void setModuleId(Integer moduleId) {
    this.moduleId = moduleId;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getLinkUrl() {
    return linkUrl;
  }

  public void setLinkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getListOrder() {
    return listOrder;
  }

  public void setListOrder(Integer listOrder) {
    this.listOrder = listOrder;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

public String getModuleName() {
	return moduleName;
}

public void setModuleName(String moduleName) {
	this.moduleName = moduleName;
}
  
}
