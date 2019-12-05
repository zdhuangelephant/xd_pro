package com.xiaodou.ms.model.ruide;

import java.sql.Timestamp;
import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

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
	// 头像
	private String portrait;
	/* 正文图片 */
	private String contentImage;
	// 发布时间
	private Timestamp publishTime;
	/* 创建时间 */
	@Column(canUpdate = false)
	private Timestamp createTime;
	
	private TutorMajorModel tutorMajorModel;
	
	public static void main(String[] args) {
	    MybatisXmlTool.getInstance(StudentModel.class, "xd_rd_student",
	        "F:/snippet/eclipseWorks/xiaodou-ms-2b/src/main/resources/conf/mybatis/ruide/")
	        .buildXml();
	  }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMajorId() {
		return majorId;
	}

	public void setMajorId(Long majorId) {
		this.majorId = majorId;
	}

	public String getThinkDesc() {
		return thinkDesc;
	}

	public void setThinkDesc(String thinkDesc) {
		this.thinkDesc = thinkDesc;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getContentImage() {
		return contentImage;
	}

	public void setContentImage(String contentImage) {
		this.contentImage = contentImage;
	}

	public Timestamp getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public TutorMajorModel getTutorMajorModel() {
		return tutorMajorModel;
	}

	public void setTutorMajorModel(TutorMajorModel tutorMajorModel) {
		this.tutorMajorModel = tutorMajorModel;
	}
	
}
