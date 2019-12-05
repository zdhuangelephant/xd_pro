package com.xiaodou.resources.cache.product;

import java.util.List;

import com.xiaodou.resources.model.product.CourseProduct;
import com.xiaodou.resources.model.product.CourseProductChapter;
import com.xiaodou.resources.model.product.CourseProductItem;
import com.xiaodou.resources.model.product.CourseProductResource;
import com.xiaodou.resources.model.product.ProductItemModel;

public interface ProductCache {
  /**
   * 根据产品ID获取产品信息
   * 
   * @param productId 产品ID
   * @return 产品信息
   */
  public CourseProduct getProductById(String productId);

  /**
   * 获取产品列表缓存
   * 
   * @param module 模块ID
   * @param typeCode 类型码
   * @return 产品列表
   */
  public List<CourseProduct> getProductList();

  /**
   * 获取产品条目列表缓存
   * 
   * @param productId 产品ID
   * @return 产品条目列表
   */
  public List<ProductItemModel> getProductItemList(String productId);

  /**
   * 获取产品条目-章列表缓存
   * 
   * @param productId 产品ID
   * @return 章产品条目列表
   */
  public List<CourseProductChapter> getChapterList(String productId);

  /**
   * 获取产品条目-节列表缓存
   * 
   * @param productId 产品ID
   * @param chapterId 章ID
   * @return 节产品条目列表
   */
  public List<CourseProductItem> getItemList(String productId, String chapterId);
  
  /**
   * 获取产品条目-资源列表缓存
   * 
   * @param productId 产品ID
   * @param parentId 资源挂载节点ID
   * @return 资源产品条目列表
   */
  public List<CourseProductResource> getResourceList(String productId, String parentId);
  
  /**
   * 获取产品条目-章信息缓存
   * 
   * @param productId 产品ID
   * @param chapterId 章ID
   * @return 章产品条目列表
   */
  public CourseProductChapter getChapterInfo(String productId, String chapterId);

  /**
   * 获取产品条目-节信息缓存
   * 
   * @param productId 产品ID
   * @param itemId 节ID
   * @return 节产品条目列表
   */
  public CourseProductItem getItemInfo(String productId, String itemId);
  
  /**
   * 获取产品条目-资源信息缓存
   * @param productId 产品ID
   * @param resourceId 资源ID
   * @return 资源产品条目列表
   */
  public CourseProductResource getResourceInfo(String productId, String resourceId);
}
