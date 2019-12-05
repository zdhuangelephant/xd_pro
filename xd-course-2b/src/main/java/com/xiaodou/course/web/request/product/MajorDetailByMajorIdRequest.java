package com.xiaodou.course.web.request.product;

import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;
@OverComeField(field = {"module","uid","typeCode"},annotiation = AnnotationType.NotEmpty)
@AddValidField(field = {"majorId"},annotiation = AnnotationType.NotEmpty)
public class MajorDetailByMajorIdRequest extends BaseRequest{}
