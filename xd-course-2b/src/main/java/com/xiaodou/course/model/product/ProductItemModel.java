package com.xiaodou.course.model.product;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.xiaodou.common.annotation.BaseField;

import lombok.Data;

/**
 * Created by zyp on 15/6/26.
 */
@Data
public class ProductItemModel {
  
  // 主键ID
  @BaseField
  private Long id;

  // 产品Id
  @BaseField
  private Long productId;

  // 父ID
  @BaseField
  private Long parentId;

  // 资源Id
  @BaseField
  private Long resourceId;

  // 资源类型
  @BaseField
  private Integer resourceType;

  // 节点等级（所在层级(从0级开始，最好不要超过3级) level=0表示章， level=1表示节，level=2表示节下面的课件、视频。）
  @BaseField
  private Integer level;

  // item 名称
  @BaseField
  private String name;

  // 是否展示
  @BaseField
  private Integer showStatus;

  // 详情
  @BaseField
  private String detail;

  // 杂项
  @BaseField
  private String misc;

  // 所有父Id
  @BaseField
  private String allParentId;

  // 子ID
  @BaseField
  private String childId;

  // 所有子Id
  @BaseField
  private String allChildId;

  // 创建时间
  @BaseField
  private Timestamp createTime;

  // 更新时间
  @BaseField
  private Timestamp updateTime;

  // 是否免费
  @BaseField
  private Integer isFree;

  // 是否子节点
  @BaseField
  private Integer isLeaf;

  // 子章节
  @BaseField
  private List<ProductItemModel> childList = new ArrayList<>();

  // 章节号
  @BaseField
  private String chapterId;

  // 重要程度
  @BaseField
  private Integer importanceLevel;

  // 排序
  @BaseField
  private Integer listOrder;

  // 题目数
  @BaseField
  private Integer quesNum;

  // 资源
  @BaseField
  private String resource;

  // 任务比例
  @BaseField
  private Integer taskRatio;

  // 关联资源
  @BaseField
  private Long relationItem;

  // 关联资源名称
  @BaseField
  private String relationItemName;

  private Integer commentCount;

  private String topUserList;

  private Integer completeCount;
  @BaseField
  private String pictureUrl;

  

}
