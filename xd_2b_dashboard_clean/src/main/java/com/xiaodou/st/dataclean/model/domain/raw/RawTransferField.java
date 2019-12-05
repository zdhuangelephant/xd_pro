package com.xiaodou.st.dataclean.model.domain.raw;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.st.dataclean.model.domain.dashboard.ApplyModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.StudentModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.Unit;
import com.xiaodou.st.dataclean.model.transport.BaseTransferModel;

/**
 * @name @see com.xiaodou.st.dataclean.model.domain.raw.RawTransferField.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月26日
 * @description 标准传输模型
 * @version 1.0
 */
public class RawTransferField extends BaseTransferModel{
  @Column(persistent = false)
  private StudentModel student;
  @Column(persistent = false)
  private RawDataProductCategoryModel categoryModel;
  @Column(persistent = false)
  private RawDataProductModel productModel;
  @Column(persistent = false)
  private ApplyModel applyInfo;
  @Column(persistent = false)
  private Unit taughtUnit;
  @Column(persistent = false)
  private Unit cheifUnit;
  @Column(persistent = false)
  private Unit pioltUnit;

  public StudentModel getStudent() {
    return student;
  }

  public void setStudent(StudentModel student) {
    this.student = student;
  }

  public RawDataProductCategoryModel getCategoryModel() {
    return categoryModel;
  }

  public void setCategoryModel(RawDataProductCategoryModel categoryModel) {
    this.categoryModel = categoryModel;
  }

  public RawDataProductModel getProductModel() {
    return productModel;
  }

  public void setProductModel(RawDataProductModel productModel) {
    this.productModel = productModel;
  }

  public ApplyModel getApplyInfo() {
    return applyInfo;
  }

  public void setApplyInfo(ApplyModel applyInfo) {
    this.applyInfo = applyInfo;
  }

  public Unit getTaughtUnit() {
    return taughtUnit;
  }

  public void setTaughtUnit(Unit taughtUnit) {
    this.taughtUnit = taughtUnit;
  }

  public Unit getCheifUnit() {
    return cheifUnit;
  }

  public void setCheifUnit(Unit cheifUnit) {
    this.cheifUnit = cheifUnit;
  }

  public Unit getPioltUnit() {
    return pioltUnit;
  }

  public void setPioltUnit(Unit pioltUnit) {
    this.pioltUnit = pioltUnit;
  }

}
