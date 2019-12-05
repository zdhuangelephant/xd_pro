package com.xiaodou.oms.vo.input.order;

import org.springframework.validation.Errors;

import com.xiaodou.oms.entity.order.Gorder;
import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.ValidateEntity;

/**
 * <p>下单入参Pojo</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月19日
 */
public class CreateOrderPojo extends BasePojo {

    /**
     * gorder对象
     */
  @NotEmpty
  @ValidateEntity
  private Gorder gorder;
    /**
     * 关联类型
     */
  @LegalValueList(value={"1","2","3","4"})
  private String relationType;
    /**
     * 关联关系
     */
  private String relations;
  /**
   * 风控推单信息
   */
  private String fraudJson;

  public Gorder getGorder() {
    return gorder;
  }

  public void setGorder(Gorder gorder) {
    this.gorder = gorder;
  }

  public String getRelationType() {
    return relationType;
  }

  public void setRelationType(String relationType) {
    this.relationType = relationType;
  }

  public String getRelations() {
    return relations;
  }

  public void setRelations(String relations) {
    this.relations = relations;
  }

  public void validate(CreateOrderPojo objet,Errors errors){

  }

  public String getFraudJson() {
    return fraudJson;
  }

  public void setFraudJson(String fraudJson) {
    this.fraudJson = fraudJson;
  }

}
