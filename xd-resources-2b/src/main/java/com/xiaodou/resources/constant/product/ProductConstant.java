package com.xiaodou.resources.constant.product;

/**
 * @name ProductConstant 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年11月14日
 * @description 产品常量
 * @version 1.0
 */
public interface ProductConstant {

  /** PRODUCT 产品缓存前缀 */
  public final static String PRODUCT = "xd:resource:product:%s";
  
  /** USER_PRODUCT 用户产品缓存前缀 */
  public final static String USER_PRODUCT = "xd:resource:user:product:%s:%s";

  /** PRODUCT_LIST 产品列表缓存前缀 */
  public final static String PRODUCT_LIST = "xd:resource:productlist:%s:%s";

  /** PRODUCT_ITEM_LIST 产品条目列表缓存前缀 */
  public final static String PRODUCT_ITEM_LIST = "xd:resource:productitemlist:%s";

  /** PRODUCT_ITEM_CHAPTER_LIST 产品条目章列表缓存前缀 */
  public final static String PRODUCT_ITEM_CHAPTER_LIST = "xd:resource:productitem:chapter:list:%s";
  
  /** PRODUCT_ITEM_RESOURCE_LIST 产品条目资源列表缓存前缀 */
  public final static String PRODUCT_ITEM_RESOURCE_LIST = "xd:resource:productitem:resource:list:%s:%s";
  
  /** PRODUCT_ITEM_RESOURCE_INFO 产品条目资源信息缓存前缀 */
  public final static String PRODUCT_ITEM_RESOURCE_INFO = "xd:resource:productitem:resourceinfo:%s:%s";

  /** PRODUCT_ITEM_CHAPTER_INFO 产品条目章信息缓存前缀 */
  public final static String PRODUCT_ITEM_CHAPTER_INFO = "xd:resource:productitem:chapterinfo:%s:%s";

  /** PRODUCT_ITEM_ITEM_INFO 产品条目节信息缓存前缀 */
  public final static String PRODUCT_ITEM_ITEM_INFO = "xd:resource:productitem:iteminfo:%s:%s";

  /** PRODUCT_ITEM_ITEM_LIST 产品条目节列表缓存前缀 */
  public final static String PRODUCT_ITEM_ITEM_LIST = "xd:resource:productitem:item:list:%s:%s";

  /** PRODUCT_LIST_TASK_DELAY 产品列表刷新调度任务执行周期 */
  public final static String PRODUCT_LIST_TASK_DELAY = "xd.resource.product.list.task.delay";
  
}
