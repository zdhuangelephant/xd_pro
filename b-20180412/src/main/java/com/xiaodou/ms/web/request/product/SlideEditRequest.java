package com.xiaodou.ms.web.request.product;

import com.xiaodou.common.util.StringUtils;

/**
 * Created by zyp on 15/8/18.
 */
public class SlideEditRequest {

  // 编辑
  private Integer id;

  // 应用Id
  private Integer moduleId;

  // 图片地址
  private String imageUrl;

  // 连接地址
  private String linkUrl;

  // 描述
  private String description;

  // 排序
  private Integer listOrder;

  // 新增数据 moudleId-moduleName
  private String coalition;

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
    if(StringUtils.isNotBlank(imageUrl)) {
      return imageUrl.trim();
    }
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getLinkUrl() {
    if(StringUtils.isNotBlank(linkUrl)) {
      return linkUrl.trim();
    }
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

  public String getCoalition() {
    return coalition;
  }

  public void setCoalition(String coalition) {
    this.coalition = coalition;
  }


}
