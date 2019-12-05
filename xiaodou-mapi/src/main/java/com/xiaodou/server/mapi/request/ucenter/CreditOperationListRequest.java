package com.xiaodou.server.mapi.request.ucenter;

import com.xiaodou.server.mapi.request.MapiBaseRequest;

/**
 * @name @see com.xiaodou.server.mapi.request.ucenter.CreditOperationListRequest.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年11月13日
 * @description 积分变更列表请求类
 * @version 1.0
 */
public class CreditOperationListRequest extends MapiBaseRequest {

  /** idUpper 消息最后一条ID */
  private String idUpper;
  /** size 单次查询消息数 */
  private String size = "10";

  public String getIdUpper() {
    return idUpper;
  }

  public void setIdUpper(String idUpper) {
    this.idUpper = idUpper;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

}
