package com.xiaodou.course.service.cache;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.xiaodou.course.model.product.FinalExamModel;
import com.xiaodou.course.model.product.ProductItemModel;
import com.xiaodou.course.model.product.ProductModel;
import com.xiaodou.course.service.cache.impl.ProductListCache;

/**
 * @name @see com.xiaodou.course.service.cache.ProductCacheImpl.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月29日
 * @description 产品缓存Service实现类
 * @version 1.0
 */
@Service("productCache")
public class ProductCacheImpl implements ProductCache {

  @Resource
  ProductListCache productListCache;

  @Override
  public List<ProductModel> getProductList(String module, String typeCode) {
    return productListCache.getProductList(module, typeCode);
  }

  @Override
  public List<ProductItemModel> getProductItemList(String productId) {
    return productListCache.getProductItemList(productId);
  }

  @Override
  public List<ProductItemModel> getChapterList(String productId) {
    return productListCache.getChapterList(productId);
  }

  @Override
  public List<ProductItemModel> getChapterItemList(String chapterId) {
    return productListCache.getItemList(chapterId);
  }

  @Override
  public ProductModel getProductById(String productId) {
    return productListCache.getProduct(productId);
  }

  @Override
  public ProductItemModel getItemInfo(String itemId) {
    return productListCache.getItemInfo(itemId);
  }

  @Override
  public List<ProductItemModel> getItemList(@SuppressWarnings("rawtypes") List itemIdList) {
    List<ProductItemModel> itemList = Lists.newArrayList();
    for (Object itemId : itemIdList) {
      if (null == itemId) continue;
      ProductItemModel itemInfo = getItemInfo(itemId.toString());
      if (null == itemInfo) continue;
      itemList.add(itemInfo);
    }
    return itemList;
  }

  @Override
  public List<FinalExamModel> getFinalExamList(String productId) {
    return productListCache.getFinalExamList(productId);
  }
}
