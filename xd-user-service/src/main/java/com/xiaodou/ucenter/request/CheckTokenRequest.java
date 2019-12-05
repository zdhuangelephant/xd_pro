package com.xiaodou.ucenter.request;

import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * @name @see com.xiaodou.ucenter.request.CheckTokenRequest.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年9月12日
 * @description 校验token参数pojo
 * @version 1.0
 */
@AddValidField(field = {"sessionToken"}, annotiation = AnnotationType.NotEmpty)
public class CheckTokenRequest extends BaseRequest {
}