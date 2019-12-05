package com.xiaodou.ms.model.exam;

import java.util.List;

/**
 * Created by zyp on 15/7/27.
 */
public class ExamRule {

  /**
   * 有子章节的
   */
  private List<String> hasChildChapters;

  /**
   * 重点章节
   */
  private List<String> importChapters;

  /**
   * 细目
   */
  private List<ExamRuleItem> itemList;

  public List<String> getHasChildChapters() {
    return hasChildChapters;
  }

  public void setHasChildChapters(List<String> hasChildChapters) {
    this.hasChildChapters = hasChildChapters;
  }

  public List<String> getImportChapters() {
    return importChapters;
  }

  public void setImportChapters(List<String> importChapters) {
    this.importChapters = importChapters;
  }

  public List<ExamRuleItem> getItemList() {
    return itemList;
  }

  public void setItemList(List<ExamRuleItem> itemList) {
    this.itemList = itemList;
  }
}
