package com.xiaodou.resources.model.product;

import java.lang.reflect.Field;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * @name CourseProductChaper 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年11月11日
 * @description 课程产品章封装模型
 * @version 1.0
 */
public class CourseProductChapter extends ProductItemModel {
  public CourseProductChapter(ProductItemModel product) {
    for (Field f : ProductItemModel.class.getDeclaredFields()) {
      f.setAccessible(true);
      try {
        f.set(this, f.get(product));
      } catch (Exception e) {
        continue;
      }
    }
  }
  /** itemList 节列表 */
  private List<CourseProductItem> itemList = Lists.newArrayList();
  /** resourceList 资源列表 */
  private List<CourseProductResource> resourceList = Lists.newArrayList();
  public List<CourseProductItem> getItemList() {
    return itemList;
  }
  public void setItemList(List<CourseProductItem> itemList) {
    this.itemList = itemList;
  }
  public List<CourseProductResource> getResourceList() {
    return resourceList;
  }
  public void setResourceList(List<CourseProductResource> resourceList) {
    this.resourceList = resourceList;
  }
}