package com.xiaodou.ms.web.request.ruide;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.ms.model.knowledge.AuthorModel;
import com.xiaodou.ms.model.ruide.TutorMajorModel;
import com.xiaodou.ms.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zdh:
 * @date 2017年6月7日
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TutorMajorEditRequest extends BaseRequest {
	@NotEmpty
	private Long id;
	  /*类型(1、导师介绍2、专业介绍3、导师论文/最热文章)*/
	  private Short type;
	  /*标题*/
	  private String title;
	  /*副标题*/
	  private String subtitle;
	  /*图片*/
	  private String image;
	  /*作者姓名*/
	  private String author;
	  /*内容*/
	  private String content;
	  /*发布时间*/
	  private Timestamp publishTime; 
	  // 所属专业类别
	  private Long majorCategoryId;
	  // 专业名称
	  private String majorName;
	  /* 正文图片 */
		private String contentImage;
	  /*创建时间*/
	  @Column(canUpdate = false)
	  private Timestamp createTime;

	public TutorMajorModel initModel() {
		TutorMajorModel model = new TutorMajorModel();
		model.setAuthor(author);
		model.setContent(content);
		model.setCreateTime(createTime);
		model.setId(id);
		model.setImage(image);
		model.setPublishTime(publishTime);
		model.setSubtitle(subtitle);
		model.setTitle(title);
		model.setType(type);
		model.setMajorCategoryId(majorCategoryId);
		model.setMajorName(majorName);
		model.setContentImage(contentImage);
		return model;
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

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
}
