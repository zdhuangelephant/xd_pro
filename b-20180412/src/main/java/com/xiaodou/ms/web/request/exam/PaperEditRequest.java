package com.xiaodou.ms.web.request.exam;

import com.xiaodou.ms.web.request.BaseRequest;

/**
 * Created by zyp on 15/8/12.
 */
public class PaperEditRequest extends BaseRequest {

  // id
  private String id;

  // 试卷名
  private String examName;

  // 描述
  private String mdesc;

  // 问题
  private String quesIds;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getExamName() {
    return examName;
  }

  public void setExamName(String examName) {
    this.examName = examName;
  }

  public String getMdesc() {
    return mdesc;
  }

  public void setMdesc(String mdesc) {
    this.mdesc = mdesc;
  }

  public String getQuesIds() {
    return quesIds;
  }

  public void setQuesIds(String quesIds) {
    this.quesIds = quesIds;
  }
}
