package com.xiaodou.resources.vo.product;

import java.util.List;

/**
 * Created by zyp on 15/8/16.
 */
public class ProductList {

  // 图片列表
  private List<ModuleSlide> slideList;

  // 产品列表
  private List<ProductListDetail> productList;

  public List<ModuleSlide> getSlideList() {
    return slideList;
  }

  public void setSlideList(List<ModuleSlide> slideList) {
    this.slideList = slideList;
  }

  public List<ProductListDetail> getProductList() {
    return productList;
  }

  public void setProductList(List<ProductListDetail> productList) {
    this.productList = productList;
  }
}
