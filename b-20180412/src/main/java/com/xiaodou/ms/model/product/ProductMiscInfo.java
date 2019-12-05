package com.xiaodou.ms.model.product;

import lombok.Data;

/**
 * @name @see com.xiaodou.course.model.product.ProductMiscInfo.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月13日
 * @description 课程产品MISC字段内容
 * @version 1.0
 */
@Data
public class ProductMiscInfo {
  /** chapterCount 章数量 */
  private Integer chapterCount = 0;
  /** itemCount 节数量 */
  private Integer itemCount = 0;
  /** finalExamCount 期末测试数量 */
  private Integer finalExamCount = 0;

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

public Integer getFinalExamCount() {
	return finalExamCount;
}

public void setFinalExamCount(Integer finalExamCount) {
	this.finalExamCount = finalExamCount;
}

}
