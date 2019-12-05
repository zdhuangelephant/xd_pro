package com.xiaodou.mooccrawler.web.response;

/**
 * Created by zyp on 15/4/19.
 */
public class CategoryCreateResponse extends BaseResponse {

  /**
   * 栏目Id
   */
  private Integer catId;

  public CategoryCreateResponse(ResultType resultType) {
    super(resultType);
  }

  public Integer getCatId() {
    return catId;
  }

  public void setCatId(Integer catId) {
    this.catId = catId;
  }
}
