package com.xiaodou.course.vo.productItem;

import lombok.Data;

/**
 * @name @see com.xiaodou.course.vo.productItem.AudioInfo.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月21日
 * @description 音频信息
 * @version 1.0
 */
@Data
public class AudioInfo {
	/** courseName 课程名 */
	private String courseName;
	/** courseId 课程ID */
	private Long courseId;
	/** chapterId 章ID */
	private Long chapterId;
	/** itemId 节ID */
	private Long itemId;
	/** audioName 音频名称 */
	private String audioName;
	/** audioId 音频ID */
	private Long audioId;
	/** timeLength 音频时长 */
	private String timeLength;
	/** size 音频大小 */
	private String size;
	/** url 下载地址 */
	private String url;

}
