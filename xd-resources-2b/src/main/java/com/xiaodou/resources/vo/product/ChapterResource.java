package com.xiaodou.resources.vo.product;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.xiaodou.resources.model.product.CourseProductChapter;
import com.xiaodou.resources.model.product.CourseProductItem;
import com.xiaodou.resources.model.product.CourseProductResource;

/**
 * @name ChapterResource CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年10月24日
 * @description 章节资源实体
 * @version 1.0
 */
public class ChapterResource {
  /** chapterId 章ID */
  private String chapterId = StringUtils.EMPTY;
  /** chapterName 章名称 */
  private String chapterName = StringUtils.EMPTY;
  private List<ItemResource> itemList = Lists.newArrayList();
  /** resourceList 资源列表 */
  private List<ResourceVo> resourceList = Lists.newArrayList();

  public String getChapterId() {
    return chapterId;
  }

  public void setChapterId(String chapterId) {
    this.chapterId = chapterId;
  }

  public String getChapterName() {
    return chapterName;
  }

  public void setChapterName(String chapterName) {
    this.chapterName = chapterName;
  }

  public List<ItemResource> getItemList() {
    return itemList;
  }

  public void setItemList(List<ItemResource> itemList) {
    this.itemList = itemList;
  }

  public List<ResourceVo> getResourceList() {
    return resourceList;
  }

  public void setResourceList(List<ResourceVo> resourceList) {
    this.resourceList = resourceList;
  }

  public static class ItemResource {
    /** itemId 节ID */
    private String itemId = StringUtils.EMPTY;
    /** itemName 节名称 */
    private String itemName = StringUtils.EMPTY;
    /** resourceList 资源列表 */
    private List<ResourceVo> resourceList = Lists.newArrayList();

    public String getItemId() {
      return itemId;
    }

    public void setItemId(String itemId) {
      this.itemId = itemId;
    }

    public String getItemName() {
      return itemName;
    }

    public void setItemName(String itemName) {
      this.itemName = itemName;
    }

    public List<ResourceVo> getResourceList() {
      return resourceList;
    }

    public void setResourceList(List<ResourceVo> resourceList) {
      this.resourceList = resourceList;
    }

    public void initFromProductItem(CourseProductItem productItem) {
      this.itemId = productItem.getId().toString();
      this.itemName = productItem.getName();
      if (null != productItem.getResourceList() && productItem.getResourceList().size() > 0) {
        for (CourseProductResource productResource : productItem.getResourceList()) {
          if (null == productResource) continue;
          ResourceVo resource = new ResourceVo();
          resource.getResourceFromProductItem(productResource);
          this.resourceList.add(resource);
        }
      }
    }
  }

  public void initFromProductChapter(CourseProductChapter productChapter) {
    this.chapterId = productChapter.getId().toString();
    this.chapterName = productChapter.getName();
    if (null != productChapter.getItemList() && productChapter.getItemList().size() > 0) {
      for (CourseProductItem productItem : productChapter.getItemList()) {
        if (null == productItem) continue;
        ItemResource item = new ItemResource();
        item.initFromProductItem(productItem);
        this.itemList.add(item);
      }
    }
    if (null != productChapter.getResourceList() && productChapter.getResourceList().size() > 0) {
      for (CourseProductResource productResource : productChapter.getResourceList()) {
        if (null == productResource) continue;
        ResourceVo resource = new ResourceVo();
        resource.getResourceFromProductItem(productResource);
        this.resourceList.add(resource);
      }
    }
  }
}
