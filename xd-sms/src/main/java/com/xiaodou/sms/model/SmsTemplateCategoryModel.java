package com.xiaodou.sms.model;

public class SmsTemplateCategoryModel extends SmsTemplateModel {
  private SmsTemplateTypeModel cateGory;

  public SmsTemplateTypeModel getCateGory() {
    return cateGory;
  }

  public void setCateGory(SmsTemplateTypeModel cateGory) {
    this.cateGory = cateGory;
  }
}
