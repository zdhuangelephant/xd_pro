package com.xiaodou.resources.response.product;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.resources.constant.product.CourseConstant;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

public class TalkDetailResponse extends BaseResponse {
  public TalkDetailResponse() {}

  public TalkDetailResponse(ResultType resultType) {
    super(resultType);
  }

  public TalkDetailResponse(ProductResType resultType) {
    super(resultType);
  }

  private String title = StringUtils.EMPTY;
  private String content = StringUtils.EMPTY;
  private String fromResource = StringUtils.EMPTY;
  private String deadline = StringUtils.EMPTY;
  private String isPraise = CourseConstant.ISNOTPRAISE;
  private String praiseCount = String.valueOf(0);
  private String commentCount = String.valueOf(0);

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getFromResource() {
    return fromResource;
  }

  public void setFromResource(String fromResource) {
    this.fromResource = fromResource;
  }

  public String getDeadline() {
    return deadline;
  }

  public void setDeadline(String deadline) {
    this.deadline = deadline;
  }

  public String getIsPraise() {
    return isPraise;
  }

  public void setIsPraise(String isPraise) {
    this.isPraise = isPraise;
  }

  public String getPraiseCount() {
    return praiseCount;
  }

  public void setPraiseCount(String praiseCount) {
    this.praiseCount = praiseCount;
  }

  public String getCommentCount() {
    return commentCount;
  }

  public void setCommentCount(String commentCount) {
    this.commentCount = commentCount;
  }

}
