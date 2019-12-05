package com.xiaodou.live.vo.request;

import com.xiaodou.live.constants.LiveConstants;

/**
 * @name RecommendListRequest 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月26日
 * @description 推荐直播列表请求
 * @version 1.0
 */
public class RecommendListRequest extends LiveBaseRequest {

  /** pageNo 当前页码 */
  private Integer pageNo = LiveConstants.I_FIRST_PAGENO;

  public Integer getPageNo() {
    return pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }
  
}
