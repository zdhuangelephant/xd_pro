package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 发布话题request
 * 
 * @author bing.cheng
 * 
 */
public class ForumRequest extends BaseRequest {
  private String resourcesId;
  /** 资源名称 */
  private String title;
  

  /** 资源内容 */
  private String content;
  /** 资源概要 */
  private String outline;

  /** 话题图片 前端传入，以逗号分割 */
  private String images;

  /** 文章分类 */
  private String categoryId;

  /** 资源类型 1-说说 ，2-文章 ，3-视频 */
  @NotEmpty
  private Integer digest;

  /** 视频URL */
  private String videoUrl;

  /** 封面URL */
  private String cover;

  /** 专栏ID */
  private String columnId;

  /** clientIp 设备IP */
  @NotEmpty
  private String clientIp;
  
  /** categoryName 分类名称 */
  private String categoryName;

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

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public String getImages() {
    return images;
  }

  public void setImages(String images) {
    this.images = images;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }

  public Integer getDigest() {
    return digest;
  }

  public void setDigest(Integer digest) {
    this.digest = digest;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public String getColumnId() {
    return columnId;
  }

  public void setColumnId(String columnId) {
    this.columnId = columnId;
  }

  public String getClientIp() {
    return clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

public String getCategoryName() {
	return categoryName;
}

public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
}


public String getResourcesId() {
	return resourcesId;
}

public void setResourcesId(String resourcesId) {
	this.resourcesId = resourcesId;
}

public String getOutline() {
	return outline;
}

public void setOutline(String outline) {
	this.outline = outline;
}

}
