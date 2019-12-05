package com.xiaodou.ms.web.request.topic;

import com.xiaodou.ms.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class ForumEditRequest extends BaseRequest {
	@NotEmpty
	private Integer id;
	//private Integer categoryId;
	//话题状态
	private Integer status;
	//话题是否精华
	private Integer digest;
	//话题是否置顶
	private Integer top;
	//话题是否推荐
	private Integer recommend;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDigest() {
		return digest;
	}

	public void setDigest(Integer digest) {
		this.digest = digest;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
