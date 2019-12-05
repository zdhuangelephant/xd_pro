package com.xiaodou.ms.model.ruide;

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
	/* 标题 */
	private String title;
	/* 副标题 */
	private String subtitle;
	/* 图片 */
	private String image;
	/* 作者姓名 */
	private String author;
	/* 内容 */
	private String content;
	/* 发布时间 */
	private Timestamp publishTime;

	// 所属专业类别
	private Long majorCategoryId;
	// 专业名称
	private String majorName;
	/* 正文图片 */
	private String contentImage;
	
	private MajorCategoryModel majorCategoryModel;

	/* 创建时间 */
	@Column(canUpdate = false)
	private Timestamp createTime;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(TutorMajorModel.class, "xd_rd_tutor_major",
				"F:/snippet/eclipseWorks/xiaodou-ms-2b/src/main/resources/conf/mybatis/ruide/").buildXml();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public Timestamp getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	public Long getMajorCategoryId() {
		return majorCategoryId;
	}

	public void setMajorCategoryId(Long majorCategoryId) {
		this.majorCategoryId = majorCategoryId;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getContentImage() {
		return contentImage;
	}

	public void setContentImage(String contentImage) {
		this.contentImage = contentImage;
	}

	public MajorCategoryModel getMajorCategoryModel() {
		return majorCategoryModel;
	}

	public void setMajorCategoryModel(MajorCategoryModel majorCategoryModel) {
		this.majorCategoryModel = majorCategoryModel;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
}
