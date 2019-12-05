package com.xiaodou.resources.cache.product.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.cache.local.DynamicTimingLocalCache;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.resources.constant.product.ProductConstant;
import com.xiaodou.resources.dao.product.ProductDao;
import com.xiaodou.resources.dao.product.ProductItemDao;
import com.xiaodou.resources.enums.product.ResourceType;
import com.xiaodou.resources.model.product.CourseProduct;
import com.xiaodou.resources.model.product.CourseProductChapter;
import com.xiaodou.resources.model.product.CourseProductItem;
import com.xiaodou.resources.model.product.CourseProductResource;
import com.xiaodou.resources.model.product.ProductItemModel;
import com.xiaodou.resources.model.product.ProductModel;
import com.xiaodou.resources.service.QueueService;
import com.xiaodou.resources.util.CacheInfoProp;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;

import edu.emory.mathcs.backport.java.util.Collections;

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
  ProductDao productDao;

  @Resource
  ProductItemDao productItemDao;

  private Comparator<ProductItemModel> comparator = new Comparator<ProductItemModel>() {
    @Override
    public int compare(ProductItemModel o1, ProductItemModel o2) {
      return (int) (o1.getListOrder() - o2.getListOrder());
    }
  };

  /** productTask 产品信息刷新任务类 */
  private final ProductInfoRefreshTask _task = new ProductInfoRefreshTask();

  /** initialDelay 初始调度任务时机 */
  private Integer initialDelay = 0;

  /** productListDelay 产品列表刷新延迟 */
  private Integer productListDelay = CacheInfoProp.getBaseCache().getPropertiesInt(
      ProductConstant.PRODUCT_LIST_TASK_DELAY);

  /**
   * 根据模块ID和类型码获取产品刷新任务类实例
   * 
   * @param moduleId 模块ID
   * @param typeCode 类型吗
   * @return 产品刷新任务类实例
   */
  public ProductInfoRefreshTask getInstance() {
    return _task;
  }

  /**
   * 根据产品ID获取产品信息
   * 
   * @param productId 产品ID
   * @return
   */
  public CourseProduct getProduct(String productId) {
    String cacheKey = String.format(ProductConstant.PRODUCT, productId);
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
    if (StringUtils.isBlank(productId) || null == product) return;
    String cacheKey = String.format(ProductConstant.PRODUCT, productId);
    DynamicTimingLocalCache.cache(cacheKey, product, productListDelay * 5);
  }

  /**
   * 获取产品列表
   * 
   * @param module 模块ID
   * @param typeCode 类型码
   * @return 产品列表
   */
  public List<CourseProduct> getProductList() {
    getInstance().startRefreshProduct();
    Object productList = DynamicTimingLocalCache.getCache(ProductConstant.PRODUCT_LIST);
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
  private void setProductList(List<CourseProduct> courseProductList) {
    if (null == courseProductList || courseProductList.size() == 0) return;
    DynamicTimingLocalCache.cache(ProductConstant.PRODUCT_LIST, courseProductList,
        productListDelay * 5);
  }

  /**
   * 获取产品条目列表
   * 
   * @param productId 产品ID
   * @return 产品条目列表
   */
  public List<ProductItemModel> getProductItemList(String productId) {
    String cacheKey = String.format(ProductConstant.PRODUCT_ITEM_LIST, productId);
    Object productItemList = DynamicTimingLocalCache.getCache(cacheKey);
    if (null != productItemList) {
      return (List<ProductItemModel>) productItemList;
    }
    return null;
  }

  /**
   * 存储产品条目表
   * 
   * @param productId 产品ID
   * @param productItemList 产品条目列表
   */
  private void setProductItemList(Long productId, List<ProductItemModel> productItemList) {
    if (null == productId || null == productItemList || productItemList.size() == 0) return;
    String cacheKey = String.format(ProductConstant.PRODUCT_ITEM_LIST, productId);
    DynamicTimingLocalCache.cache(cacheKey, productItemList, productListDelay * 5);
  }

  /**
   * 根据产品ID获取章列表
   * 
   * @param productId 产品ID
   * @return 产品章条目列表
   */
  public List<CourseProductChapter> getChapterList(String productId) {
    String cacheKey = String.format(ProductConstant.PRODUCT_ITEM_CHAPTER_LIST, productId);
    Object productItemList = DynamicTimingLocalCache.getCache(cacheKey);
    if (null != productItemList) {
      return (List<CourseProductChapter>) productItemList;
    }
    return null;
  }

  /**
   * 存储产品章条目表
   * 
   * @param productId 产品ID
   * @param productItemList 产品章条目列表
   */
  private void setChapterList(Long productId, List<CourseProductChapter> productChapterList) {
    if (null == productId || null == productChapterList || productChapterList.size() == 0) return;
    String cacheKey = String.format(ProductConstant.PRODUCT_ITEM_CHAPTER_LIST, productId);
    DynamicTimingLocalCache.cache(cacheKey, productChapterList, productListDelay * 5);
    for (CourseProductChapter chapter : productChapterList) {
      setChapterInfo(productId, chapter.getId(), chapter);
    }
  }

  /**
   * 根据产品ID获取资源列表
   * 
   * @param productId 产品ID
   * @return 产品资源列表
   */
  public List<CourseProductResource> getResourceList(String productId, String parentId) {
    if (StringUtils.isBlank(parentId)) parentId = "0";
    String cacheKey = String.format(ProductConstant.PRODUCT_ITEM_RESOURCE_LIST, productId);
    Object productResourceList = DynamicTimingLocalCache.getCache(cacheKey);
    if (null != productResourceList) {
      return (List<CourseProductResource>) productResourceList;
    }
    return null;
  }

  /**
   * 存储产品资源列表
   * 
   * @param productId 产品ID
   * @param productResourceList 产品资源列表
   */
  private void setResourceList(Long productId, Long parentId,
      List<CourseProductResource> productResourceList) {
    if (null == productId || null == productResourceList || productResourceList.size() == 0)
      return;
    if (null == parentId) parentId = 0l;
    String cacheKey =
        String.format(ProductConstant.PRODUCT_ITEM_RESOURCE_LIST, productId, parentId);
    DynamicTimingLocalCache.cache(cacheKey, productResourceList, productListDelay * 5);
    for (CourseProductResource resource : productResourceList) {
      setResourceInfo(productId, resource.getId(), resource);
    }
  }

  /**
   * 根据产品ID和章ID获取节列表
   * 
   * @param productId 产品ID
   * @param chapterId 章ID
   * @return 产品章条目列表
   */
  public List<CourseProductItem> getItemList(String productId, String chapterId) {
    String cacheKey = String.format(ProductConstant.PRODUCT_ITEM_ITEM_LIST, productId, chapterId);
    return DynamicTimingLocalCache.getCache(cacheKey);
  }

  /**
   * 存储产品节条目表
   * 
   * @param productId 产品ID
   * @param chapterId 章ID
   * @param productItemList 产品章条目列表
   */
  private void setItemList(Long productId, Long chapterId, List<CourseProductItem> productItemList) {
    if (null == productId || null == productItemList || productItemList.size() == 0) return;
    if (null == chapterId) chapterId = 0l;
    String cacheKey = String.format(ProductConstant.PRODUCT_ITEM_ITEM_LIST, productId, chapterId);
    DynamicTimingLocalCache.cache(cacheKey, productItemList, productListDelay * 5);
    for (CourseProductItem item : productItemList) {
      setItemInfo(productId, item.getId(), item);
      /** 存储资源列表 */
      if (null != item.getResourceList() && item.getResourceList().size() > 0) {
        Collections.sort(item.getResourceList(), comparator);
        setResourceList(productId, item.getId(), item.getResourceList());
      }
    }
  }

  /**
   * 获取产品章信息
   * 
   * @param productId 产品ID
   * @param chapterId 章ID
   * @return 章信息实体
   */
  public CourseProductChapter getChapterInfo(String productId, String chapterId) {
    String cacheKey =
        String.format(ProductConstant.PRODUCT_ITEM_CHAPTER_INFO, productId, chapterId);
    return DynamicTimingLocalCache.getCache(cacheKey);
  }

  /**
   * 存储产品章信息
   * 
   * @param productId 产品ID
   * @param chapterId 章ID
   * @param itemInfo 产品章条目实体
   */
  private void setChapterInfo(Long productId, Long chapterId, CourseProductChapter chapterInfo) {
    String cacheKey =
        String.format(ProductConstant.PRODUCT_ITEM_CHAPTER_INFO, productId, chapterId);
    DynamicTimingLocalCache.cache(cacheKey, chapterInfo, productListDelay * 5);
  }

  /**
   * 获取产品节信息
   * 
   * @param productId 产品ID
   * @param itemId 节ID
   * @return 节信息实体
   */
  public CourseProductItem getItemInfo(String productId, String itemId) {
    String cacheKey = String.format(ProductConstant.PRODUCT_ITEM_ITEM_INFO, productId, itemId);
    return DynamicTimingLocalCache.getCache(cacheKey);
  }

  /**
   * 存储产品节信息
   * 
   * @param productId 产品ID
   * @param itemId 节ID
   * @param itemInfo 产品节条目实体
   */
  private void setItemInfo(Long productId, Long itemId, CourseProductItem itemInfo) {
    String cacheKey = String.format(ProductConstant.PRODUCT_ITEM_ITEM_INFO, productId, itemId);
    DynamicTimingLocalCache.cache(cacheKey, itemInfo, productListDelay * 5);
  }

  /**
   * 获取产品节信息
   * 
   * @param productId 产品ID
   * @param itemId 节ID
   * @return 节信息实体
   */
  public CourseProductResource getResourceInfo(String productId, String resourceId) {
    String cacheKey =
        String.format(ProductConstant.PRODUCT_ITEM_RESOURCE_INFO, productId, resourceId);
    return DynamicTimingLocalCache.getCache(cacheKey);
  }

  /**
   * 存储产品节信息
   * 
   * @param productId 产品ID
   * @param itemId 节ID
   * @param itemInfo 产品节条目实体
   */
  private void setResourceInfo(Long productId, Long resourceId, CourseProductResource resourceInfo) {
    String cacheKey =
        String.format(ProductConstant.PRODUCT_ITEM_RESOURCE_INFO, productId, resourceId);
    DynamicTimingLocalCache.cache(cacheKey, resourceInfo, productListDelay * 5);
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


    public ProductInfoRefreshTask() {}

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
                Page<ProductModel> productPage =
                    productDao.findEntityListByCond(new QueryParam(), null);
                if (null == productPage || null == productPage.getResult()
                    || productPage.getResult().size() == 0) return;
                List<CourseProduct> courseProductList = Lists.newArrayList();
                for (ProductModel product : productPage.getResult()) {
                  courseProductList.add(new CourseProduct(product));
                }
                /** 缓存产品列表 */
                setProductList(courseProductList);
                for (CourseProduct product : courseProductList) {
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
                IQueryParam param = new QueryParam();
                param.addInput("productId", product.getId());
                Page<ProductItemModel> productItemList =
                    productItemDao.findEntityListByCond(param, null);
                if (null == productItemList || null == productItemList.getResult()
                    || productItemList.getResult().size() == 0) return;
                setProductItemList(product.getId(), productItemList.getResult());
                Map<Long, ProductItemModel> itemMap = Maps.newHashMap();
                List<ProductItemModel> processItemList = Lists.newArrayList();
                for (ProductItemModel _item : productItemList.getResult()) {
                  ResourceType _itemResType = ResourceType.getByTypeId(_item.getResourceType());
                  switch (_itemResType) {
                    case CHAPTER:
                      if (_item.getParentId() == 0) {
                        CourseProductChapter chapter = new CourseProductChapter(_item);
                        itemMap.put(chapter.getId(), chapter);
                        product.getChapterList().add(chapter);
                      } else {
                        CourseProductItem item = new CourseProductItem(_item);
                        itemMap.put(item.getId(), item);
                        processItemList.add(item);
                      }
                      break;
                    case VIDEO:
                    case DOC:
                    case QUIZ:
                    case TALK:
                    case TASK:
                    case EXAM:
                    case FINAL:
                      CourseProductResource res = new CourseProductResource(_item);
                      if (res.getParentId() == 0)
                        product.getResourceList().add(res);
                      else
                        processItemList.add(res);
                      break;
                    default:
                      break;
                  }
                }
                for (ProductItemModel itemModel : processItemList) {
                  if (itemModel.getParentId() == 0) continue;
                  ProductItemModel parent = itemMap.get(itemModel.getParentId());
                  if (parent.getParentId() == 0) {
                    if (itemModel instanceof CourseProductResource)
                      ((CourseProductChapter) parent).getResourceList().add(
                          (CourseProductResource) itemModel);
                    if (itemModel instanceof CourseProductItem)
                      ((CourseProductChapter) parent).getItemList().add(
                          (CourseProductItem) itemModel);
                  } else {
                    if (itemModel instanceof CourseProductResource)
                      ((CourseProductItem) parent).getResourceList().add(
                          (CourseProductResource) itemModel);
                  }
                }
                /** 缓存全量产品条目列表 */
                storeChapter(product);
                // 缓存课程资源列表
                storeResource(product);
              }

              private void storeResource(CourseProduct product) {
                if (null == product || null == product.getChapterList()
                    || product.getChapterList().size() == 0) return;
                Collections.sort(product.getResourceList(), comparator);
                /** 存储章列表 */
                setResourceList(product.getId(), product.getId(), product.getResourceList());
              }

              /**
               * 存储章节信息
               * 
               * @param productId 产品ID
               * @param chapterList 章节列表
               */
              private void storeChapter(CourseProduct product) {
                if (null == product || null == product.getChapterList()
                    || product.getChapterList().size() == 0) return;
                Collections.sort(product.getChapterList(), comparator);
                /** 存储章列表 */
                setChapterList(product.getId(), product.getChapterList());
                List<CourseProductItem> itemList = Lists.newArrayList();
                for (CourseProductChapter chapter : product.getChapterList()) {
                  /** 存储节列表 */
                  if (null != chapter.getItemList() && chapter.getItemList().size() > 0) {
                    Collections.sort(chapter.getItemList(), comparator);
                    setItemList(product.getId(), chapter.getId(), chapter.getItemList());
                    itemList.addAll(chapter.getItemList());
                  }
                  /** 存储资源列表 */
                  if (null != chapter.getResourceList() && chapter.getResourceList().size() > 0) {
                    Collections.sort(chapter.getResourceList(), comparator);
                    setResourceList(product.getId(), chapter.getId(), chapter.getResourceList());
                  }
                }
                if (itemList.size() > 0) setItemList(product.getId(), null, itemList);
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
