package com.xiaodou.resources.cache.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.resources.cache.product.impl.ProductListCache;
import com.xiaodou.resources.model.product.CourseProduct;
import com.xiaodou.resources.model.product.CourseProductChapter;
import com.xiaodou.resources.model.product.CourseProductItem;
import com.xiaodou.resources.model.product.CourseProductResource;
import com.xiaodou.resources.model.product.ProductItemModel;

@Service("productCache")
public class ProductCacheImpl implements ProductCache {

  @Resource
  ProductListCache productListCache;
  
  @Override
  public CourseProduct getProductById(String productId) {
    return productListCache.getProduct(productId);
  }

  @Override
  public List<CourseProduct> getProductList() {
    return productListCache.getProductList();
  }

  @Override
  public List<ProductItemModel> getProductItemList(String productId) {
    return productListCache.getProductItemList(productId);
  }

  @Override
  public List<CourseProductChapter> getChapterList(String productId) {
    return productListCache.getChapterList(productId);
  }

  @Override
  public List<CourseProductItem> getItemList(String productId, String chapterId) {
    return productListCache.getItemList(productId, chapterId);
  }

  @Override
  public List<CourseProductResource> getResourceList(String productId, String parentId) {
    return productListCache.getResourceList(productId, parentId);
  }

  @Override
  public CourseProductChapter getChapterInfo(String productId, String chapterId) {
    return productListCache.getChapterInfo(productId, chapterId);
  }

  @Override
  public CourseProductItem getItemInfo(String productId, String itemId) {
    return productListCache.getItemInfo(productId, itemId);
  }

  @Override
  public CourseProductResource getResourceInfo(String productId, String resourceId) {
    return productListCache.getResourceInfo(productId, resourceId);
  }

}
