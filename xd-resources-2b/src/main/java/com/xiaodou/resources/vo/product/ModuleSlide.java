package com.xiaodou.resources.vo.product;

/**
 * Created by zyp on 15/8/16.
 */
public class ModuleSlide {

  /** title 标题 */
  private String title;

  /**
   * 图片地址
   */
  private String imageUrl;

  /**
   * 连接地址
   */
  private String redirectUrl;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getRedirectUrl() {
    return redirectUrl;
  }

  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }
}
