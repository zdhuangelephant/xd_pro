package com.xiaodou.mission.domain;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.annotations.Xml;

/**
 * @name @see com.xiaodou.mission.domain.ProductRelation.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月27日
 * @description 产品与专业关系表
 * @version 1.0
 */
@Data
@Xml(tableName = "xd_mission_product_relation", outputDir = "src/main/resources/conf/mybatis")
public class ProductRelation {
  @Column(isMajor = true, canUpdate = false)
  /** id 主键ID */
  private Integer id;
  @Column(canUpdate = false)
  /** productCategoryId 产品分类ID */
  private Integer productCategoryId;
  @Column(canUpdate = false)
  /** productId 产品ID */
  private Integer productId;
  @Column(canUpdate = false)
  /** relationState 状态 0 无效 1 有效 */
  private Integer relationState;
  @Column(canUpdate = false, betweenScope = true)
  /** createTime 创建时间 */
  private Timestamp createTime;
}