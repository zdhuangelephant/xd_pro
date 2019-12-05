package com.xiaodou.resources.model.quesbk;

import com.xiaodou.resources.model.BaseEntity;

/**
 * <p>
 * 测验规则
 * </p>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @version 1.0
 * @date 2015年6月29日
 */
public class QuesbkExamRule extends BaseEntity {
  /**
   * 主键ID
   */
  private Integer id;

  /** name 规则名称 */
  private String name;

  /**
   * 产品ID
   */
  private Integer productId;

  /**
   * 测验类型ID
   */
  private Integer examTypeId;

  /**
   * 规则细节
   */
  private String ruleDetail;

  /**
   * 规则状态
   */
  private Short status;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public Integer getExamTypeId() {
    return examTypeId;
  }

  public void setExamTypeId(Integer examTypeId) {
    this.examTypeId = examTypeId;
  }

  public Short getStatus() {
    return status;
  }

  public void setStatus(Short status) {
    this.status = status;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getRuleDetail() {
    return ruleDetail;
  }

  public void setRuleDetail(String ruleDetail) {
    this.ruleDetail = ruleDetail == null ? null : ruleDetail.trim();
  }
}
