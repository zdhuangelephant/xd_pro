package com.xiaodou.ms.web.response.selftaught;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.ms.model.knowledge.AuthorModel;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShowResponse extends BaseResponse {
	public ShowResponse(ResultType resultType) {
		super(resultType);
	}

	public ShowResponse() {
	}

	private AuthorModel author;

	public AuthorModel getAuthor() {
		return author;
	}

	public void setAuthor(AuthorModel author) {
		this.author = author;
	}

}
