package com.xiaodou.userCenter.module.selfTaught.request;

import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.userCenter.request.ConfigRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class StConfigRequest extends ConfigRequest {
  /** majorList 专业列表 */
  @LegalValueList({"0", "1"})
  private String majorList;

  /** thirdlogin 三方登录 */
  @LegalValueList({"0", "1"})
  private String thirdlogin;
  /** shareplateform 分享平台 */
  @LegalValueList({"0", "1"})
  private String shareplateform;
  /** blankNotice 系统公告和活动 */
  @LegalValueList({"0", "1"})
  private String blankNotice;
  /** feedBackCategory 意见反馈类别 */
  @LegalValueList({"0", "1"})
  private String feedbackCategory;

}
