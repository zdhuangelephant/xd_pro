package com.xiaodou.resources.model.product;

/**
 * @name @see com.xiaodou.course.model.product.ProductMiscInfo.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月13日
 * @description 课程产品MISC字段内容
 * @version 1.0
 */
public class ProductMiscInfo {
  /** chapterCount 章数量 */
  private Integer chapterCount;
  /** itemCount 节数量 */
  private Integer itemCount;
  public Integer getChapterCount() {
    return chapterCount;
  }
  public void setChapterCount(Integer chapterCount) {
    this.chapterCount = chapterCount;
  }
  public Integer getItemCount() {
    return itemCount;
  }
  public void setItemCount(Integer itemCount) {
    this.itemCount = itemCount;
  }
}
