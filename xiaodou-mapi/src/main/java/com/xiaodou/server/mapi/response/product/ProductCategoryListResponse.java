package com.xiaodou.server.mapi.response.product;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.vo.RegionVO;
import com.xiaodou.server.mapi.vo.forum.ProductCategory;
import com.xiaodou.summer.vo.out.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductCategoryListResponse extends BaseResponse {
  public ProductCategoryListResponse() {};

  public ProductCategoryListResponse(ResultType type) {
    super(type);
  }
  private List<ProductCategory> list = Lists.newArrayList();
  /**regionList 地域列表*/
  private List<RegionVO> regionList = Lists.newArrayList();
}
