package com.xiaodou.vo;

import com.xiaodou.domain.behavior.UserWrongRecord;
import com.xiaodou.domain.product.CourseProductItem;

import lombok.Data;
@Data
public class ProductUserWrongRecordVO {
  private UserWrongRecord userWrongRecord;
  private CourseProductItem courseProductItem;
}
