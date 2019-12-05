package com.xiaodou.vo.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.xiaodou.constant.ResultType;
import com.xiaodou.domain.product.ProductScorePointRule;

/**
 * @name @see com.xiaodou.vo.response.ProductScorePointRuleResponse.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月24日
 * @description 计分点规则详情响应类
 * @version 1.0
 */
public class ProductScorePointRuleResponse extends BaseResponse {

  public ProductScorePointRuleResponse(ResultType type) {
    super(type);
  }

  /** scorePointRule 计分点规则详情 */
  private ProductScorePointRule scorePointRule;

  public ProductScorePointRule getScorePointRule() {
    return scorePointRule;
  }

  public void setScorePointRule(ProductScorePointRule scorePointRule) {
    this.scorePointRule = scorePointRule;
  }
}
