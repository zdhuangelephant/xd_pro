package com.xiaodou.course.model.user;

import java.sql.Timestamp;

import lombok.Data;

/**
 * Created by zyp on 15/8/9.
 */
@Data
public class UserLearnProcessModel {

  private Long id;

  // 用户Id
  private Long userId;

  // 产品Id
  private Long productId;

  // 章节Id
  private Long chapterId;
  
  // 条目Id
  private Long itemId;

  // 记录时间
  private Timestamp recordTime;

  // appId
  private Integer moduleId;


  
}
