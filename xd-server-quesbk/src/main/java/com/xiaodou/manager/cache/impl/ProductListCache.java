package com.xiaodou.manager.cache.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.cache.local.DynamicTimingLocalCache;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.dao.product.CourseProductDao;
import com.xiaodou.dao.product.CourseProductItemDao;
import com.xiaodou.domain.product.CourseProduct;
import com.xiaodou.domain.product.CourseProductItem;
import com.xiaodou.service.QueueService;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;
import com.xiaodou.util.CacheInfoProp;

/**
 * @name @see com.xiaodou.service.cache.QuesbkCache.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月8日
 * @description 产品列表缓存
 * @version 1.0
 */
@SuppressWarnings("unchecked")
@Service("productListCache")
public class ProductListCache {

  @Resource
  QueueService queueService;

  @Resource
  CourseProductDao courseProductDao;

  @Resource
  CourseProductItemDao courseProductItemDao;

  /** productTask 产品信息刷新任务类 */
  private final ConcurrentMap<String, ProductInfoRefreshTask> _taskMap = Maps.newConcurrentMap();

  /** initialDelay 初始调度任务时机 */
  private Integer initialDelay = 0;

  /** productListDelay 产品列表刷新延迟 */
  private Integer productListDelay = CacheInfoProp.getBaseCache().getPropertiesInt(
      QuesBaseConstant.PRODUCT_LIST_TASK_DELAY);

  /**
   * 根据模块ID和类型码获取产品刷新任务类实例
   * 
   * @param moduleId 模块ID
   * @param typeCode 类型吗
   * @return 产品刷新任务类实例
   */
  public ProductInfoRefreshTask getInstance(String moduleId, String typeCode) {
    String key = moduleId + typeCode;
    if (_taskMap.containsKey(key)) return _taskMap.get(key);
    synchronized (_taskMap) {
      if (_taskMap.containsKey(key)) return _taskMap.get(key);
      ProductInfoRefreshTask productTask = new ProductInfoRefreshTask(moduleId, typeCode);
      _taskMap.put(key, productTask);
    }
    return _taskMap.get(key);
  }

  /**
   * 根据产品ID获取产品信息
   * 
   * @param productId 产品ID
   * @return
   */
  public CourseProduct getProduct(String productId) {
    String cacheKey = String.format(QuesBaseConstant.PRODUCT, productId);
    Object product = DynamicTimingLocalCache.getCache(cacheKey);
    if (null != product) {
      return (CourseProduct) product;
    }
    return null;
  }

  /**
   * 缓存产品信息
   * 
   * @param productId 产品ID
   * @param product 产品信息
   */
  private void setProduct(String productId, CourseProduct product) {
    String cacheKey = String.format(QuesBaseConstant.PRODUCT, productId);
    DynamicTimingLocalCache.cache(cacheKey, product, productListDelay * 5);
  }

  /**
   * 获取产品列表
   * 
   * @param module 模块ID
   * @param typeCode 类型码
   * @return 产品列表
   */
  public List<CourseProduct> getProductList(String module, String typeCode) {
    String cacheKey = String.format(QuesBaseConstant.PRODUCT_LIST, module, typeCode);
    getInstance(module, typeCode).startRefreshProduct();
    Object productList = DynamicTimingLocalCache.getCache(cacheKey);
    if (null != productList) {
      return (List<CourseProduct>) productList;
    }
    return null;
  }

  /**
   * 设置产品列表
   * 
   * @param module 模块ID
   * @param typeCode 类型码
   * @param courseProductList 产品列表
   */
  private void setProductList(String module, String typeCode, List<CourseProduct> courseProductList) {
    String cacheKey = String.format(QuesBaseConstant.PRODUCT_LIST, module, typeCode);
    DynamicTimingLocalCache.cache(cacheKey, courseProductList, productListDelay * 5);
  }

  /**
   * 获取产品条目列表
   * 
   * @param productId 产品ID
   * @return 产品条目列表
   */
  public List<CourseProductItem> getChapterItemList(String productId) {
    String cacheKey = String.format(QuesBaseConstant.PRODUCT_ITEM_LIST, productId);
    Object productItemList = DynamicTimingLocalCache.getCache(cacheKey);
    if (null != productItemList) {
      return (List<CourseProductItem>) productItemList;
    }
    return null;
  }

  /**
   * 存储产品条目表
   * 
   * @param productId 产品ID
   * @param productItemList 产品条目列表
   */
  private void setChapterItemList(String productId, List<CourseProductItem> productItemList) {
    String cacheKey = String.format(QuesBaseConstant.PRODUCT_ITEM_LIST, productId);
    DynamicTimingLocalCache.cache(cacheKey, productItemList, productListDelay * 5);
  }

  /**
   * 根据产品ID获取章列表
   * 
   * @param productId 产品ID
   * @return 产品章条目列表
   */
  public List<CourseProductItem> getChapterList(String productId) {
    String cacheKey = String.format(QuesBaseConstant.PRODUCT_ITEM_CHAPTER_LIST, productId);
    Object productItemList = DynamicTimingLocalCache.getCache(cacheKey);
    if (null != productItemList) {
      return (List<CourseProductItem>) productItemList;
    }
    return null;
  }

  /**
   * 存储产品章条目表
   * 
   * @param productId 产品ID
   * @param productItemList 产品章条目列表
   */
  private void setChapterList(String productId, List<CourseProductItem> productItemList) {
    String cacheKey = String.format(QuesBaseConstant.PRODUCT_ITEM_CHAPTER_LIST, productId);
    DynamicTimingLocalCache.cache(cacheKey, productItemList, productListDelay * 5);
  }

  /**
   * 根据产品ID和章ID获取节列表
   * 
   * @param productId 产品ID
   * @param chapterId 章ID
   * @return 产品章条目列表
   */
  public List<CourseProductItem> getItemList(String productId, String chapterId) {
    String cacheKey = String.format(QuesBaseConstant.PRODUCT_ITEM_ITEM_LIST, productId, chapterId);
    return DynamicTimingLocalCache.getCache(cacheKey);
  }

  /**
   * 存储产品节条目表
   * 
   * @param productId 产品ID
   * @param chapterId 章ID
   * @param productItemList 产品章条目列表
   */
  private void setItemList(String productId, String chapterId,
      List<CourseProductItem> productItemList) {
    String cacheKey = String.format(QuesBaseConstant.PRODUCT_ITEM_ITEM_LIST, productId, chapterId);
    DynamicTimingLocalCache.cache(cacheKey, productItemList, productListDelay * 5);
  }

  /**
   * 获取产品章信息
   * 
   * @param productId 产品ID
   * @param chapterId 章ID
   * @return 章信息实体
   */
  public CourseProductItem getChapterInfo(String productId, String chapterId) {
    String cacheKey =
        String.format(QuesBaseConstant.PRODUCT_ITEM_CHAPTER_INFO, productId, chapterId);
    return DynamicTimingLocalCache.getCache(cacheKey);
  }

  /**
   * 存储产品章信息
   * 
   * @param productId 产品ID
   * @param chapterId 章ID
   * @param itemInfo 产品章条目实体
   */
  private void setChapterInfo(String productId, String chapterId, CourseProductItem itemInfo) {
    String cacheKey =
        String.format(QuesBaseConstant.PRODUCT_ITEM_CHAPTER_INFO, productId, chapterId);
    DynamicTimingLocalCache.cache(cacheKey, itemInfo, productListDelay * 5);
  }

  /**
   * 获取产品节信息
   * 
   * @param productId 产品ID
   * @param chapterId 章ID
   * @param itemId 节ID
   * @return 节信息实体
   */
  public CourseProductItem getItemInfo(String productId, String chapterId, String itemId) {
    String cacheKey =
        String.format(QuesBaseConstant.PRODUCT_ITEM_ITEM_INFO, productId, chapterId, itemId);
    return DynamicTimingLocalCache.getCache(cacheKey);
  }

  /**
   * 存储产品节信息
   * 
   * @param productId 产品ID
   * @param chapterId 章ID
   * @param itemId 节ID
   * @param itemInfo 产品节条目实体
   */
  private void setItemInfo(String productId, String chapterId, String itemId,
      CourseProductItem itemInfo) {
    String cacheKey =
        String.format(QuesBaseConstant.PRODUCT_ITEM_ITEM_INFO, productId, chapterId, itemId);
    DynamicTimingLocalCache.cache(cacheKey, itemInfo, productListDelay * 5);
  }

  /**
   * @name @see com.xiaodou.service.cache.ProductInfoRefreshTask.java
   * @CopyRright (c) 2015 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年8月8日
   * @description 产品信息刷新任务
   * @version 1.0
   */
  private class ProductInfoRefreshTask {

    /** flag 刷新任务标志位 */
    private AtomicBoolean flag = new AtomicBoolean(false);

    /** module 模块ID */
    /** typeCode 类型码 */
    private String _module, _typeCode;

    public ProductInfoRefreshTask(String moduleId, String typeCode) {
      _module = moduleId;
      _typeCode = typeCode;
    }

    /**
     * 刷新产品信息
     * 
     * @param moduleId 模块ID
     * @param typeCode 类型码
     */
    public void startRefreshProduct() {
      if (flag.compareAndSet(false, true)) {
        SummerCommonScheduledExecutor.getExecutor().scheduleWithFixedDelay(
            new SummerScheduledTask() {
              @Override
              public void doMain() {
                processProductList();
              }

              /**
               * 处理产品列表
               */
              private void processProductList() {
                List<CourseProduct> productLst =
                    courseProductDao.selectByModuleAndTypeCode(_module, _typeCode);
                /** 缓存产品列表 */
                setProductList(_module, _typeCode, productLst);
                for (CourseProduct product : productLst) {
                  if (null == product || null == product.getId()) continue;
                  setProduct(product.getId().toString(), product);
                  processProductItem(product);
                }
              }

              /**
               * 处理产品条目
               * 
               * @param product 产品
               */
              private void processProductItem(CourseProduct product) {
                List<CourseProductItem> productItemList =
                    courseProductItemDao.selectByProductId(product.getId().toString());
                /** 缓存全量产品条目列表 */
                setChapterItemList(product.getId().toString(), productItemList);
                storeChapter(product.getId().toString(),
                    processChapterItem(product.getId().toString(), productItemList));
              }

              /**
               * 处理产品章节条目
               * 
               * @param productItemList 产品全量条目列表
               */
              private List<CourseProductItem> processChapterItem(String productId,
                  List<CourseProductItem> productItemList) {
                Map<Long, CourseProductItem> _chapterMap = Maps.newHashMap();
                List<CourseProductItem> chapterList = Lists.newArrayList();
                List<CourseProductItem> itemList = Lists.newArrayList();
                CourseProductItem _chapter = null;
                for (CourseProductItem productItem : productItemList) {
                  if (null == productItem.getResourceType()) continue;
                  if (productItem.getChapterType() == QuesBaseConstant.RESOURCE_TYPE_CHAPTER) {
                    _chapter = _chapterMap.get(productItem.getId());
                    if (_chapter != null) {
                      productItem.setChildList(_chapter.getChildList());
                    }
                    _chapterMap.put(productItem.getId(), productItem);
                    chapterList.add(productItem);
                    setChapterInfo(productItem.getProductId().toString(), productItem.getId()
                        .toString(), productItem);
                  } else if (productItem.getChapterType() == QuesBaseConstant.RESOURCE_TYPE_ITEM) {
                    _chapter = _chapterMap.get(productItem.getParentId());
                    if (_chapter == null) {
                      _chapter = new CourseProductItem();
                      _chapterMap.put(productItem.getParentId(), _chapter);
                    }
                    _chapter.getChildList().add(productItem);
                    itemList.add(productItem);
                    setItemInfo(productItem.getProductId().toString(), productItem.getParentId()
                        .toString(), productItem.getId().toString(), productItem);
                  }
                }
                setItemList(productId, null, productItemList);
                return chapterList;
              }

              /**
               * 存储章节信息
               * 
               * @param productId 产品ID
               * @param chapterList 章节列表
               */
              private void storeChapter(String productId, List<CourseProductItem> chapterList) {
                /** 存储章列表 */
                setChapterList(productId, chapterList);
                for (CourseProductItem chapter : chapterList) {
                  /** 存储节列表 */
                  setItemList(productId, chapter.getId().toString(), chapter.getChildList());
                }
              }

              @Override
              public void onException(Throwable t) {
                LoggerUtil.error("设置问题类型缓存异常.", t);
              }
            }, initialDelay, productListDelay, TimeUnit.SECONDS);
      }
    }
  }

}
