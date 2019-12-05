package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * 话题详情－评论部分
 * 
 * @author weichao.zhai
 * @time 2015年4月6日 下午4:56:06
 */
@OverComeField(field = {"uid", "module"}, annotiation = AnnotationType.NotEmpty)
public class ForumDetailCommentsRequest extends BaseRequest{

  /** 话题ID */
  @NotEmpty
  private String resourcesId;

  /** 每页数量 */
  private Integer size = 20;

  /** 评论Id */
  private String commentId;



  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public String getCommentId() {
    return commentId;
  }

  public void setCommentId(String commentId) {
    this.commentId = commentId;
  }

public String getResourcesId() {
	return resourcesId;
}

public void setResourcesId(String resourcesId) {
	this.resourcesId = resourcesId;
}

}
