package com.xiaodou.resources.model.product;

import java.lang.reflect.Field;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * @name CourseProductItem 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年11月11日
 * @description 课程产品节封装
 * @version 1.0
 */
public class CourseProductItem extends ProductItemModel {
  public CourseProductItem(ProductItemModel product) {
    for (Field f : ProductItemModel.class.getDeclaredFields()) {
      f.setAccessible(true);
      try {
        f.set(this, f.get(product));
      } catch (Exception e) {
        continue;
      }
    }
  }
  /** resourceList 资源列表 */
  private List<CourseProductResource> resourceList = Lists.newArrayList();
  public List<CourseProductResource> getResourceList() {
    return resourceList;
  }
  public void setResourceList(List<CourseProductResource> resourceList) {
    this.resourceList = resourceList;
  }
}