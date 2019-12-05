package com.xiaodou.userCenter.request;

import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;

@AddValidField(field = {"sessionToken"}, annotiation = AnnotationType.NotEmpty)
@OverComeField(annotiation = AnnotationType.NotEmpty, field = {"module"})
public class CheckTokenRequest extends BaseRequest {

}
