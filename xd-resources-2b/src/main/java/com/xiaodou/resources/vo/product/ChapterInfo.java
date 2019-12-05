package com.xiaodou.resources.vo.product;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;

/**
 * @name ChapterInfo CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年10月24日
 * @description 教学计划模型
 * @version 1.0
 */
public class ChapterInfo {
  /** chapterId 章ID */
  private String chapterId = StringUtils.EMPTY;
  /** chapterName 章名称 */
  private String chapterName = StringUtils.EMPTY;
  private List<ItemInfo> itemList = Lists.newArrayList();

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

  public List<ItemInfo> getItemList() {
    return itemList;
  }

  public void setItemList(List<ItemInfo> itemList) {
    this.itemList = itemList;
  }

  /**
   * @name ItemInfo CopyRright (c) 2016 by zhaodan
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2016年10月24日
   * @description 节信息
   * @version 1.0
   */
  public static class ItemInfo {
    /** itemId 节ID */
    private String itemId = StringUtils.EMPTY;
    /** itemName 节名称 */
    private String itemName = StringUtils.EMPTY;

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

  }
}
