package com.xiaodou.manager.cache;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.domain.product.CourseProduct;
import com.xiaodou.domain.product.CourseProductItem;
import com.xiaodou.domain.product.QuesbkQuesType;
import com.xiaodou.manager.cache.impl.ProductListCache;
import com.xiaodou.manager.cache.impl.QuesTypeCache;

/**
 * @name @see com.xiaodou.service.cache.QuesbkCacheImpl.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月8日
 * @description 题库缓存service实现
 * @version 1.0
 */
@Service("quesbkCache")
public class QuesbkCacheImpl implements QuesbkCache {

  @Resource
  ProductListCache productListCache;

  @Resource
  QuesTypeCache quesTypeCache;

  @Override
  public List<QuesbkQuesType> getQuesType() {
    return quesTypeCache.getQuesType();
  }

  @Override
  public List<CourseProduct> getProductList(String module, String typeCode) {
    return productListCache.getProductList(module, typeCode);
  }

  @Override
  public List<CourseProductItem> getChapterItemList(String productId) {
    return productListCache.getChapterItemList(productId);
  }

  @Override
  public List<CourseProductItem> getChapterList(String productId) {
    return productListCache.getChapterList(productId);
  }

  @Override
  public List<CourseProductItem> getItemList(String productId, String chapterId) {
    return productListCache.getItemList(productId, chapterId);
  }

  @Override
  public CourseProduct getProductById(String productId) {
    return productListCache.getProduct(productId);
  }

  @Override
  public CourseProductItem getItemInfo(String productId, String chapterId, String itemId) {
    return null;
  }

}
