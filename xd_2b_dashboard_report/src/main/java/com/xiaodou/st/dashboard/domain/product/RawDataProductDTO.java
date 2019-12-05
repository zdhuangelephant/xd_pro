package com.xiaodou.st.dashboard.domain.product;

import lombok.Data;

@Data
public class RawDataProductDTO {

  // 主键Id
  private Integer id;

  // 栏目Id
  private Integer productCategoryId;

  // 产品名
  private String name;
  // 课程是否报名(0,沒有報名的課程 1,已經報名的課程)
  private Integer applyStatus = 0;


}
