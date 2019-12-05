package com.xiaodou.resources.response.forum;

import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.resources.response.forum.ColumnistCountResponse.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年9月8日
 * @description 专栏数量响应
 * @version 1.0
 */
public class ColumnistCountResponse extends BaseResponse {
  public ColumnistCountResponse(ResultType type) {
    super(type);
  }

  public ColumnistCountResponse(ForumResType type) {
    super(type);
  }

  /** columnistCount 发布专栏数量 */
  private Integer columnistCount = 0;

  public Integer getColumnistCount() {
    return columnistCount;
  }

  public void setColumnistCount(Integer columnistCount) {
    if (null == columnistCount) return;
    this.columnistCount = columnistCount;
  }

}
