package com.xiaodou.ms.model.product;

import java.sql.Timestamp;

import lombok.Data;

/**
 * @name @see com.xiaodou.ms.model.product.ProductModuleModel.java
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月5日
 * @description 产品模块模型类
 * @version 1.0
 */
@Data
public class ProductModuleModel {

  private Long id;

  private Integer parentId = 0;

  private String module;

  private Integer showStatus;

  private String name;

  private String detail;

  private String misc;

  private Timestamp createTime;

  private Timestamp updateTime;
  //新手课程对应的模块
  private String hasBeginnerCourse;
  //新手课程ID
  private String courseId;

}
