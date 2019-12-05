package com.xiaodou.resources.response.product;

import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.course.web.response.product.NextItemInfoResponse.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月7日
 * @description 获取下一节信息接口响应
 * @version 1.0
 */
public class NextItemInfoResponse extends BaseResponse {

  public NextItemInfoResponse(ResultType type) {
    super(type);
  }

  public NextItemInfoResponse(ProductResType productResType) {
    super(productResType);
  }

  /** nextChapterId 下一节所属章ID */
  private Integer nextChapterId = -1;
  /** nextItemId 下一节ID */
  private Integer nextItemId = -1;

  public Integer getNextChapterId() {
    return nextChapterId;
  }

  public void setNextChapterId(Integer nextChapterId) {
    this.nextChapterId = nextChapterId;
  }

  public Integer getNextItemId() {
    return nextItemId;
  }

  public void setNextItemId(Integer nextItemId) {
    this.nextItemId = nextItemId;
  }

}
