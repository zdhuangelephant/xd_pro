package com.xiaodou.course.web.response.product;

import com.xiaodou.course.vo.product.ResourceDescription;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * Created by zyp on 15/8/9.
 */
public class ProductItemAuthResponse extends BaseResponse {

  // 资源地址
  private ResourceDescription resourceUrl;

  public ResourceDescription getResourceUrl() {
    return resourceUrl;
  }

  public void setResourceUrl(ResourceDescription resourceUrl) {
    this.resourceUrl = resourceUrl;
  }

  public ProductItemAuthResponse(ResultType resultType) {
    super(resultType);
  }
}
