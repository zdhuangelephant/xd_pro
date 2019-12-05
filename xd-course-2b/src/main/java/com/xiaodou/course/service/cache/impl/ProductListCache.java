package com.xiaodou.course.service.cache.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.cache.local.DynamicTimingLocalCache;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.course.constant.CourseConstant;
import com.xiaodou.course.dao.product.FinalExamDao;
import com.xiaodou.course.dao.product.ProductCategoryDao;
import com.xiaodou.course.dao.product.ProductDao;
import com.xiaodou.course.dao.product.ProductItemDao;
import com.xiaodou.course.model.product.FinalExamModel;
import com.xiaodou.course.model.product.ProductCategoryModel;
import com.xiaodou.course.model.product.ProductItemModel;
import com.xiaodou.course.model.product.ProductModel;
import com.xiaodou.course.util.CacheInfoProp;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;
import com.xiaodou.summer.util.SpringWebContextHolder;

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

  /** initialDelay 初始调度任务时机 */
  private Integer initialDelay = 0;

  /** productListDelay 产品列表刷新延迟 */
  private Integer productListDelay = CacheInfoProp.getBaseCache().getPropertiesInt(
      CourseConstant.PRODUCT_LIST_TASK_DELAY);

  private AtomicBoolean initFlag = new AtomicBoolean(false);

  private void init() {
    if (initFlag.compareAndSet(false, true)) {
      SummerCommonScheduledExecutor.getExecutor().scheduleWithFixedDelay(new SummerScheduledTask() {
        @Override
        public void doMain() {
          processCategoryList();
        }

        private void processCategoryList() {
          IQueryParam param = new QueryParam();
          param.addOutput("module", StringUtils.EMPTY);
          param.addOutput("typeCode", StringUtils.EMPTY);
          Page<ProductCategoryModel> catPage =
              SpringWebContextHolder.getBean(ProductCategoryDao.class).findEntityListByCond(param,
                  null);
          if (null == catPage || null == catPage.getResult() || catPage.getResult().size() == 0)
            return;
          for (ProductCategoryModel catModel : catPage.getResult())
            processProductList(catModel);
        }

        /**
         * 处理产品列表
         */
        private void processProductList(ProductCategoryModel catModel) {
          if (null == catModel
              || StringUtils.isOrBlank(catModel.getModule(), catModel.getTypeCode())) return;
          LoggerUtil.debug(String.format("缓存专业:%s", catModel.getTypeCode()));
          HashMap<Object, Object> input = Maps.newHashMap();
          input.put("module", catModel.getModule());
          input.put("typeCode", catModel.getTypeCode());
          Page<ProductModel> productPage =
              SpringWebContextHolder.getBean(ProductDao.class).cascadeQueryProduct(input,
                  CommUtil.getAllField(ProductModel.class));
          /** 缓存产品列表 */
          setProductList(catModel.getModule(), catModel.getTypeCode(), productPage.getResult());
          for (ProductModel product : productPage.getResult()) {
            if (null == product || null == product.getId()) continue;
            LoggerUtil.debug(String.format("缓存课程:%s", product.getName()));
            setProduct(product.getId().toString(), product);
            processProductItem(product);
            processFinalExam(product);
          }
        }

        private void processFinalExam(ProductModel product) {
          Map<String, Object> cond = Maps.newHashMap();
          cond.put("courseId", product.getId());
          List<FinalExamModel> finalExamList =
              SpringWebContextHolder.getBean(FinalExamDao.class).queryList(cond,
                  CommUtil.getAllField(FinalExamModel.class));
          if (null == finalExamList || finalExamList.size() == 0) return;
          setFinalExamList(product.getId().toString(), finalExamList);
        }

        /**
         * 处理产品条目
         * 
         * @param product 产品
         */
        private void processProductItem(ProductModel product) {
          IQueryParam param = new QueryParam();
          param.addInput("productId", product.getId());
          param.addInput("resourceType", CourseConstant.RESOURCE_TYPE_COURSE);
          param.addInput("showStatus", 1);
          param.addOutputs(CommUtil.getAllField(ProductItemModel.class));
          Page<ProductItemModel> itemPage =
              SpringWebContextHolder.getBean(ProductItemDao.class)
                  .findEntityListByCond(param, null);
          /** 缓存全量产品条目列表 */
          setProductItemList(product.getId().toString(), itemPage.getResult());
          storeChapter(product.getId().toString(), processChapterItem(itemPage.getResult()));
        }

        /**
         * 处理产品章节条目
         * 
         * @param productItemList 产品全量条目列表
         */
        private List<ProductItemModel> processChapterItem(List<ProductItemModel> productItemList) {
          Map<Long, ProductItemModel> _chapterMap = Maps.newHashMap();
          List<ProductItemModel> chapterList = Lists.newArrayList();
          ProductItemModel _chapter = null;
          for (ProductItemModel productItem : productItemList) {
            if (null == productItem.getResourceType()) continue;
            if (productItem.getParentId() == 0) {
              _chapter = _chapterMap.get(productItem.getId());
              if (_chapter != null) {
                productItem.setChildList(_chapter.getChildList());
              }
              _chapterMap.put(productItem.getId(), productItem);
              chapterList.add(productItem);
              setItemInfo(productItem.getId().toString(), productItem);
            } else if (productItem.getParentId() > 0) {
              _chapter = _chapterMap.get(productItem.getParentId());
              if (_chapter == null) {
                _chapter = new ProductItemModel();
                _chapterMap.put(productItem.getParentId(), _chapter);
              }
              _chapter.getChildList().add(productItem);
              setItemInfo(productItem.getId().toString(), productItem);
            }
          }
          return chapterList;
        }

        /**
         * 存储章节信息
         * 
         * @param productId 产品ID
         * @param chapterList 章节列表
         */
        private void storeChapter(String productId, List<ProductItemModel> chapterList) {
          /** 存储章列表 */
          setChapterList(productId, chapterList);
          for (ProductItemModel chapter : chapterList) {
            /** 存储节列表 */
            setItemList(chapter.getId().toString(), chapter.getChildList());
          }
        }

        @Override
        public void onException(Throwable t) {
          LoggerUtil.error("设置问题类型缓存异常.", t);
        }
      }, initialDelay, productListDelay, TimeUnit.SECONDS);
    }
  }

  /**
   * 根据产品ID获取产品信息
   * 
   * @param productId 产品ID
   * @return
   */
  public ProductModel getProduct(String productId) {
    init();
    String cacheKey = String.format(CourseConstant.PRODUCT, productId);
    Object product = DynamicTimingLocalCache.getCache(cacheKey);
    if (null != product) {
      return (ProductModel) product;
    }
    return null;
  }

  /**
   * 缓存产品信息
   * 
   * @param productId 产品ID
   * @param product 产品信息
   */
  private void setProduct(String productId, ProductModel product) {
    init();
    String cacheKey = String.format(CourseConstant.PRODUCT, productId);
    DynamicTimingLocalCache.cache(cacheKey, product, productListDelay * 5);
  }

  /**
   * 获取产品列表
   * 
   * @param module 模块ID
   * @param typeCode 类型码
   * @return 产品列表
   */
  public List<ProductModel> getProductList(String module, String typeCode) {
    init();
    String cacheKey = String.format(CourseConstant.PRODUCT_LIST, module, typeCode);
    Object productList = DynamicTimingLocalCache.getCache(cacheKey);
    if (null != productList) {
      return (List<ProductModel>) productList;
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
  private void setProductList(String module, String typeCode, List<ProductModel> courseProductList) {
    init();
    String cacheKey = String.format(CourseConstant.PRODUCT_LIST, module, typeCode);
    DynamicTimingLocalCache.cache(cacheKey, courseProductList, productListDelay * 5);
  }

  /**
   * 获取产品条目列表
   * 
   * @param productId 产品ID
   * @return 产品条目列表
   */
  public List<ProductItemModel> getProductItemList(String productId) {
    init();
    String cacheKey = String.format(CourseConstant.PRODUCT_ITEM_LIST, productId);
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
  private void setProductItemList(String productId, List<ProductItemModel> productItemList) {
    init();
    String cacheKey = String.format(CourseConstant.PRODUCT_ITEM_LIST, productId);
    DynamicTimingLocalCache.cache(cacheKey, productItemList, productListDelay * 5);
  }

  /**
   * 根据产品ID获取章列表
   * 
   * @param productId 产品ID
   * @return 产品章条目列表
   */
  public List<ProductItemModel> getChapterList(String productId) {
    init();
    String cacheKey = String.format(CourseConstant.PRODUCT_ITEM_CHAPTER_LIST, productId);
    Object productItemList = DynamicTimingLocalCache.getCache(cacheKey);
    if (null != productItemList) {
      return (List<ProductItemModel>) productItemList;
    }
    return null;
  }

  /**
   * 存储产品章条目表
   * 
   * @param productId 产品ID
   * @param productItemList 产品章条目列表
   */
  private void setChapterList(String productId, List<ProductItemModel> productItemList) {
    init();
    String cacheKey = String.format(CourseConstant.PRODUCT_ITEM_CHAPTER_LIST, productId);
    DynamicTimingLocalCache.cache(cacheKey, productItemList, productListDelay * 5);
  }

  /**
   * 根据产品ID和章ID获取节列表
   * 
   * @param productId 产品ID
   * @param chapterId 章ID
   * @return 产品章条目列表
   */
  public List<ProductItemModel> getItemList(String chapterId) {
    init();
    String cacheKey = String.format(CourseConstant.PRODUCT_ITEM_ITEM_LIST, chapterId);
    return DynamicTimingLocalCache.getCache(cacheKey);
  }

  /**
   * 存储产品节条目表
   * 
   * @param productId 产品ID
   * @param chapterId 章ID
   * @param productItemList 产品章条目列表
   */
  private void setItemList(String chapterId, List<ProductItemModel> productItemList) {
    init();
    String cacheKey = String.format(CourseConstant.PRODUCT_ITEM_ITEM_LIST, chapterId);
    DynamicTimingLocalCache.cache(cacheKey, productItemList, productListDelay * 5);
  }

  /**
   * 根据产品ID获取期末测试列表
   * 
   * @param productId 产品ID
   * @return 期末测试列表
   */
  public List<FinalExamModel> getFinalExamList(String productId) {
    init();
    String cacheKey = String.format(CourseConstant.PRODUCT_FINAL_EXAM_LIST, productId);
    return DynamicTimingLocalCache.getCache(cacheKey);
  }

  /**
   * 存储期末测试列表
   * 
   * @param productId 产品ID
   * @param finalExamList 期末测试列表
   */
  private void setFinalExamList(String productId, List<FinalExamModel> finalExamList) {
    init();
    String cacheKey = String.format(CourseConstant.PRODUCT_FINAL_EXAM_LIST, productId);
    DynamicTimingLocalCache.cache(cacheKey, finalExamList, productListDelay * 5);
  }

  /**
   * 获取产品节信息
   * 
   * @param productId 产品ID
   * @param chapterId 章ID
   * @param itemId 节ID
   * @return 节信息实体
   */
  public ProductItemModel getItemInfo(String itemId) {
    init();
    String cacheKey = String.format(CourseConstant.PRODUCT_ITEM_ITEM_INFO, itemId);
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
  private void setItemInfo(String itemId, ProductItemModel itemInfo) {
    init();
    String cacheKey = String.format(CourseConstant.PRODUCT_ITEM_ITEM_INFO, itemId);
    DynamicTimingLocalCache.cache(cacheKey, itemInfo, productListDelay * 5);
  }

}
