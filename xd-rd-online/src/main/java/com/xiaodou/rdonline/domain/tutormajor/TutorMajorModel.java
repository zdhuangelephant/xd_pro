package com.xiaodou.rdonline.domain.tutormajor;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

@Data
public class TutorMajorModel {
	@Column(isMajor = true, betweenScope = true, persistent = true)
	private Long id;
	/* 类型(1、导师介绍2、专业介绍3、导师论文/最热文章) */
	private Short type;
	/* 副标题 */
	private String title;
	/* 副标题 */
	private String subtitle;
	/* 图片 */
	private String image;
	/* 作者姓名 */
	private String author;
	/* 内容 */
	private String content;
	/* 正文图片 */
	private String contentImage;
	/* 发布时间 */
	private Timestamp publishTime;
	// 点赞数
	private Long thumbNums;
	// 已阅读量
	private Long readQuantity;
	// 所属专业类别
	private Long majorCategoryId;
	// 专业名称
	private String majorName;
	/* 创建时间 */
	@Column(canUpdate = false)
	private Timestamp createTime;

	private String htmlContent;
	public static void main(String[] args) {
		MybatisXmlTool.getInstance(TutorMajorModel.class, "xd_rd_tutor_major",
				"F:/snippet/eclipseWorks/xd-rd-online/src/main/resources/conf/mybatis/tutormajor").buildXml();
	}
	
	
}
