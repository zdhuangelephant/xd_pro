package com.xiaodou.ms.web.request.knowledge;

import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * @name @see com.xiaodou.ms.web.request.knowledge.ShareKnowledgeAddRequest.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月14日
 * @description 知识分享添加请求类
 * @version 1.0
 */
@AddValidField(annotiation = AnnotationType.NotEmpty, field = {"forumType", "forumClassify",
    "forumCover", "forumTitle", "forumTag", "status", "authorId", "region"})
public class ShareKnowledgeAddRequest extends ForumRequest {

}
