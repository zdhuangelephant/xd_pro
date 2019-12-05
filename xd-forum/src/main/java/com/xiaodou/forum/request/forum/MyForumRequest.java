package com.xiaodou.forum.request.forum;


/**
 * 我参与的，我发布的 话题
 * 
 * @author weichao.zhai
 * @time 2015年4月6日 下午6:17:26
 */
public class MyForumRequest extends BaseRequest {

  /** 最后一个话题ID */
  private String forumId;
  
  /** 显示个数 */
  private Integer size = 20;

  public String getForumId() {
    return forumId;
  }

  public void setForumId(String forumId) {
    this.forumId = forumId;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

}
