package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * @name ColumnistDetailRequest 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月31日
 * @description 专栏详情请求
 * @version 1.0
 */
@OverComeField(annotiation = AnnotationType.NotEmpty, field = {"uid"})
public class ColumnistDetailRequest extends BaseRequest {

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
