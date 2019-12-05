package com.xiaodou.forum.request.forum;

import com.xiaodou.summer.validator.annotion.LegalValue;
import com.xiaodou.summer.validator.enums.ValueScope;

/**
 * @name @see com.xiaodou.forum.request.forum.RelateCommentsRequest.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年10月18日
 * @description 与我相关接口pojo
 * @version 1.0
 */
public class RelateCommentsRequest extends BaseRequest {

  /** idUpper 最后一条评论ID */
  private String idUpper;

  /** size 每页显示个数 */
  @LegalValue(scope = ValueScope.GT, value = "0")
  private Integer size = 20;

  public String getIdUpper() {
    return idUpper;
  }

  public void setIdUpper(String idUpper) {
    this.idUpper = idUpper;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

}
