package com.xiaodou.resources.response.forum;

import com.xiaodou.resources.vo.forum.MyComment;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 
 * @author weichao.zhai
 * @time 2015年4月6日 下午6:20:14
 */
public class MyCommentResponse extends ListResponse<MyComment> {

  /** 返回评论总个数 */
  private String size;

  public MyCommentResponse(ResultType type) {
    super(type);
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }


}
