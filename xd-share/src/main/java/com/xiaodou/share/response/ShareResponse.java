package com.xiaodou.share.response;

import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.share.response.ShareResponse.java 
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年9月7日
 * @description 分享请求应答
 * @version 1.0
 */
public class ShareResponse extends ResultInfo {

  /** content 分享内容 */
  private String content;
  /** title 分享标题 */
  private String title;
  /** imageUrl 分享图片地址 */
  private String imageUrl;
  /** url 分享跳转链接 */
  private String url;

  public ShareResponse(ResultType type) {
    super(type);
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

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

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }


}
