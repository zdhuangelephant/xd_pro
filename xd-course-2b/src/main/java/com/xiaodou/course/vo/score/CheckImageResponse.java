package com.xiaodou.course.vo.score;

import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class CheckImageResponse extends BaseResponse {

  public CheckImageResponse(ResultType resultType) {
    super(resultType);
  }
  
  /**image 图片*/
  private String image;

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

}
