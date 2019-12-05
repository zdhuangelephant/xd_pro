package com.xiaodou.course.model.product;

import lombok.Data;

/**
 * @name @see com.xiaodou.ms.model.course.CourseResourceAudioModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月21日
 * @description 音频模型model
 * @version 1.0
 */
@Data
public class CourseResourceModel {

  // 视频ID
  private Long id;

  // 课程Id
  private Long courseId;

  // 音频所属的大纲章节ID
  private Long chapterId;

  // 音频名称
  private String name;

  // 音频所在的URL地址
  private String url;

  // 时长展示分钟段
  private Integer timeLengthMinute;

  // 时长展示秒数段
  private Integer timeLengthSecond;

  // 音频文件大小M
  private Double size;

  // 音频详细描述
  private String detail;

  // 音频的关键字，方便全局搜索
  private String keyPoint;

  // 章节名称
//  private String chapterName;
//  private Integer status;
//  private String fileUrl;

}
