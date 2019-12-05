package com.xiaodou.course.vo.product;

import lombok.Data;

@Data
public class ItemTaskVO {
  /** taskStatus finished / unfinished,*/
  private String taskStatus;
  /** taskDuration #duration,*/
  private String taskDuration;
  /** taskInfo 视频/音频*/
  private String taskInfo;
  
}
