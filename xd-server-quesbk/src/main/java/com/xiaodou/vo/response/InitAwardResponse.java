package com.xiaodou.vo.response;

import com.xiaodou.constant.ResultType;
import com.xiaodou.domain.product.RedBonus.CourseBonus;

/**
 * @name @see com.xiaodou.vo.response.InitAwardResponse.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月18日
 * @description 初始化红包奖励响应
 * @version 1.0
 */
public class InitAwardResponse extends BaseResponse {

  public InitAwardResponse(ResultType type) {
    super(type);
  }

  /** type 红包类型 */
  private String type;
  /** bonusId 红包ID */
  private String bonusId;
  /** originalScore 原始分 */
  private String originalScore;
  /** bonusScore 红包分 */
  private String bonusScore;
  /** other1 干扰分 */
  private String other1;
  /** other2 干扰分 */
  private String other2;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getBonusId() {
    return bonusId;
  }

  public void setBonusId(String bonusId) {
    this.bonusId = bonusId;
  }

  public String getOriginalScore() {
    return originalScore;
  }

  public void setOriginalScore(String originalScore) {
    this.originalScore = originalScore;
  }

  public String getBonusScore() {
    return bonusScore;
  }

  public void setBonusScore(String bonusScore) {
    this.bonusScore = bonusScore;
  }

  public String getOther1() {
    return other1;
  }

  public void setOther1(String other1) {
    this.other1 = other1;
  }

  public String getOther2() {
    return other2;
  }

  public void setOther2(String other2) {
    this.other2 = other2;
  }

  public void setAward(CourseBonus courseBouns) {
    if (null == courseBouns) return;
    if (null != courseBouns.getOriginalScore())
      this.originalScore = courseBouns.getOriginalScore().toString();
    if (null != courseBouns.getBonusScore())
      this.bonusScore = courseBouns.getBonusScore().toString();
    if (null != courseBouns.getOther1()) this.other1 = courseBouns.getOther1().toString();
    if (null != courseBouns.getOther2()) this.other2 = courseBouns.getOther2().toString();
  }

}
