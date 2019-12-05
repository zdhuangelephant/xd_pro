package com.xiaodou.st.dataclean.model.transport;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.ms.model.product.TransferProductRelationData.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月20日
 * @description 产品分类与产品关系数据
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TransferProductRelationData extends BaseTransferModel {

  /** id 主键ID */
  @NotEmpty
  private Long id;
  /** productCategoryId 产品分类ID */
  private Long productCategoryId;
  /** productId 产品ID */
  private Long productId;
  /** relationState 关系状态 */
  private Integer relationState;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getProductCategoryId() {
    return productCategoryId;
  }

  public void setProductCategoryId(Long productCategoryId) {
    this.productCategoryId = productCategoryId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Integer getRelationState() {
    return relationState;
  }

  public void setRelationState(Integer relationState) {
    this.relationState = relationState;
  }

}
