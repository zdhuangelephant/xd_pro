package com.xiaodou.forum.response.forum;

import com.xiaodou.summer.vo.out.ResultType;


/**
 * 发布话题回复
 * 
 * @author hualong.li
 * 
 */
public class PostResponse extends BaseResponse {
  public PostResponse(ResultType type) {
    super(type);
  }

  /**
   * serialNo TODO
   */
  private static final long serialNo = 234923049234235435L;

  public static long getSerialno() {
    return serialNo;
  }

}
