package com.xiaodou.live.vo.request;

import com.xiaodou.live.constants.LiveConstants;

/**
 * @name SerieListRequest 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月25日
 * @description 系列直播请求类
 * @version 1.0
 */
public class SerieListRequest extends LiveBaseRequest {

  /** pageNo 当前页码 */
  private Integer pageNo = LiveConstants.I_FIRST_PAGENO;

  public Integer getPageNo() {
    return pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }
  
}
