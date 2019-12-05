package com.xiaodou.server.mapi.request.ucenter;

import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;

@OverComeField(annotiation = AnnotationType.NotEmpty, field = {"confirmPassWord", "checkCode"})
public class OfficialRegistRequest extends RegistAccountRequest {

  public OfficialRegistRequest() {}



}
