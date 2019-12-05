package com.xiaodou.course.vo.product;

import java.util.List;

import lombok.Data;

/**
 * Created by zyp on 15/8/9.
 */
@Data
public class ChildChapter {

  // 章节ID
  private Long itemId;

  // 章节名称
  private String itemName;

  // 重要度
  private Integer importance;

  // 是否收费
  private Integer free;

  // 资源列表
  private List<ChapterResource> resourceList;

  // 章节别名
  private String chapterIdAlias;

  // 题目数量
  private Integer questionNum;

  
}
