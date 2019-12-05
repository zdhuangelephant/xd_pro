package com.xiaodou.resources.response.forum;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.vo.forum.Comment;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 
 * @author weichao.zhai
 * @time 2015年4月6日 下午6:18:19
 */
public class ForumDetailCommentsResponse extends BaseResponse {

  public ForumDetailCommentsResponse(ResultType type) {
    super(type);
  }

  /** 热门评论 */
  private List<Comment> hot;
  /** 普通评论 */
  private List<Comment> list;

  public List<Comment> getHot() {
    return hot;
  }

  public void setHot(List<Comment> hot) {
    this.hot = hot;
  }

  public List<Comment> getList() {
    return list;
  }

  public void setList(List<Comment> list) {
    this.list = list;
  }
  
  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }


}
