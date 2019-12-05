package com.xiaodou.summer.validator.enums;

import java.lang.annotation.Annotation;

import com.xiaodou.summer.validator.annotion.LegalValue;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.ValidateEntity;
import com.xiaodou.summer.validator.annotion.NotEmpty.AllNotEmptyList;
import com.xiaodou.summer.validator.annotion.NotEmpty.OrNotEmptyList;

public enum AnnotationType {

  NotEmpty(NotEmpty.class),
  LegalValue(LegalValue.class),
  ValidateEntity(ValidateEntity.class), 
  OrNotEmptyList(OrNotEmptyList.class), 
  AllNotEmptyList(AllNotEmptyList.class);

  <T extends Annotation> AnnotationType(Class<T> anno) {
    this.anno = anno;
  }

  private Class<? extends Annotation> anno;

  public Class<? extends Annotation> getAnno() {
    return anno;
  }

  public <T extends Annotation> void setAnno(Class<T> anno) {
    this.anno = anno;
  }
}
