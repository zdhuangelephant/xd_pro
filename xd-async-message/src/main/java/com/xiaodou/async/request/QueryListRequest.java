package com.xiaodou.async.request;

import com.xiaodou.async.constant.AsyncMessageConst;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.userCenter.request.BaseRequest;

/**
 * @name @see com.xiaodou.async.model.QueryListPojo.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月29日
 * @description 查询消息列表参数Pojo
 * @version 1.0
 */
public class QueryListRequest extends BaseRequest {

  /** type 消息类型 */
  @NotEmpty
  @LegalValueList(value = {AsyncMessageConst.POJO_STYPE_SYSTEMMES,
      AsyncMessageConst.POJO_STYPE_OTHERMES})
  private String type;
  /** sysIdUpper 系统消息最后一条Id */
  private String sysIdUpper;
  /** userIdUpper 用户消息最后一条ID */
  private String userIdUpper;
  /** size 单次查询消息数 */
  private String size = "10";

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSysIdUpper() {
    return sysIdUpper;
  }

  public void setSysIdUpper(String sysIdUpper) {
    this.sysIdUpper = sysIdUpper;
  }

  public String getUserIdUpper() {
    return userIdUpper;
  }

  public void setUserIdUpper(String userIdUpper) {
    this.userIdUpper = userIdUpper;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }


}
