package com.xiaodou.ms.vo;

import lombok.Data;

@Data
public class MicroVideo {
  /** keypointId 考点ID*/
  private String keypointId;
  /** pointVideoId 视频ID */
  private String pointVideoId;
  /** pointName 考点名 */
  private String pointName;
  /** progress 考点学习进度，1 为已完成 */
  private String progress;
  /** pointDuration 预留字段，本考点时间   单位(秒) */
  private String pointDuration;
  /** pointVideoCover 视频封面*/
  private String pointVideoCover;
  /** pointVideoUrl 视频链接*/
  private String pointVideoUrl;
  /** lastDuration 视频学习过的最新的位置（可能需要增加接口提交本记录）*/
  private String lastDuration;
public String getKeypointId() {
	return keypointId;
}
public void setKeypointId(String keypointId) {
	this.keypointId = keypointId;
}
public String getPointVideoId() {
	return pointVideoId;
}
public void setPointVideoId(String pointVideoId) {
	this.pointVideoId = pointVideoId;
}
public String getPointName() {
	return pointName;
}
public void setPointName(String pointName) {
	this.pointName = pointName;
}
public String getProgress() {
	return progress;
}
public void setProgress(String progress) {
	this.progress = progress;
}
public String getPointDuration() {
	return pointDuration;
}
public void setPointDuration(String pointDuration) {
	this.pointDuration = pointDuration;
}
public String getPointVideoCover() {
	return pointVideoCover;
}
public void setPointVideoCover(String pointVideoCover) {
	this.pointVideoCover = pointVideoCover;
}
public String getPointVideoUrl() {
	return pointVideoUrl;
}
public void setPointVideoUrl(String pointVideoUrl) {
	this.pointVideoUrl = pointVideoUrl;
}
public String getLastDuration() {
	return lastDuration;
}
public void setLastDuration(String lastDuration) {
	this.lastDuration = lastDuration;
}

}
