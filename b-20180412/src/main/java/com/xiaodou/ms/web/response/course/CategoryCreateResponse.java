package com.xiaodou.ms.web.response.course;

import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zyp on 15/4/19.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CategoryCreateResponse extends BaseResponse {
  /**
   * 栏目Id
   */
  private Long catId;
  public CategoryCreateResponse(ResultType resultType) {
    super(resultType);
  }
	public Long getCatId() {
		return catId;
	}
	public void setCatId(Long catId) {
		this.catId = catId;
	}
}
