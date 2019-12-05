package com.xiaodou.share.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

/**
 * @name @see com.xiaodou.share.request.ShareRequest.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年9月7日
 * @description 分享请求入参
 * @version 1.0
 */
public class ShareRequest extends BaseValidatorPojo {

  /** shareType 分享类型 */
  @NotEmpty
  private String shareType;

  public String getShareType() {
    return shareType;
  }

  public void setShareType(String shareType) {
    this.shareType = shareType;
  }

}
