package com.xiaodou.resources.response.forum;

import com.xiaodou.resources.vo.forum.RelateComment;
import com.xiaodou.summer.vo.out.ResultType;

public class RelateCommentResponse extends ListResponse<RelateComment> {

  public RelateCommentResponse(ResultType type) {
    super(type);
  }

  /** 返回评论总个数 */
  private String size;

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

}
