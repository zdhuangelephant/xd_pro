package com.xiaodou.course.web.response.product;

import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

public class RecordDetailResponse extends BaseResponse {

  public RecordDetailResponse(ResultType resultType) {
    super(resultType);
  }

  public RecordDetailResponse(ProductResType resultType) {
    super(resultType);
  }

  /* challengeLevelCount 闯关数目 */
  private String challengeLevelCount = "0";
  /* levelCount 关卡数目 */
  private String totalLevelCount = "0";
  /* achieveStar 获得星星数目 */
  private String achieveStarCount = "0";
  /* allStarCount 总星星数目 */
  private String totalStarCount = "0";

  public String getChallengeLevelCount() {
    return challengeLevelCount;
  }

  public void setChallengeLevelCount(String challengeLevelCount) {
    this.challengeLevelCount = challengeLevelCount;
  }

  public String getTotalLevelCount() {
    return totalLevelCount;
  }

  public void setTotalLevelCount(String totalLevelCount) {
    this.totalLevelCount = totalLevelCount;
  }

  public String getAchieveStarCount() {
    return achieveStarCount;
  }

  public void setAchieveStarCount(String achieveStarCount) {
    this.achieveStarCount = achieveStarCount;
  }

  public String getTotalStarCount() {
    return totalStarCount;
  }

  public void setTotalStarCount(String totalStarCount) {
    this.totalStarCount = totalStarCount;
  }

}
