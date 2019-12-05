package com.xiaodou.ms.web.request.course;

import com.xiaodou.ms.web.request.BaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zyp on 15/7/5.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResourceVideoEditRequest extends BaseRequest {

	// 文档ID
	private Long id;

	// 文档所属的大纲章节ID
	private Long chapterId;
	private String type = "2";
	private Integer timeLengthSecond = 0;
	private Integer timeLengthMinute = 0;
	private String img;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getTimeLengthSecond() {
		return timeLengthSecond;
	}

	public void setTimeLengthSecond(Integer timeLengthSecond) {
		this.timeLengthSecond = timeLengthSecond;
	}

	public Integer getTimeLengthMinute() {
		return timeLengthMinute;
	}

	public void setTimeLengthMinute(Integer timeLengthMinute) {
		this.timeLengthMinute = timeLengthMinute;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	// 文档名称
	private String name;

	// 文档所在的URL地址
	private String url;

	// 下载地址
	private String fileUrl;

	// 文档详细描述
	private String detail;

}
