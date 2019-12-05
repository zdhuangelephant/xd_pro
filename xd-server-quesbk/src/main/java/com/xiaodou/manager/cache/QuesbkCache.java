package com.xiaodou.manager.cache;

import java.util.List;

import com.xiaodou.domain.product.CourseProduct;
import com.xiaodou.domain.product.CourseProductItem;
import com.xiaodou.domain.product.QuesbkQuesType;

/**
 * @name @see com.xiaodou.service.cache.QuesbkCache.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月8日
 * @description 题库缓存service接口
 * @version 1.0
 */
public interface QuesbkCache {

  /**
   * 获取问题类型缓存
   * 
   * @return
   */
  public List<QuesbkQuesType> getQuesType();

  /**
   * 根据产品ID获取产品信息
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
  public List<CourseProduct> getProductList(String module, String typeCode);

  /**
   * 获取产品条目列表缓存
   * 
   * @param productId 产品ID
   * @return 产品条目列表
   */
  public List<CourseProductItem> getChapterItemList(String productId);

  /**
   * 获取产品条目-章列表缓存
   * 
   * @param productId 产品ID
   * @return 章产品条目列表
   */
  public List<CourseProductItem> getChapterList(String productId);

  /**
   * 获取产品条目-节列表缓存
   * 
   * @param productId 产品ID
   * @param chapterId 章ID
   * @return 节产品条目列表
   */
  public List<CourseProductItem> getItemList(String productId, String chapterId);
  
  /**
   * 获取产品条目-节信息缓存
   * 
   * @param productId 产品ID
   * @param chapterId 章ID
   * @param itemId 节ID
   * @return 节产品条目列表
   */
  public CourseProductItem getItemInfo(String productId, String chapterId, String itemId);
}
