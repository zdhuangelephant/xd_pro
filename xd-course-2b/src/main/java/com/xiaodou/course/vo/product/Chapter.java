package com.xiaodou.course.vo.product;

import java.util.List;

import lombok.Data;

/**
 * Created by zyp on 15/8/9.
 */
@Data
public class Chapter {

  // 章节ID
  private Long chapterId;

  // 章节名称
  private String chapterName;

  // 重要度
  private Integer importance;

  // 是否收费
  private Integer free;

  // 子章节
  private List<ChildChapter> childList;

  // 章节别名
  private String chapterIdAlias;

  // 资源列表
  private List<ChapterResource> resourceList;

  // 题目数
  private Integer questionNum;

}
