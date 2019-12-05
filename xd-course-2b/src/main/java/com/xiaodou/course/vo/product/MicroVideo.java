package com.xiaodou.course.vo.product;

import lombok.Data;

@Data
public class MicroVideo {
  /** keypointId 考点ID*/
  private String pointId;
  /** pointVideoId 视频ID */
  private String pointName;
  /** pointName 考点名 */
  private String content;
  /** progress 考点学习进度，1 为已完成 */
  private String progress = "0";
  /** pointDuration 视频学习过的最新的位置（可能需要增加接口提交本记录） */
  private String lastLearnTime = "0";
  /** pointVideoCover 视频封面*/
  private String videoCover;
  /** pointVideoUrl 视频链接*/
  private String videoUrl;
  /** videoDuration 资源时长、单位秒*/
  private String videoDuration;

}
