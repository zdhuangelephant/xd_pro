package com.xiaodou.server.mapi.domain;

import lombok.Data;

/**
 * @name ItemTaskInfo 
 * CopyRright (c) 2018 by Corp.XiaodouTech
 *
 * @author <a href="mailto:hzd82274@gmail.com">zdhuang</a>
 * @date 2018年6月21日
 * @description TODO
 * @version 1.0
 */
@Data
public class ItemTaskInfo {
  /** taskStatus 1/0,    #finished / unfinished, */
  private String taskStatus;  
  /** taskDuration 3:00, ,*/
  private String taskDuration;
  /** taskInfo 视频/音频/闯关*/
  private String taskInfo;
  
}
