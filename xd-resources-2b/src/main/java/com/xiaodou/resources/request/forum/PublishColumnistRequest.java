package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name PublishColumnistRequest 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月31日
 * @description 发布专栏请求
 * @version 1.0
 */
public class PublishColumnistRequest extends BaseRequest {

  /** title 标题 */
  @NotEmpty
  private String title;
  /** cover 封面 */
  private String cover;
  /** info 简介 */
  private String info;
  
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getCover() {
    return cover;
  }
  public void setCover(String cover) {
    this.cover = cover;
  }
  public String getInfo() {
    return info;
  }
  public void setInfo(String info) {
    this.info = info;
  }

}
