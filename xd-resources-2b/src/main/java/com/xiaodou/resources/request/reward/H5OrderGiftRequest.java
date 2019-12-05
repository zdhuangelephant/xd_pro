package com.xiaodou.resources.request.reward;


public class H5OrderGiftRequest extends OrderGiftRequest {

  /* h5Type 网页支付类型 */
  private String h5Type;
  /* weixinName 微信名 */
  private String weixinName;
  /* weixinPortrait 微信头像 */
  private String weixinPortrait;

  public String getH5Type() {
    return h5Type;
  }

  public void setH5Type(String h5Type) {
    this.h5Type = h5Type;
  }

  public String getWeixinName() {
    return weixinName;
  }

  public void setWeixinName(String weixinName) {
    this.weixinName = weixinName;
  }

  public String getWeixinPortrait() {
    return weixinPortrait;
  }

  public void setWeixinPortrait(String weixinPortrait) {
    this.weixinPortrait = weixinPortrait;
  }
}
