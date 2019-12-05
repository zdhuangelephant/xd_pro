package com.xiaodou.resources.model.product;

import java.lang.reflect.Field;

/**
 * @name CourseProductResource 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年11月11日
 * @description 课程产品资源封装
 * @version 1.0
 */
public class CourseProductResource extends ProductItemModel {
  public CourseProductResource(ProductItemModel product) {
    for (Field f : ProductItemModel.class.getDeclaredFields()) {
      f.setAccessible(true);
      try {
        f.set(this, f.get(product));
      } catch (Exception e) {
        continue;
      }
    }
  }
}