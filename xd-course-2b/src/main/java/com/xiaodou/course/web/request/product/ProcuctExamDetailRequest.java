package com.xiaodou.course.web.request.product;

import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.enums.AnnotationType;

@AddValidField(field = {"majorId"}, annotiation = AnnotationType.NotEmpty)
public class ProcuctExamDetailRequest extends BaseRequest {}
