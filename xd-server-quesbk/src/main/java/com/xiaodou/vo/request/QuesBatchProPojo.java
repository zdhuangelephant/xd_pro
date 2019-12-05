package com.xiaodou.vo.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

public class QuesBatchProPojo extends QuesBasePojo {
  /**
   * 科目ID
   */
  @NotEmpty
  private String subject;

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }
  
}
