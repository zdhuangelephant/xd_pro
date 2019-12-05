package com.xiaodou.sms.dao.ms;

import org.springframework.stereotype.Repository;

import com.xiaodou.sms.dao.BaseProcessDao;
import com.xiaodou.sms.model.SmsTemplateCategoryModel;

@Repository
public class SmsTemplateCategoryModelDao extends BaseProcessDao<SmsTemplateCategoryModel> {
  public SmsTemplateCategoryModel querySmsTemplateCategoryById(String id) {
    SmsTemplateCategoryModel model = new SmsTemplateCategoryModel();
    model.setId(new Integer(id));
    return (SmsTemplateCategoryModel) this.getSqlSession().selectOne("cascadeTemplateCategory.querySmsTemplateCategoryModelById",
        model);
  }
}
