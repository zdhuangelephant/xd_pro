package com.xiaodou.rdonline.domain.student;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.rdonline.domain.tutormajor.TutorMajorModel;

import lombok.Data;

@Data
public class StudentModel {
	@Column(isMajor = true, betweenScope = true, persistent = true)
	private Long id;
	// 所修专业
	private Long majorId;
	// 感悟简介
	private String thinkDesc;
	// 姓名
	private String author;
	// 内容
	private String content;
	/* 正文图片 */
	private String contentImage;
	// 头像
	private String portrait;
	// 发布时间
	private Timestamp publishTime;
	// 点赞数
	private Long thumbNums;
	// 已阅读量
	private Long readQuantity;
	// 是否可删除
	private Byte isDel;
	/* 创建时间 */
	@Column(canUpdate = false)
	private Timestamp createTime;
	
	private TutorMajorModel tutorMajorModel;
	
	private String htmlContent;
	public static void main(String[] args) {
		MybatisXmlTool.getInstance(StudentModel.class, "xd_rd_student",
				"F:/snippet/eclipseWorks/xd-rd-online/src/main/resources/conf/mybatis/student").buildXml();
	}
}
