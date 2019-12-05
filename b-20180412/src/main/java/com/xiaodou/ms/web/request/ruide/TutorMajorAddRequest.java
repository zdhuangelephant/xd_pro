package com.xiaodou.ms.web.request.ruide;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.ms.model.ruide.TutorMajorModel;
import com.xiaodou.ms.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TutorMajorAddRequest extends BaseRequest {

	/* 标题 */
	@NotEmpty
	private String title;
	/* 副标题 */
	private String subtitle;
	/* 图片 */
	private String image;
	/* 作者姓名 */
	@NotEmpty
	private String author;
	/* 内容 */
	@NotEmpty
	private String content;
	// 所属专业类别
	private Long majorCategoryId;
	// 专业名称
	private String majorName;
	/* 正文图片 */
	private String contentImage;
	public TutorMajorModel initModel() {
		TutorMajorModel model = new TutorMajorModel();
		model.setAuthor(author);
		model.setContent(content);
		model.setImage(image);
		model.setSubtitle(subtitle);
		model.setTitle(title);
		model.setMajorName(majorName);
		model.setMajorCategoryId(majorCategoryId);
		model.setContentImage(contentImage);
		return model;
	}

}