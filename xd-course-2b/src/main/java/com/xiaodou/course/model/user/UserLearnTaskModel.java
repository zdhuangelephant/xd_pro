package com.xiaodou.course.model.user;

import java.sql.Timestamp;

import com.xiaodou.common.annotation.BaseField;

import lombok.Data;

/**
 * Created by zyp on 15/8/23.
 */
@Data
public class UserLearnTaskModel {

  // 主键
  @BaseField
  private Long id;

  // 产品Id
  @BaseField
  private Long productId;

  // itemId
  @BaseField
  private Long itemId;

  // 任务比例
  @BaseField
  private Integer taskRatio;

  // 完成时间
  @BaseField
  private Timestamp completeTime;

  // 开始时间
  @BaseField
  private Timestamp beginTime;

  // 模块Id
  @BaseField
  private Integer moduleId;

  // 用户Id
  @BaseField
  private Long userId;

  
}
