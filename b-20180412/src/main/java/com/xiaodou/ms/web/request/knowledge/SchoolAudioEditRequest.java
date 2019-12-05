package com.xiaodou.ms.web.request.knowledge;

import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * @name @see com.xiaodou.ms.web.request.user.SchoolAudioAddRequest.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月13日
 * @description 校园之声修改请求类
 * @version 1.0
 */
@AddValidField(annotiation = AnnotationType.NotEmpty, field = {"forumId", "forumType",
    "forumClassify", "forumCover", "forumTitle", "forumMedia"})
public class SchoolAudioEditRequest extends ForumRequest {}
