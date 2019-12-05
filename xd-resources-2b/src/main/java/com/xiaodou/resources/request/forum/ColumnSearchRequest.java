package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * @name ColumnSearchRequest CopyRright (c) 2016 by zhaodan
 * 
 * @author zhaodan
 * @date 2016年9月5日
 * @description 专栏查询请求类
 * @version 1.0
 */
@OverComeField(annotiation = AnnotationType.NotEmpty, field = {"uid"})
public class ColumnSearchRequest extends BaseRequest {

  /** title 专栏标题 */
  @NotEmpty
  private String title;
  /** lastColumnistId 最后一条专栏ID */
  private String lastColumnistId;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLastColumnistId() {
    return lastColumnistId;
  }

  public void setLastColumnistId(String lastColumnistId) {
    this.lastColumnistId = lastColumnistId;
  }

}
