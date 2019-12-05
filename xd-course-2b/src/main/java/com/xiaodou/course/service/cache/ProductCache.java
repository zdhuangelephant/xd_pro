package com.xiaodou.course.service.cache;

import java.util.List;

import com.xiaodou.course.model.product.FinalExamModel;
import com.xiaodou.course.model.product.ProductItemModel;
import com.xiaodou.course.model.product.ProductModel;

/**
 * @name @see com.xiaodou.course.service.cache.ProductCache.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月29日
 * @description 产品缓存Service接口
 * @version 1.0
 */
public interface ProductCache {

  /**
   * 根据产品ID获取产品信息
   * 
   * @param productId 产品ID
   * @return 产品信息
   */
  public ProductModel getProductById(String productId);

  /**
   * 获取产品列表缓存
   * 
   * @param module 模块ID
   * @param typeCode 类型码
   * @return 产品列表
   */
  public List<ProductModel> getProductList(String module, String typeCode);

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
  public List<ProductItemModel> getChapterList(String productId);

  /**
   * 获取产品条目-节列表缓存
   * 
   * @param productId 产品ID
   * @param chapterId 章ID
   * @return 节产品条目列表
   */
  public List<ProductItemModel> getChapterItemList(String chapterId);

  /**
   * 获取产品条目-节信息缓存
   * 
   * @param productId 产品ID
   * @param chapterId 章ID
   * @param itemId 节ID
   * @return 节产品条目列表
   */
  public ProductItemModel getItemInfo(String itemId);
  
  /**
   * 获取产品条条目-节缓存列表
   * @param itemIdList 节ID列表
   * @return 节产品条目列表
   */
  public List<ProductItemModel> getItemList(@SuppressWarnings("rawtypes") List itemIdList);
  
  /**
   * 获取期末测试列表
   * @param productId 产品ID
   * @return 期末测试列表
   */
  List<FinalExamModel> getFinalExamList(String productId);
} 
