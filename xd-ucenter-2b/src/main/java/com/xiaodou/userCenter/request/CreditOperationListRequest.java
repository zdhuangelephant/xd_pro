package com.xiaodou.userCenter.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name @see com.xiaodou.server.mapi.request.ucenter.CreditOperationListRequest.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年11月13日
 * @description 积分变更列表请求类
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CreditOperationListRequest extends BaseRequest {

  /** idUpper 消息最后一条ID */
  private String idUpper;
  /** size 单次查询消息数 */
  private int size = 10;

}
