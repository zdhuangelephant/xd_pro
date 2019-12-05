package com.xiaodou.course.web.request.forum;

import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;

@OverComeField(field = {"typeCode", "uid", "module"}, annotiation = AnnotationType.NotEmpty)
public class MajorRequest extends BaseRequest {

}
