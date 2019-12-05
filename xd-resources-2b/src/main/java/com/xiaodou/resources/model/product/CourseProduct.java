package com.xiaodou.resources.model.product;

import java.lang.reflect.Field;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * @name CourseProduct CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年11月11日
 * @description 课程产品封装模型
 * @version 1.0
 */
public class CourseProduct extends ProductModel {
  /** chapterList 章列表 */
  private List<CourseProductChapter> chapterList = Lists.newArrayList();
  /** itemList 节列表 */
  private List<CourseProductItem> itemList = Lists.newArrayList();
  /** resourceList 资源列表 */
  private List<CourseProductResource> resourceList = Lists.newArrayList();

  public CourseProduct(ProductModel product) {
    for (Field f : ProductModel.class.getDeclaredFields()) {
      f.setAccessible(true);
      try {
        f.set(this, f.get(product));
      } catch (Exception e) {
        continue;
      }
    }
  }

  public List<CourseProductChapter> getChapterList() {
    return chapterList;
  }

  public void setChapterList(List<CourseProductChapter> chapterList) {
    this.chapterList = chapterList;
  }

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
