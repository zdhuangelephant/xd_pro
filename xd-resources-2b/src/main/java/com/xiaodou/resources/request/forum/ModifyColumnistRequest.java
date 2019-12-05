package com.xiaodou.resources.request.forum;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * @name ModifyColumnistRequest CopyRright (c) 2016 by zhaodan
 * 
 * @author zhaodan
 * @date 2016年8月31日
 * @description 修改专栏请求
 * @version 1.0
 */
@OverComeField(annotiation = AnnotationType.NotEmpty, field = {"title"})
public class ModifyColumnistRequest extends PublishColumnistRequest {

  /** columnistId 专栏ID */
  @NotEmpty
  private String columnistId;

  public String getColumnistId() {
    return columnistId;
  }

  public void setColumnistId(String columnistId) {
    this.columnistId = columnistId;
  }

}
