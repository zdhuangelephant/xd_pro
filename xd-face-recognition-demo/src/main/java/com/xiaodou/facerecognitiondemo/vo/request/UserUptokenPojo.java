package com.xiaodou.facerecognitiondemo.vo.request;

import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;
import com.xiaodou.userCenter.request.UpTokenPojo;

@OverComeField(field = {"deviceId", "clientIp", "module", "version", "clientType"}, annotiation = AnnotationType.NotEmpty)
public class UserUptokenPojo extends UpTokenPojo {}
