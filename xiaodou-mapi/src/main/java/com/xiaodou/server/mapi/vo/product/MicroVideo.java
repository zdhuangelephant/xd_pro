package com.xiaodou.server.mapi.vo.product;

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
  private String progress;
  /** pointDuration 视频学习过的最新的位置（可能需要增加接口提交本记录） */
  private String lastLearnTime;
  /** pointVideoCover 视频封面*/
  private String videoCover;
  /** pointVideoUrl 视频链接*/
  private String videoUrl;
  /** videoDuration 视频时长、单位秒*/
  private String videoDuration;

}
