package com.xiaodou.userCenter.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FeedBackCategoryRequest extends BaseRequest {

  @NotEmpty
  private String feedContent;

  /* 类型描述列表 */
  @NotEmpty
  private String categoryDescList;

  /* 邮箱或者手机号 */
  @NotEmpty
  private String number;

  /* 图片列表 */
  private String imageUrlList;

  /* 手机设备类型 */
  @NotEmpty
  private String deviceType;

  /* os版本 */
  @NotEmpty
  private String osVersion;

  @NotEmpty
  private String userId;

  // @Override
  // public Errors validate() {
  // Errors errors = super.validate();
  // if (errors.hasErrors()) return errors;
  // if (StringUtils.isNotBlank(feedContent) && feedContent.length() > 1000)
  // errors.rejectValue("feedContent", null, "feedContent is too long.");
  // return errors;
  // }
}
