package com.xiaodou.course.vo.product;

import lombok.Data;

/**
 * Created by zyp on 15/8/9.
 */
@Data
public class ChapterResource {

  // 资源Id
  private Long resourceId;

  // 名称
  private String resourceName;

  // 类型
  private Integer resourceType;

  // 是否收费
  private Integer free;

 
}
