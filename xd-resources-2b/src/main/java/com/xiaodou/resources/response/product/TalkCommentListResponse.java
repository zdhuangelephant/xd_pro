package com.xiaodou.resources.response.product;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.resources.vo.forum.Comment;
import com.xiaodou.summer.vo.out.ResultType;

public class TalkCommentListResponse extends BaseResponse {
  public TalkCommentListResponse() {}

  public TalkCommentListResponse(ResultType resultType) {
    super(resultType);
  }

  public TalkCommentListResponse(ProductResType resultType) {
    super(resultType);
  }

  private List<Comment> commentList = Lists.newArrayList();

  public List<Comment> getCommentList() {
    return commentList;
  }

  public void setCommentList(List<Comment> commentList) {
    this.commentList = commentList;
  }

}
