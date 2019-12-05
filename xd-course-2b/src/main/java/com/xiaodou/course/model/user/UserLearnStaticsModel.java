package com.xiaodou.course.model.user;

import java.sql.Timestamp;

import lombok.Data;

/**
 * Created by zyp on 15/8/23.
 */
@Data
public class UserLearnStaticsModel {

  // 主键
  private Integer id;

  // 用户Id
  private Long userId;

  // 比例
  private Integer ratio;

  // 产品Id
  private Long productId;

  // 当前进度
  private Integer currentItem;

  // 当前进度
  private String currentItemName;

  // 更新时间
  private Timestamp updateTime;

  // 章节号
  private Long chapterId;

  // 章节名称
  private String chapterName;

  
}
