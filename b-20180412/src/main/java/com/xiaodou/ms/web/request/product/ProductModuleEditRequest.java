package com.xiaodou.ms.web.request.product;

import com.xiaodou.ms.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zyp on 15/4/19.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ProductModuleEditRequest extends BaseRequest {

  // 课程分类ID
  @NotEmpty
  private Long id;

  // 课程分类名称
  private String name;

  // 课程分类详细介绍
  private String detail;

  /** module APP模块ID */
  private String module;
}
