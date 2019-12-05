package com.xiaodou.userCenter.request;

import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;

@OverComeField(annotiation = AnnotationType.NotEmpty, field = {"module"})
public class ConfigRequest extends BaseRequest {

}
