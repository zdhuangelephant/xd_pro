package com.xiaodou.userCenter.request;

import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;
import com.xiaodou.userCenter.model.UserModel;

@OverComeField(annotiation = AnnotationType.NotEmpty, field = {"confirmPassWord", "checkCode"})
public abstract class OfficialRegistRequest extends RegistAccountRequest {

  public OfficialRegistRequest(UserBaseInfo info) {
    super(info);
  }

  public abstract void setRegistOfficialInfo(UserModel model);
}
