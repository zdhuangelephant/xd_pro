package com.xiaodou.vo.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.vo.request.PkResultPojo.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年3月3日
 * @description 获取pk结果参数Pojo
 * @version 1.0
 */
public class PkResultPojo extends QuesBasePojo {

  /** recordId PK记录ID */
  @NotEmpty
  private String recordId;

  public String getRecordId() {
    return recordId;
  }

  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }

}
