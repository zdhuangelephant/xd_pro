package com.xiaodou.resources.response.forum;

import java.io.Serializable;
import java.util.List;

import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.vo.forum.Comment;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 话题评论列表
 * 
 * @author hualong.li
 * 
 */
public class ForumCommentListResponse extends BaseResponse implements Serializable {
  public ForumCommentListResponse(ResultType type) {
    super(type);
  }

  /**
	 * 
	 */
  private static final long serialVersionUID = 9193286923912312015L;
  public static final String IS_HOT = "true";
  public static final String NO_HOT = "false";

  /**
   * 是否热门评论
   */
  private String isHot;
  /**
   * 评论列表
   */
  private List<Comment> list;

  public String getIsHot() {
    return isHot;
  }

  public void setIsHot(String isHot) {
    this.isHot = isHot;
  }

  public List<Comment> getList() {
    return list;
  }

  public void setList(List<Comment> list) {
    this.list = list;
  }

}
