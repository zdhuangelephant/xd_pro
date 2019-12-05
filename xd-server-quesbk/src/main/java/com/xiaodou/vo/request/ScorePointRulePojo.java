package com.xiaodou.vo.request;

import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * @name @see com.xiaodou.vo.request.ScorePointRulePojo.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月24日
 * @description 计分点规则详情参数类
 * @version 1.0
 */
@OverComeField(annotiation = AnnotationType.NotEmpty, field = {"uid", "typeCode"})
public class ScorePointRulePojo extends QuesBasePojo {}
