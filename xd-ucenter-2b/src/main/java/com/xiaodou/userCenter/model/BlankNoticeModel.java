package com.xiaodou.userCenter.model;

import lombok.Data;

@Data
public class BlankNoticeModel {
  /** id  标识id */
  private Long id;
  /** type  显示频次 0:每日首次， 1：每次启动 2：一次性*/
  private Short type;
  /** jumpUrl 跳转地址 http:// app:// */
  private String jumpUrl;
  /** title 开屏通知标题 */
  private String title;
  /** image 展示地址 http:// */
  private String image;
  /** isVisible 是否显示 */
  private Short isVisible;
}
