package com.xiaodou.resources.service.quesbk.rule.model;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @name RuleDetail CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月8日
 * @description 出題規則明細
 * @version 1.0
 */
public class RuleDetail {

  /**
   * 規則詳細列表
   */
  private List<Rule> itemList = Lists.newArrayList();

  public List<Rule> getItemList() {
    return itemList;
  }

  public void setItemList(List<Rule> itemList) {
    this.itemList = itemList;
  }

}
