package com.xiaodou.server.mapi.domain;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class ChapterRecord {
  private String chapterId;// 章ID
  private String chapterName;// 章名称
  private String chapterIndex;// 章序号 eg:"第一章"
  private String starLevel; // 星级 0 0颗 1 一星 2 两星 3 三星
  private String score; // 得分
  private List<String> topUserList = Lists.newArrayList();
  private Integer completeCount = 0;
  private String pictureUrl;

  private Integer learnedItemCount = 0;// 已学习节数量
  private Integer totalItemCount = 0;// 节总数量
  private List<ItemRecord> itemList = Lists.newArrayList();// 节列表

  /** relationItem 关联id update:2018-02-28 */
  private Integer relationItem;

}
