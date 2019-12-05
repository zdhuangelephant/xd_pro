package com.xiaodou.server.mapi.request.ucenter;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.enums.AnnotationType;

@AddValidField(field = {"sessionToken"}, annotiation = AnnotationType.NotEmpty)
public class CheckTokenRequest extends MapiBaseRequest {

}
