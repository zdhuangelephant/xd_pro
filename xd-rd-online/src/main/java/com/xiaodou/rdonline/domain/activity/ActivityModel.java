package com.xiaodou.rdonline.domain.activity;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.rdonline.domain.tutormajor.TutorMajorModel;

import lombok.Data;

@Data
public class ActivityModel {
	@Column(isMajor = true,betweenScope=true ,persistent = true)
	private Long id;
	/* 标题 */
	private String title;
	/* 副标题 */
	private String subtitle;
	// 活动时间
	private Timestamp activityTime;
	// 截至时间
	private Timestamp deadlineTime;
	// 活动地点
	private String activityPlace;
	// 关联导师
	private Long tutorId;
	/* 内容 */
	private String content;
	/* 正文图片 */
	private String contentImage;
	// 点赞数
	private Long thumbNums;
	// 已阅读量
	private Long readQuantity;
	/* 创建时间 */
	@Column(canUpdate = false)
	private Timestamp createTime;
	
	private TutorMajorModel tutorMajorModel;
	
	private String htmlContent;
	public static void main(String[] args) {
		MybatisXmlTool.getInstance(ActivityModel.class, "xd_rd_activity",
				"F:/snippet/eclipseWorks/xd-rd-online/src/main/resources/conf/mybatis/activity").buildXml();
	}
}
