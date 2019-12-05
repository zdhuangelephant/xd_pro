package com.xiaodou.resources.response.quesbk;

import com.xiaodou.common.util.MathUtil;
import com.xiaodou.resources.constant.quesbk.ResultType;
import com.xiaodou.resources.model.quesbk.UserExamRecordDetail;
import com.xiaodou.resources.response.BaseResponse;

public class SubmitResultResponse extends BaseResponse {
  public SubmitResultResponse(ResultType type) {
    super(type);
  }

  /** score 得分 */
  private String score;

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public void setDetail(UserExamRecordDetail detail) {
    if (null != detail) {
      if (null != detail.getScore()) setScore(MathUtil.getIntStringValue(detail.getScore()));
    }
  }

}
