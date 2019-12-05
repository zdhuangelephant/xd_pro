package com.xiaodou.ucerCenter.agent.request;

import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.enums.AnnotationType;

@AddValidField(field = {"sessionToken"}, annotiation = AnnotationType.NotEmpty)
public class CheckTokenRequest extends BaseRequest {

}
